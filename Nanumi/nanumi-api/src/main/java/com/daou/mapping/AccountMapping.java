package com.daou.mapping;

import com.daou.entity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

/**
 * @author Song
 */

public interface AccountMapping {

    Long getSeqNo();

    String getSerialNo();

    String getStartNo();

    String getEndNo();

    LocalDateTime getAssignDt();

    LocalDateTime getExpireDt();

    String getSvcId();

    byte getStatus();

//    String getId();
//
//    String getSvcNm();

    default String getAccountId() {
        return getAccount().getId();
    }

    default String getAccountSvcNm() {
        return getAccount().getSvcNm();
    }

    //band table의 category column 조회
    @JsonIgnore
    Account getAccount();
}
