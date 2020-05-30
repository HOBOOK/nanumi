package com.daou.authentication.auth.ip;

import com.daou.authentication.exceptions.AuthMethodNotSupportedException;
import com.daou.common.Logger;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 등록된 IP 주소만 접근 가능한 IP 화이트 리스트
 * @author pkh879
 */
@Component
public class IpWhiteList {
    private final Set<String> ipList = new HashSet<>();

    IpWhiteList(){
        ipList.add("127.0.0.1");
        ipList.add("172.21.25.66");
        ipList.add("172.21.25.67");
        ipList.add("172.21.25.75");
    }
    public boolean check(HttpServletRequest request){
        return ipList.contains(getClientIpAddress(request));
    }

    /**
     * 인증 요청 클라이언트의 IP주소를 얻는 메소드
     * @param request
     * @return
     */
    private String getClientIpAddress(HttpServletRequest request){
        System.setProperty("java.net.preferIPv4Stack","true");
        String ip = request.getHeader("X-Forwarded-For");
        if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip==null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
