package com.paytend.models.trans.req;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Builder
@Getter
@JacksonXmlRootElement(localName = "CardTransaction")
@JsonPropertyOrder(
        {"MsgTypId",
                "CardNbr",
                "TransProcCd",
                "TransAmt",
                "XmitTs",
                "TransTs",
                "CardEffDt",
                "AcqInstCtryCd",
                "PointOfServiceData",
                "FuncCd",
                "MsgRsnCd",
                "MerCtgyCd",
                "AprvCdLgth",
                "TransId",
                "AcqInstIdCd",
                "FwdInstIdCd",
                "CardTrack2Data",
                "RtrvRefNbr",
                "MerTrmnlId",
                "CardAcceptorIdentification",
                "CardAcceptorDetail",
                "CardTrack1Data",
                "AdditionalDataNational",
                "AdditionalDataPrivate",
                "TransCurrCd",
                "PINDataTxt",
                "ICCSDataTxt",
                "NatlUseData",
                "SecureAuthenticationSafeKey",
                "ValidationInformation62",
                "ValidationInformation63",
                "KeyMgmtData",
                "ExtendedKeyMngtData",
                "PaymentAcctData",
                "AcptEnvData"
        })

public class Authorization {

    String MsgTypId;

    /**
     * bit 2
     */
    String CardNbr;

    /**
     * bit 3
     */
    String TransProcCd;
    /**
     * bit 4
     */
    String TransAmt;
    /**
     * bit 7
     */

    String XmitTs;

    /**
     * bit 11
     */
    String MerSysTraceAudNbr;

    /**
     * YYMMDDhhmmss
     * bit 12
     */
    String TransTs;

    /**
     * YYMM.
     * bit 13
     */
    String CardEffDt;

    /**
     * YYMM
     * bit 14
     */
    String CardExprDt;

    /**
     * bit 19
     */
    String AcqInstCtryCd;

    /**
     * bit 22
     */
    PointOfServiceData PointOfServiceData;

    /**
     * bit 24
     */
    String FuncCd;

    /**
     * bit 25
     */
    String MsgRsnCd;

    /**
     * bit 26
     */
    String MerCtgyCd;

    /**
     * bit 27
     */
    String AprvCdLgth;

    /**
     * bit 31
     */
    String TransId;

    /**
     * bit 32
     */
    String AcqInstIdCd;

    /**
     * bit 33
     */
    String FwdInstIdCd;

    /**
     * bit 35
     */
    String CardTrack2Data;

    /**
     * bit 37
     */
    String RtrvRefNbr;

    /**
     * bit 41
     */
    String MerTrmnlId;

    /**
     * bit 42
     */
    CardAcceptorIdentification CardAcceptorIdentification;

    /**
     * bit 43
     */
    CardAcceptorDetail CardAcceptorDetail;

    /**
     * bit 45
     */
    String CardTrack1Data;

    /**
     * bit 47
     */
    AdditionalDataNational AdditionalDataNational;

    /**
     * bit 48
     */
    AdditionalDataPrivate AdditionalDataPrivate;

    /**
     * bit 49
     */
    String TransCurrCd;

    /**
     * bit 52
     */
    String PINDataTxt;

    /**
     * bit 55
     */
    String ICCSDataTxt;

    /**
     * bit 60
     */
    NatlUseData NatlUseData;

    /**
     * bit 61
     */
    SecureAuthenticationSafeKey SecureAuthenticationSafeKey;

    /**
     * bit 62
     */
    ValidationInformation62 ValidationInformation;

    /**
     * bit 63
     * todo
     */
    @JacksonXmlProperty(localName = "ValidationInformation")
    VerificationInformation63 ValidationInformation63;

    /**
     * bit 96
     */
    KeyMgmtData KeyMgmtData;

    /**
     * bit 111
     */
    ExtendedKeyMngtData ExtendedKeyMngtData;

    /**
     * bit 112
     */
    PaymentAcctData PaymentAcctData;
    /**
     * bit 113
     */
    AcptEnvData AcptEnvData;

    @Tolerate
    public Authorization() {
    }
}
