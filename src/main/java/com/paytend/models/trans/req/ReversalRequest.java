package com.paytend.models.trans.req;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.paytend.models.trans.XmlRequest;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sunny
 * @create 2023/8/25 15:16
 */
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
@Setter
@Getter
public class ReversalRequest extends BaseFields implements XmlRequest {

    /**
     * Description:
     * Note: This requirement applies to the container element and all related sub-elements.
     * This container element holds the sub-elements listed in the following example;
     * and the sub-elements must contain the same values used in the original Authorization Request (1100) message.
     */
    OriginalDataElements OriginalDataElements;

    @Override
    public String toXml() {
        String xml = XmlUtility.getInstance().getString(this);
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }

    public ReversalRequest(OriginalDataElements originalDataElements, Authorization authorization) {
        BeanUtil.copyProperties(authorization, this);
        this.OriginalDataElements = originalDataElements;
    }
}
