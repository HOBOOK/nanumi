package com.daou.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 커스텀 문자 디코딩 하기 위한 클래스
 * @author pkh879
 */

@Component
public class NanumiDecoder {
    private Base64.Decoder base64Decoder = Base64.getDecoder();
    private final String HEADER_PREFIX = "bearer ";
    @Autowired private ObjectMapper objectMapper;

    public String base64Decode(String authorizationHeader) throws IllegalArgumentException {
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
}
