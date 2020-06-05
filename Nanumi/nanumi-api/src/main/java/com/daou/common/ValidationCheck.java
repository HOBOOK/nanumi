package com.daou.common;

import com.daou.entity.Band;
import org.springframework.stereotype.Component;

/**
 * @author pkh879
 * @author song
 * @author jsw
 * 공통 유효성 체크 클래스
 */
@Component
public class ValidationCheck{

    //번호 대역 길이, 범위 체크
    public boolean validBandRange(Band band){

        int startNo = Integer.parseInt(band.getStartNo());
        int endNo = Integer.parseInt(band.getEndNo());

        if(band.getStartNo().length() != 4 || band.getEndNo().length() != 4)
            return false;
        if(startNo > endNo)
            return false;
        if(startNo < 0 && endNo > 9999)
            return false;

        return true;
    }

}
