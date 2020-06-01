package com.daou.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 유틸리티 함수 모음
 * @author pkh879
 */

@Component
public class NanumiUtil {
    private Base64.Decoder base64Decoder = Base64.getDecoder();
    private final String HEADER_PREFIX = "bearer ";
    @Autowired private ObjectMapper objectMapper;

    /**
     * JWT 토큰의 헤더에서 API 요청자를 찾는 함수
     * @param authorizationHeader
     * @return
     * @throws IllegalArgumentException
     */
    public String base64DecodeForRequestId(String authorizationHeader) throws IllegalArgumentException {
        String decodedText = "";
        if(authorizationHeader.toLowerCase().contains(HEADER_PREFIX))
            authorizationHeader = authorizationHeader.substring(7);
        try{
            Map<String, Object> map = objectMapper.readValue(new String(base64Decoder.decode(authorizationHeader.split("\\.")[1])), HashMap.class);
            decodedText = map.get("sub").toString();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return decodedText;
    }


    /**
     * 숫자값을 '0000' 문자열 포맷으로 변경하는 함수
     * @param num
     * @return
     */
    public String formatNumber(int num){
        return new DecimalFormat("0000").format(num);
    }
}
