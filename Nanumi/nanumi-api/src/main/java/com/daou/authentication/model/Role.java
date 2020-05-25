package com.daou.authentication;

public enum Role {
    ADMIN, USER;

    public String authority(){
        return" ROLE_" + this.name();
    }
}
