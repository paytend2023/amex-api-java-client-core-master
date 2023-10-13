package com.paytend.models.trans.req;
/*
 <KeyMgmtData>
  <PrimIdCd></PrimIdCd>
  <SecIdCd></SecIdCd>
  <SessKeyPINChkValTxt>
  </SessKeyPINChkValTxt>
  <SessKeyMACChkValTxt>
  </SessKeyMACChkValTxt>
  <SessKeyDATAChkValTxt>
  </SessKeyDATAChkValTxt>
</KeyMgmtData>
*/
/**
 * @author gudongyang
 */
public class KeyMgmtData {
    String PrimIdCd;
    /**
     * This data element contains the Merchant-captured, Security Identification Code associated with processing Authorization Request (1100) messages initiated by electronic,
     * radio-frequency devices (transponders or RFIDs; e.g., SpeedpassTM). This unique, transponder-Issuer assigned code corresponds to a customer-designated form of payment and
     * Card Number on the transponder-Issuer's system.
     * For transactions initiated by an electronic, radio-frequency device (transponder or RFID, e.g., Speedpass), SecIdCd may be used alone or in conjunction with PointOfServiceData,
     * CardPresentCd, value “W”. Alternately, PointOfServiceData, CardPresentCd, value “W” may be used without a Security Identification Code entered in SecIdCd.
     * Ideally, both items are transmitted. For more details, see page 72.
     * Note: Transactions containing Transponder data are considered Card Not Present transactions.
     */
    String SecIdCd;
    String SessKeyPINChkValTxt;
    String SessKeyMACChkValTxt;
    String SessKeyDATAChkValTxt;
}

