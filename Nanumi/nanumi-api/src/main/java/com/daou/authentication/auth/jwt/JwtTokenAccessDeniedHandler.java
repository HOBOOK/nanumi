package com.daou.authentication.auth.jwt;

import com.daou.common.ErrorCode;
import com.daou.common.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 계정의 권한에 따른 접근 제한 핸들러
 * @author pkh879
 */

@Component
public class JwtTokenAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired private ObjectMapper mapper;
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(httpServletResponse.getWriter(), ErrorResponse.of("Unauthorized authority.", ErrorCode.AUTHENTICATION, HttpStatus.NOT_ACCEPTABLE));
    }
}
