package com.daou.authentication.model;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author pkh879
 */
@Getter
public class UserContext {

    private final String username;
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
