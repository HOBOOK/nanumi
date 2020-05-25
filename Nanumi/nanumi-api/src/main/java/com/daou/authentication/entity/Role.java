package com.daou.authentication.entity;

public enum Role {
    ADMIN, USER;

    public String authority(){
        return" ROLE_" + this.name();
    }
}
