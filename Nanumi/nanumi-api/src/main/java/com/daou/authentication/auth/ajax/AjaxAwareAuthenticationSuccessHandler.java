package com.daou.authentication.auth.ajax;

import com.daou.authentication.model.UserContext;
import com.daou.authentication.model.token.AccessJwtToken;
import com.daou.authentication.model.token.JwtToken;
import com.daou.authentication.model.token.JwtTokenFactory;
import com.daou.common.Logger;
import com.daou.entity.Account;
import com.daou.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 인증 성공시 실행 되는 콜백
 * @author pkh879
 */
@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final JwtTokenFactory jwtTokenFactory;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper objectMapper, final JwtTokenFactory jwtTokenFactory, final AccountService accountService){
        this.objectMapper = objectMapper;
        this.jwtTokenFactory = jwtTokenFactory;
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        AccessJwtToken accessToken = jwtTokenFactory.createAccessJwtToken(userContext);
        JwtToken refreshToken = jwtTokenFactory.createRefreshToken(userContext);

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());

//        // 사용자 DB 토큰값 입력 Deprecated 2020.06.04
//        Logger.write(userContext.getUsername(), accessToken.getToken(),accessToken.getClaims().getExpiration());
//        if(accountService.findById(userContext.getUsername()).isPresent()){
//            Account account = accountService.findById(userContext.getUsername()).get();
//            account.setAccessToken(accessToken.getToken());
//            account.setRefreshToken(refreshToken.getToken());
//            account.setExpireDt(LocalDateTime.ofInstant(accessToken.getClaims().getExpiration().toInstant(), ZoneId.systemDefault()));
//            accountService.save(account);
//        }

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    /**
     * 저장되어있을 수 있는 임시 인증 관련 데이터를 제거
     *
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
