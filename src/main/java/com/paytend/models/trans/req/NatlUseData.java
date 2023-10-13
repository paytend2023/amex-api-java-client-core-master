package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

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
 * • Mandatory—When National ID value isrequired
 * • Conditional—Transaction originated using a mobile device
 * • Not used—All other transactions
 * Global — All regions
 * Mandatory — Third Party Processors, OptBlue Participants, Bill Payment Providers and/or Vendors must be certified to
 * pass data in the sub-elements related to this container element. After certification, all Merchant-provided data must be forwarded.
 * <p>
 * This container element supports three types of transaction processing: Payment Facilitator, OptBlue Participant,
 * Bill Pay Provider Data and Payment Token. These three types of transactions can be sent together or separately.
 * <p>
 * This container element consists of five sub-elements listed in the following example.
 * Payment Facilitator, OptBlue Participants and Bill Payment Providers
 * <p>
 * The sub-elements CardAcptSellerId, CardAcptEmailIAddr and CardAcptPhoneNbr support Payment Facilitator, OptBlue Participant and Bill Pay Provider data.
 * These sub-elements should be used in conjunction with the Bit 43, CardAcceptorDetail, Payment Facilitator and OptBlue Participant format.
 *
 * @author gudongyang
 */
@Builder
@Getter
public class NatlUseData {
    String CardAcptSellerId;
    String CardAcptEmailIAddr;
    String CardAcptPhoneNbr;
    String TokenReqId;
    String Last4PrimAcctNbrRtrnCd;
    String OriginalTransId;
    String TrmnlClassificationCd;
    String NatlIdentfctnDocmnt;

    @Tolerate
    public NatlUseData() {
    }
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
