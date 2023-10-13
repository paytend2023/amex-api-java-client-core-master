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
     * Data Length: 38 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: Yes
     * Description:
     * This field contains the Merchant/Seller name that appears on the storefront and/or customer receipts,
     * commonly referred to as the “DBA” (Doing Business As) name.
     * This information will become part of the descriptive bill on the Cardmember's statement.
     * This name must be easily recognized by the Cardmember to avoid unnecessary inquiries.
     * If the name is more than 38 characters, use proper and meaningful abbreviation, when possible. Do not truncate.
     * Payment Facilitators and Bill Payment Providers Only
     * Payment Facilitators and Bill Payment Providers must use their Payment Facilitator or Bill Pay Provider business name
     * in conjunction with the name of the actual seller for whom the transaction is submitted.
     *
     *
     */
    String CardAcptNm;
    /**
     * Data Length: 38 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: Yes
     * Description:
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
