package com.daou.mapping;

import com.daou.entity.Account;

/**
 * @author Song
 */

public interface AccountMapping {

    Long getSeqNo();

    String getSerialNo();

    String getStartNo();

    String getEndNo();

    String getSvcId();

//    String getId();
//
//    String getSvcNm();

    Account getAccount();
}
