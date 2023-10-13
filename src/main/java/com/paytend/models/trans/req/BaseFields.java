package com.paytend.models.trans.req;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @author Sunny
 * @create 2023/8/26 10:20
 */
@Getter
@Setter
@SuperBuilder
public class BaseFields {

    protected String MsgTypId;
    /**
     * bit 2
     */
    protected String CardNbr;
    /**
     * bit 3
     */
    protected String TransProcCd;
    /**
     * bit 4
     */
    protected String TransAmt;
    /**
     * bit 7
     */

    protected String XmitTs;
    /**
     * bit 11
     */
    protected String MerSysTraceAudNbr;
    /**
     * YYMMDDhhmmss
     * bit 12
     */
    protected String TransTs;
    /**
     * YYMM.
     * bit 13
     */
    protected String CardEffDt;
    /**
     * YYMM
     * bit 14
     */
    protected String CardExprDt;
    /**
     * bit 19
     */
    protected String AcqInstCtryCd;
    /**
     * bit 22
     */
    protected PointOfServiceData PointOfServiceData;


    /**
     * bit 24
     */
    protected String FuncCd;

    /**
     * bit 25
     */
    protected String MsgRsnCd;

    /**
     * bit 26
     */
    protected String MerCtgyCd;
    /**
     * bit 27
     */
    protected String AprvCdLgth;
    /**
     * bit 31
     */
    protected String TransId;
    /**
     * bit 32
     */
    protected String AcqInstIdCd;
    /**
     * bit 33
     */
    protected String FwdInstIdCd;
    /**
     * bit 35
     */
    protected String CardTrack2Data;
    /**
     * bit 37
     */
    protected String RtrvRefNbr;
    /**
     * bit 41
     */
    protected String MerTrmnlId;
    /**
     * bit 42
     */
    protected CardAcceptorIdentification CardAcceptorIdentification;
    /**
     * bit 43
     */
    protected CardAcceptorDetail CardAcceptorDetail;
    /**
     * bit 45
     */
    protected String CardTrack1Data;
    /**
     * bit 47
     */
    protected AdditionalDataNational AdditionalDataNational;
    /**
     * bit 48
     */
    protected AdditionalDataPrivate AdditionalDataPrivate;
    /**
     * bit 49
     */
    protected String TransCurrCd;
    /**
     * bit 52
     */
    protected String PINDataTxt;
    /**
     * bit 55
     */
    protected String ICCSDataTxt;
    /**
     * bit 60
     */
    protected NatlUseData NatlUseData;
    /**
     * bit 61
     */
    protected SecureAuthenticationSafeKey SecureAuthenticationSafeKey;
    /**
     * bit 96
     */
    protected KeyMgmtData KeyMgmtData;
    /**
     * bit 111
     */
    protected ExtendedKeyMngtData ExtendedKeyMngtData;
    /**
     * bit 112
     */
    protected PaymentAcctData PaymentAcctData;
    /**
     * bit 113
     */
    protected AcptEnvData AcptEnvData;


    @Tolerate
    protected BaseFields() {
    }
}
