package com.daou.common;

import com.daou.entity.Band;
import com.daou.entity.BandAssign;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author pkh879
 * @author song
 * @author jsw
 * 공통 유효성 체크 클래스
 */
@Component
public class ValidationCheck{

    //번호 대역 길이, 범위 체크
    public boolean validBandRange(String start, String end){

        int startNo = Integer.parseInt(start);
        int endNo = Integer.parseInt(end);

        if(start.length() != 4 || end.length() != 4)
            return false;
        if(startNo > endNo)
            return false;
        if(startNo < 0 && endNo > 9999)
            return false;

        return true;
    }

    public boolean validBandAssignRange(Optional<Band> band , BandAssign bandAssign){

        if(!validBandRange(bandAssign.getStartNo(), bandAssign.getEndNo())){
            return false;
        }

        int startNo = Integer.parseInt(band.get().getStartNo());
        int endNo = Integer.parseInt(band.get().getEndNo());
        int inputStartNo = Integer.parseInt(bandAssign.getStartNo());
        int inputEndNo = Integer.parseInt(bandAssign.getEndNo());

        if( (startNo > inputStartNo) || (endNo < inputEndNo) )
            return false;

        return true;
    }


}
