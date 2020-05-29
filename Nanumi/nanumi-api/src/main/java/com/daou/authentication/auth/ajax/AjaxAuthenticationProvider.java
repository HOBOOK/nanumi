package com.daou.authentication.auth.ajax;

import com.daou.authentication.Role;
import com.daou.authentication.model.UserContext;
import com.daou.common.Logger;
import com.daou.entity.Account;
import com.daou.service.AccountService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link AuthenticationProvider} 구현
 * Ajax 인증을 수행하는 클래스
 * @author pkh879
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final AccountService accountService;

    @Autowired
    public AjaxAuthenticationProvider(final AccountService accountService, final BCryptPasswordEncoder encoder){
        this.accountService = accountService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        Assert.notNull(authentication, "인증 정보를 받지못했습니다.");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();



        Account account = accountService.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("사용자 정보가 없습니다 : " + username));
        if(!encoder.matches(password, account.getPwd())){
            throw new BadCredentialsException("사용자 인증정보가 일치하지 않습니다.");
        }

        if(account.getRoleCd() == null) throw new InsufficientAuthenticationException("사용자의 권한 정보가 부여되지 않았습니다.");
        List<Role> roles = new ArrayList<>();
        if(account.getRoleCd().equals("ADMIN"))
            roles.add(Role.ADMIN);
        else
            roles.add(Role.USER);
        List<GrantedAuthority> authorities = roles.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(account.getId(), authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
