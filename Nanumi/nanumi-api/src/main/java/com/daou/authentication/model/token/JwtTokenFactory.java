package com.daou.authentication.model.token;

import com.daou.authentication.config.JwtSettings;
import com.daou.authentication.model.Scopes;
import com.daou.authentication.model.UserContext;
import org.apache.commons.lang3.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * {@link JwtToken}을 만드는데 항상 사용되는 팩토리 클래스
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings jwtSettings;

    @Autowired
    public JwtTokenFactory(JwtSettings jwtSettings){
        this.jwtSettings = jwtSettings;
    }

    /**
     * 새로운 JWT 토큰을 발행하기 위한 메소드
     * @param userContext
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext){
        if(StringUtils.isBlank(userContext.getUsername()))
            throw new IllegalArgumentException("유저이름 없이 JWT 토큰을 만들 수 없습니다.");
        if(userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
            throw new IllegalArgumentException("사용자의 권한이 없습니다.");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();

        String token =Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtSettings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(jwtSettings.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext){
        if(StringUtils.isBlank(userContext.getUsername()))
            throw new IllegalArgumentException("유저 이름 없이 JWT 토큰을 만들 수 없습니다.");

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        String token =Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtSettings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(jwtSettings.getRefreshTokenExpTime())
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getTokenSigningKey())
                .compact();
        return new AccessJwtToken(token, claims);
    }


}
