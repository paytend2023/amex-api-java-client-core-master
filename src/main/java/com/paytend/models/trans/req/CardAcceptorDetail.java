package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */


@Getter
@Builder
@Setter
public class CardAcceptorDetail {
    @Tolerate
    public CardAcceptorDetail() {
    }

    String CardAcptNm;
    String CardAcptStreetNm;
    String CardAcptCityNm;
    String CardAcptPostCd;
    String CardAcptRgnCd;
    String CardAcptCtryCd;
}
