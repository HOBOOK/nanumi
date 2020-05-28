package com.daou.authentication.auth.ip;

import com.daou.common.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link AuthenticationProvider} 구현
 * IP 인증을 수행하기 위한 인스턴스
 * @author pkh879
 */
@Component
public class IpAuthenticationProvider implements AuthenticationProvider {

    Set<String> whiteList = new HashSet<String>();

    public IpAuthenticationProvider(){
        whiteList.add("175.115.92.132");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException{
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String userIp = details.getRemoteAddress();
        Logger.write(userIp);
        if(!whiteList.contains(userIp)){
            throw new BadCredentialsException("허용 되지 않은 IP 주소 입니다.");
        }
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
