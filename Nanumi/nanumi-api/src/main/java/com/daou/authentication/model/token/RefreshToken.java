package com.daou.authentication.model.token;

import com.daou.authentication.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.List;
import java.util.Optional;

/**
 * 토큰 새로고침
 * @author pkh879
 */
@SuppressWarnings("unchecked")
public class RefreshToken implements JwtToken{
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims){
        this.claims = claims;
    }

    /**
     * 토큰 생성, 새로 고침
     * @param token
     * @param signingKey
     * @return
     */
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey){
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<String> scopes = claims.getBody().get("scopes", List.class);
        if(scopes == null || scopes.isEmpty() || !scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope)).findFirst().isPresent()){
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken(){
        return null;
    }

    public Jws<Claims> getClaims(){
        return claims;
    }
    public String getJti(){
        return claims.getBody().getId();
    }

    public String getSubject(){
        return claims.getBody().getSubject();
    }
}
