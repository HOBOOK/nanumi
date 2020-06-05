package com.daou.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * REST 에러 공통 코드
 * @author pkh879
 */
public enum  ErrorCode {
    GLOBAL(1), // 전역 오류
    AUTHENTICATION(10), // 인증 실패
    JWT_TOKEN_EXPIRED(11), // 토큰 만료
    FAIL_CREATE_ACCOUNT(100), // 계정 추가
    FAIL_READ_ACCOUNT(101), // 계정 조회
    FAIL_UPDATE_ACCOUNT(102), // 계정 수정
    FAIL_DELETE_ACCOUNT(103), // 계정 삭제
    FAIL_CREATE_BAND(200), // 대역 추가
    FAIL_READ_BAND(201), // 대역 조회
    FAIL_UPDATE_BAND(202), // 대역 수정
    FAIL_DELETE_BAND(203), // 대역 삭제
    FAIL_CREATE_ASSIGN_BAND(300), // 대역 할당
    FAIL_READ_ASSIGN_BAND(301), // 할당된 대역 조회
    FAIL_UPDATE_ASSIGN_BAND(302), // 할당된 대역 수정
    FAIL_DELETE_ASSIGN_BAND(303), // 할당된 대역 반납
    FAIL_CREATE_RECEIVING_NUMBER(400), // 수신 번호 추가
    FAIL_READ_RECEIVING_NUMBER(401), // 수신 번호 조회
    FAIL_UPDATE_RECEIVING_NUMBER(402), // 수신 번호 수정
    FAIL_DELETE_RECEIVING_NUMBER(403); // 수신 번호 제거



    private int errorCode;

    private ErrorCode(int errorCode){
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode(){
        return errorCode;
    }

}
