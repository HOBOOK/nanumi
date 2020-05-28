package com.daou.authentication.model.token;

import com.daou.authentication.exceptions.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * @author pkh879
 */
public class RawAccessJwtToken implements JwtToken{
    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    @Getter
    private String token;

    public RawAccessJwtToken(String token){
        this.token = token;
    }

    /**
     * JWT 토큰 서명을 분석하고 유효성을 검증
     * @param signingKey
     * @return
     */
    public Jws<Claims> parseClaims(String signingKey){
        try{
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex){
            logger.error("JWT 토큰이 적합하지 않습니다.", ex);
            throw new BadCredentialsException("JWT 토큰이 적합하지 않습니다 : ", ex);
        } catch (ExpiredJwtException ex){
            logger.info("JWT 토큰이 만료되었습니다.", ex);
            throw new JwtExpiredTokenException(this, "JWT 토큰이 만료되었습니다.", ex);
        }
    }
}
