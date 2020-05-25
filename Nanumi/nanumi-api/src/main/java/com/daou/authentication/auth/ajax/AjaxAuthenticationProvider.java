package com.daou.authentication.auth.ajax;

import com.daou.authentication.model.UserContext;
import com.daou.entity.TempAccount;
import com.daou.service.DatabaseUserService;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pkh879
 */
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final DatabaseUserService userService;

    @Autowired
    public AjaxAuthenticationProvider(final DatabaseUserService userService, final BCryptPasswordEncoder encoder){
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        Assert.notNull(authentication, "인증 정보를 받지못했습니다.");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        TempAccount tempAccount = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 없습니다 : " + username));

        if(!encoder.matches(password, tempAccount.getPassword())){
            throw new BadCredentialsException("사용자 인증정보가 일치하지 않습니다.");
        }
        if(tempAccount.getRoles() == null) throw new InsufficientAuthenticationException("사용자의 권한 정보가 부여되지 않았습니다.");
        List<GrantedAuthority> authorities = tempAccount.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(tempAccount.getUsername(), authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
