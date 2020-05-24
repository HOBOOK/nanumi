package com.daou.authentication.model;

/**
 * 스코프
 * @author pkh879
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String authority(){
        return "ROLE_" + this.name();
    }
}
