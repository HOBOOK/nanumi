package com.daou.mapping;

import com.daou.entity.Band;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

/**
 * @author Song
 */

public interface AssignMapping {

    Long getSeqNo();

    String getSerialNo();

    String getStartNo();

    String getEndNo();

    String getSvcId();

    Band getBand();

}
