package com.daou.authentication.auth.jwt.verifier;

/**
 * @author pkh879
 */
public interface TokenVerifier {
    public boolean verify(String jti);
}
