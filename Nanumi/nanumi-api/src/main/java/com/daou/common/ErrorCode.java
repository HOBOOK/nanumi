package com.daou.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * REST 에러 공통 코드
 * @author pkh879
 */
public enum  ErrorCode {
    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);

    private int errorCode;

    private ErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode(){
        return errorCode;
    }

}
