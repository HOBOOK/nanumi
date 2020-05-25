package com.daou.authentication.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author pkh879
 */

public class UserContext {

    @Getter
    private final String username;

    @Getter
    private final List<GrantedAuthority> authorities;

    private UserContext(String username, List<GrantedAuthority> authorities){
        this.username = username;
        this.authorities = authorities;
    }

    public static UserContext create(String username, List<GrantedAuthority> authorities){
        if(StringUtils.isBlank(username)) throw new IllegalArgumentException("유저 이름이 공백입니다. " + username);
        return new UserContext(username, authorities);
    }

}
