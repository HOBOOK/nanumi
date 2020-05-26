package com.daou.authentication.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import com.daou.authentication.model.token.JwtToken;

/**
 * @author pkh879
 */
@Configuration
@Getter
@Setter
public class JwtSettings {

    /**
     * {@link JwtToken}
     * 만료되는 시간
     */
    private Integer tokenExpirationTime = 60 * 24 * 30;

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
     * 재발급 가능한 시간
     */
    private Integer refreshTokenExpTime = 60;
}
