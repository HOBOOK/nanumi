package com.daou.authentication;

/**
 * 권한 타입
 * @author pkh879
 */
public enum Role {
    ADMIN, USER;

    public String authority(){
        return" ROLE_" + this.name();
    }
}
