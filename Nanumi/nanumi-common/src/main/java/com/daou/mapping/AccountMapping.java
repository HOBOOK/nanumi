package com.daou.mapping;

import com.daou.entity.Account;
import com.daou.entity.Band;

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
