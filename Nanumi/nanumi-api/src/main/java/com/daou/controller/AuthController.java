package com.daou.controller;

import com.daou.authentication.model.Role;
import com.daou.authentication.auth.ajax.LoginRequest;
import com.daou.authentication.auth.jwt.extractor.TokenExtractor;
import com.daou.authentication.auth.jwt.verifier.TokenVerifier;
import com.daou.authentication.config.JwtSettings;
import com.daou.authentication.config.WebSecurityConfig;
import com.daou.authentication.exceptions.InvalidJwtToken;
import com.daou.authentication.model.UserContext;
import com.daou.authentication.model.token.JwtToken;
import com.daou.authentication.model.token.JwtTokenFactory;
import com.daou.authentication.model.token.RawAccessJwtToken;
import com.daou.authentication.model.token.RefreshToken;
import com.daou.common.Logger;
import com.daou.entity.Account;
import com.daou.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 인증 앤드 포인트
 * @author pkh879
 */
@RestController
@RequestMapping("api")
public class AuthController {

    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private AccountService accountService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;

    // Swagger 제공용 토큰 발행 컨트롤러
    @PostMapping("/auth/login")
    public ResponseEntity<Object> createToken(@RequestBody LoginRequest loginRequest){
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("token","string");
        tokenData.put("refreshToken","string");
        return new ResponseEntity<Object>(tokenData, HttpStatus.OK);
    }

    
    // 토큰 재발급 앤드 포인트
    @RequestMapping(value = "/auth/token", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawAccessJwtToken, jwtSettings.getTokenSigningKey()).orElseThrow(()-> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if(!tokenVerifier.verify(jti)){
            throw new InvalidJwtToken();
        }

        // 유저정보 추가
        String subject = refreshToken.getSubject();
        Account account = accountService.findById(subject).orElseThrow(() -> new UsernameNotFoundException("사용자를 발견하지 못했습니다 : " + subject));

        if(account.getRoleCd() == null) throw new InsufficientAuthenticationException("사용자의 권한이 부여되지 않았습니다");
        List<Role> roles = new ArrayList<>();
        if(account.getRoleCd().equals("ADMIN"))
            roles.add(Role.ADMIN);
        else
            roles.add(Role.USER);
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(account.getId(), authorities);
        return tokenFactory.createAccessJwtToken(userContext);
    }
}
