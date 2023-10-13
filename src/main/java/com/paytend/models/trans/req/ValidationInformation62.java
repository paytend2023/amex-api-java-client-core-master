package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 *
 * 62
 * @author gudongyang
 */
@Getter
@Setter
@Builder
public class ValidationInformation62 {

    private AMEXTransponderTransaction AMEXTransponderTransaction;
    private VISAPS2000Transaction VISAPS2000Transaction;

    @Tolerate
    public ValidationInformation62(){}
    public static class VISAPS2000Transaction {
        String VISAPS2000QlfyTransInd;
        String AddVISASubElemValTxt;
    }

    public static class AMEXTransponderTransaction {
        String SecIdCd;
    }

}

/*  62域结构
<ValidationInformation>
  <AMEXTransponderTransaction>
    <SecIdCd></SecIdCd>
  </AMEXTransponderTransaction>
  <VISAPS2000Transaction>
    <VISAPS2000QlfyTransInd>
    </VISAPS2000QlfyTransInd>
    <AddVISASubElemValTxt>
    </AddVISASubElemValTxt>
  </VISAPS2000Transaction>
 </ValidationInformation>
 */
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