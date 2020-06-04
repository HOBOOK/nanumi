package com.daou.mapping;

import com.daou.types.category;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * @author Song
 */

public interface bandMapping {

    Long getSeqNo();

    String getSerialNo();

    String getReceiveNo();

    LocalDate getUpdateDate();

    String getUserId();

    default category getCategory() {
        return getBandCategory();
    }

    //band table의 category column 조회
    @JsonIgnore
    category getBandCategory();
}
