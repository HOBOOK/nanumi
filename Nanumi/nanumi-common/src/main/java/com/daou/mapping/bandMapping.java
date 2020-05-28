package com.daou.mapping;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public interface bandMapping {

    Long getSeqNo();

    String getSerialNo();

    String getReceiveNo();

    LocalDate getUpdateDate();

    String getUserId();

    default com.daou.types.category getCategory() {
        return getBandCategory();
    }

    @JsonIgnore
    com.daou.types.category getBandCategory();
}
