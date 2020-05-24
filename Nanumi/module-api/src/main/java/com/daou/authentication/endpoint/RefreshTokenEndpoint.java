package com.daou.authentication.endpoint;

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
import com.daou.entity.TempAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 토큰 새로고침 앤드포인트
 * @author pkh879
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawAccessJwtToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawAccessJwtToken, jwtSettings.getTokenSigningKey()).orElseThrow(()-> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if(!tokenVerifier.verify(jti)){
            throw new InvalidJwtToken();
        }

        // 유저정보 추가
        String subject = refreshToken.getSubject();

        return null;
    }
}
