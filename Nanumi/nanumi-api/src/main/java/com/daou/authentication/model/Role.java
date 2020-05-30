package com.daou.authentication.model;

/**
 * 권한 타입
 * @author pkh879
 */
public enum Role {
    ADMIN, USER;

    public String authority(){
        return "ROLE_" + this.name();
    }
}
