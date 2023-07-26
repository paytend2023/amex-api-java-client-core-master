package com.paytend.models.seller.req;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author gudongyang
 */
@Builder
@Getter
public class Sale {


    /**
     * channel_indicator_code
     * string, max: 2 REQUIRED
     * This alphanumeric field contains a two-character indicator that identifies the sales channel. Valid values are:
     * DS - Direct Sales Channel.
     * IS - ISO, or other Indirect Sales Channel.
     * RA - Referral Agent or ISV Sales Channel.
     * PS - Payment Facilitator Sub-Channel.
     * WI - Wholesale ISO.
     * RI - Retail ISO.
     * Or other values as approved by American Express.
     */
    String channelIndicatorCode;

    /**
     * channel_name
     * string, max: 25 REQUIRED
     * This alphanumeric field contains the name of the specific sales entity.
     *
     * If the channel_indicator_code is:
     *
     * DS (Direct Sales Channel) – populate the Direct Channel Name.
     * IS, WI, or RI (ISO, Wholesale ISO, or Retail ISO) – populate the name of the ISO.
     * RA (Referral Agent or ISV) – populate the name of the Referral Agent or ISV.
     * PS (Payment Aggregator Sub-Channel) – populate the name of the PSP Payment Facilitator Sub-Channel.
     */
    String channelName;

    /**
     * represent_id
     * string, max: 25 OPTIONAL
     * This alphanumeric field contains the identifier for the sales representative. Populate the Sales Rep ID or Rep Name if no ID.
     */
    String representId;

    /**
     * iso_register_number
     * string, max: 25 REQUIRED
     * This alphanumeric field contains the registration number assigned to the ISO.
     * <p>
     * If the channel_indicator_code is IS, WI, or RI (ISO, Wholesale ISO, or Retail ISO) – populate the registration number assigned to the ISO.
     */

    String isoRegisterNumber;
}
