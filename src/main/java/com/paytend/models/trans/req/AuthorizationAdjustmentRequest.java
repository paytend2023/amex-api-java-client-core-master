package com.paytend.models.trans.req;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.errorprone.annotations.SuppressPackageLocation;
import com.paytend.models.trans.XmlRequest;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Sunny
 * @create 2023/8/26 10:14
 */

@Getter
@Setter
@JacksonXmlRootElement(localName = "CardTransaction")
@SuperBuilder
@JsonPropertyOrder(
        {
                "MsgTypId",
                "CardNbr",
                "TransProcCd",
                "TransAmt",
                "XmitTs",
                "TransTs",
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
public class AuthorizationAdjustmentRequest extends BaseFields implements XmlRequest {
    /**
     * bit 30
     * authorization adjustment 交易使用
     * authorization 不用上送
     * Data Length: 24 bytes, fixed
     * <p>
     * <p>
     * Description: This data element contains the Original Transaction Amount that was previously approved by
     * American Express.
     * Positions 1-12 of this data element are the original transaction amount from Bit 4, TransAmt
     * in the corresponding Authorization Response (1110) message.
     * Positions 13-24 are zero filled and reserved for future use.
     * This data element is mandatory for processing this message, and it will be preserved and returned in
     * the response message without alteration
     */
    String OrigTransAmt;


    /**
     * Description:
     * Note: This requirement applies to the container element and all related sub-elements.
     * This container element holds the sub-elements listed in the following example;
     * and the sub-elements must contain the same values used in the original Authorization Request (1100) message.
     */
    OriginalDataElements OriginalDataElements;


    public String toXMLString() {
        return XmlUtility.getInstance().getString(this);
    }

    @Override
    public String toXml() {
        String xml = XmlUtility.getInstance().getString(this);
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }
}
