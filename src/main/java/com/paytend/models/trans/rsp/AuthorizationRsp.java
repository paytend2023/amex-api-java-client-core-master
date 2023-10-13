package com.paytend.models.trans.rsp;

import com.paytend.models.trans.req.Authorization;
import com.paytend.models.trans.req.BaseFields;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */

@Getter
@Setter
 public class AuthorizationRsp extends BaseFields {

    /**
     * bit 38
     */
    String transAprvCd;

    /**
     * 000 Approved
     * 001 Approve with ID
     * 002 Partial Approval (Prepaid Cards only)
     * 100 Deny
     * 101 Expired Card / Invalid Expiration Date
     * 106 Exceeded PIN attempts
     * 109 Invalid merchant
     * 110 Invalid amount
     * 111 Invalid account / Invalid MICR (Travelers Cheque)
     * 115 Requested function not supported
     * 116 Not Sufficient Funds
     * 117 Invalid PIN
     * 119 Cardmember not enrolled / not permitted
     * 121 Limit Exceeded
     * 122 Invalid card security code (a.k.a., CID, 4DBC, 4CSC)
     * 125 Invalid effective date
     * 130 Additional customer identification required 181 Format error
     * 183 Invalid currency code
     * 187 Deny - New card issued
     * 189 Deny - Canceled or Closed Merchant/SE
     * 190 National ID Mismatch
     * 193 Invalid Country Code
     * 200 Deny - Pick up card
     * 900 Accepted - ATC Synchronization
     * 909 System Malfunction (Cryptographic Error) 912 Issuer not available
     * 977 Invalid Payment Plan
     * 978 Invalid Payment Times
     * bit 39
     */
    String transActCd;

    @Override
    public String toString() {
        return "AuthorizationRsp{" +
                "transAprvCd='" + transAprvCd + '\'' +
                ", transActCd='" + transActCd + '\'' +
                ", TransId='" + TransId + '\'' +
                ", MerSysTraceAudNbr='" + MerSysTraceAudNbr + '\'' +

                '}';
    }
}
