package com.paytend.models.trans.req;

/**
 * @author gudongyang
 */
public class VerificationInformation63 {
    String FormNbr;
    String ServId;
    String ReqTypId;
    String MICRNbr;
    String TCNbr;
    AddressVerificationData AddressVerificationData;
    public class AddressVerificationData {
        String CMBillPostCd;
        String CMBillAddr;
        String CMFirstNm;
        String CMLastNm;
        String CMPhoneNbr;
        String ShipToPostCd;
        String ShipToAddr;
        String ShipToFirstNm;
        String ShipToLastNm;
        String ShipToPhoneNbr;
        String ShipToCtryCd;
    }
}


/*
63域结构
<VerificationInformation>
<FormNbr></FormNbr>
 <ServId></ServId>
 <ReqTypId></ReqTypId>
  <MICRNbr></MICRNbr>
  <TCNbr></TCNbr>
 <AddressVerificationData>
    <CMBillPostCd></CMBillPostCd>
    <CMBillAddr></CMBillAddr>
    <CMFirstNm></CMFirstNm>
    <CMLastNm></CMLastNm>
    <CMPhoneNbr></CMPhoneNbr>
    <ShipToPostCd></ShipToPostCd>
    <ShipToAddr></ShipToAddr>
    <ShipToFirstNm></ShipToFirstNm>
    <ShipToLastNm></ShipToLastNm>
    <ShipToPhoneNbr></ShipToPhoneNbr>
    <ShipToCtryCd></ShipToCtryCd>
 </AddressVerificationData>
</VerificationInformation>
 */
