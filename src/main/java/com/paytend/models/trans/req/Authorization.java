package com.paytend.models.trans.req;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.paytend.models.trans.XmlRequest;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Builder
@Getter
@Setter
@JacksonXmlRootElement(localName = "CardTransaction")
@JsonPropertyOrder(
        {
                "MsgTypId",
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
                "ValidationInformation",
                "ValidationInformation63",
                "KeyMgmtData",
                "ExtendedKeyMngtData",
                "PaymentAcctData",
                "AcptEnvData"
        })

public class Authorization implements XmlRequest {

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
     * bit 62
     */
    @Setter(AccessLevel.NONE)
    private ValidationInformation62 ValidationInformation;

    /**
     * bit 63
     * todo
     */
    @JacksonXmlProperty(localName = "ValidationInformation")
    @Setter(AccessLevel.NONE)
    private VerificationInformation63 ValidationInformation63;

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
    public Authorization() {
    }

    public String toXMLString() {
        return XmlUtility.getInstance().getString(this);
    }


    @Override
    public String toXml() {
        String xml = XmlUtility.getInstance().getString(this);
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }
}
