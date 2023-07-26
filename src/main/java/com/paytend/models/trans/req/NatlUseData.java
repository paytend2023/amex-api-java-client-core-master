package com.paytend.models.trans.req;

/**
 * ISO 8583 Bit # / Data Element: Data Length:
 * Data Element Type:
 * Constant:
 * Data Element Requirement:
 * Certification Requirement:
 * Description:
 * 164 April 2023
 * NatlUseData
 * Bit 60 / National Use Data N/A
 * N/A
 * N/A
 * • Global — All regions
 * • Mandatory—PaymentFacilitators,OptBlueParticipants and Bill Payment Providers
 * • Mandatory—PaymentTokentransactionswheretheToken Requester ID (TRID) is requested
 * • Mandatory—WhenNationalIDvalueisrequired
 * • Conditional—Transactionoriginatedusingamobiledevice • Notused—Allothertransactions
 * Global — All regions
 * Mandatory — Third Party Processors, OptBlue Participants, Bill Payment Providers and/or Vendors must be certified to pass data in the sub-elements related to this container element. After certification, all Merchant-provided data must be forwarded.
 * This container element supports three types of transaction processing: Payment Facilitator, OptBlue Participant, Bill Pay Provider Data and Payment Token. These three types of transactions can be sent together or separately.
 * This container element consists of five sub-elements listed in the following example.
 * Payment Facilitator, OptBlue Participants and Bill Payment Providers
 * The sub-elements CardAcptSellerId, CardAcptEmailIAddr and CardAcptPhoneNbr support Payment Facilitator, OptBlue Participant and Bill Pay Provider data. These sub-elements should be used in conjunction with the Bit 43, CardAcceptorDetail, Payment Facilitator and OptBlue Participant format.
 * @author gudongyang
 */
public class NatlUseData {
    String CardAcptSellerId;
    String CardAcptEmailIAddr;
    String CardAcptPhoneNbr;
    String TokenReqId;
    String Last4PrimAcctNbrRtrnCd;
    String OriginalTransId;
    String TrmnlClassificationCd;
    String NatlIdentfctnDocmnt;
}


/*
<NatlUseData>
    <CardAcptSellerId></CardAcptSellerId>
    <CardAcptEmailIAddr></CardAcptEmailIAddr>
    <CardAcptPhoneNbr></CardAcptPhoneNbr>
    <TokenReqId></TokenReqId>
    <Last4PrimAcctNbrRtrnCd></Last4PrimAcctNbrRtrnCd>
    <OriginalTransId></OriginalTransId>
    <TrmnlClassificationCd></TrmnlClassificationCd>
    <NatlIdentfctnDocmnt></NatlIdentfctnDocmnt>
</NatlUseData>

 */
