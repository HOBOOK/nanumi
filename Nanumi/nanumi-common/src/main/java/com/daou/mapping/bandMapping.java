package com.daou.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * @author Song
 */

public interface bandMapping {

    Long getSeqNo();

    String getSerialNo();

    String getReceiveNo();

    LocalDate getCreateDate();

    LocalDate getUpdateDate();

    String getSvcId();

    String getSvcUserId();

    default com.daou.types.category getCategory() {
        return getBandCategory();
    }

    //band table의 category column 조회
    @JsonIgnore
    com.daou.types.category getBandCategory();
}
