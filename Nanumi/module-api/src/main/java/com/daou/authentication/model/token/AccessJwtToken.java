package com.daou.authentication.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

/**
 * JWT 토큰 표현
 * @author pkh879
 */
public final class AccessJwtToken implements JwtToken{
    private final String rawToken;
    @JsonIgnore private Claims claims;

    protected AccessJwtToken(final String token, Claims claims){
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken(){
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
