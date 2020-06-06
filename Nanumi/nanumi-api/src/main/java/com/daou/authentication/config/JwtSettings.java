package com.daou.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import com.daou.authentication.model.token.JwtToken;

/**
 * Jwt토큰 설정 클래스
 * @author pkh879
 */
@Configuration
@Getter
@Setter
public class JwtSettings {

    /**
     * {@link JwtToken}
     * 토큰 만료 시간 7일
     */
    private Integer tokenExpirationTime = 1;

    /**
     * 토큰 발행자
     */
    private String tokenIssuer = "nanumi";

    /**
     * {@link JwtToken}
     * 서명할 때 사용되는 키
     */
    private String tokenSigningKey = "nanumiKey";

    /**
     * {@link JwtToken}
     * 재발급 토큰 만료시간 30일
     */
    private Integer refreshTokenExpTime = 60 * 24 * 30;
}
