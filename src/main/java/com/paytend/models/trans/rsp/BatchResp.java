package com.paytend.models.trans.rsp;

import io.aexp.api.client.core.utils.XmlUtility;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sunny
 * @create 2023/9/2 09:04
 */
@Getter
@Setter
@ToString
public class BatchResp {
    /**
     * <BatchAdminResponse>
     * <Version>12010000</Version>
     * <MerId>8127921740</MerId>
     * <MerTrmnlId>80000011</MerTrmnlId>
     * <BatchID>100005</BatchID>
     * <BatchStatus>001</BatchStatus>
     * <BatchStatusText>Close</BatchStatusText>
     * </BatchAdminResponse>
     */

    String version;
    String merId;
    String merTrmnlId;
    String batchID;
    String batchStatus;
    String batchStatusText;


    public static BatchResp createByXml(String xml) {
        return XmlUtility.getInstance().readFromXML(xml, BatchResp.class);
    }


}
