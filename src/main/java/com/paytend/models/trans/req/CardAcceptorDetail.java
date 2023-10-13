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

    /**
     * 38 bytes
     * Alphanumeric
     * Yes
     */
    String CardAcptNm;
    /**
     * 38 bytes
     * Alphanumeric
     * Yes
     */
    String CardAcptStreetNm;
    /**
     * 21 bytes
     * Alphanumeric
     * Yes
     */
    String CardAcptCityNm;
    /**
     * 3 bytes
     * Alphanumeric
     * Yes
     */
    String CardAcptCtryCd;


    /**
     * 3 bytes
     * Alphanumeric
     * Yes
     */
    String CardAcptRgnCd;

    /**
     * 15 bytes
     * Alphanumeric
     * No
     */
    String CardAcptPostCd;
}
