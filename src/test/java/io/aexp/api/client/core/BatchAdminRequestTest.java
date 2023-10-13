package io.aexp.api.client.core;

import com.paytend.models.trans.req.BatchAdminRequest;
import com.paytend.models.trans.req.CardAcceptorDetail;
import com.paytend.models.trans.req.Header;
import com.paytend.models.trans.req.TransCommUtils;
import org.junit.Test;

import java.util.Map;

/**
 * @author Sunny
 * @create 2023/8/21 21:20
 */
public class BatchAdminRequestTest {
    String url = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";

    CardAcceptorDetail.CardAcceptorDetailBuilder
            cardAcceptorDetailBuilder = CardAcceptorDetail.builder()
            .CardAcptNm("PAYTEND EUROPE UAB")
            .CardAcptStreetNm("Vilnius City sav")
            .CardAcptCityNm("Vilnius")
            .CardAcptPostCd("01113")
            .CardAcptRgnCd("58")
            .CardAcptCtryCd("440");

    Map<String, String> headersBatchAdmin = Header.builder()
            .origin("Paytend")
            .country("276")
            .region("EMEA")
            .message("GFSG XML BAR")
            .merchNbr("3285220521")
            .rtInd("015")
            .host(url)
            .build().buildHeaders();

    Map<String, String> dataCaptureRequest = Header.builder()
            .origin("Paytend")
            .country("276")
            .region("EMEA")
            .message("GFSG XML DCR")
            .merchNbr("3285220521")
            .rtInd("015")
            .host(url)
            .build().buildHeaders();

    int version = 12010000;
    String merId = "8127921740";
    String termId ="80000011";
    int batchID = 100000;
    int submitterId = 106544;
    String  submitterCode = "8038464327";

    /**
     * AuthorizationRequestParam=<?xml version="1.0" encoding="utf-8"?><BatchAdminRequest><Version>12010000</Version><MerId>8127921740</MerId><BatchID>100000</BatchID><MerTrmnlId>80000011</MerTrmnlId><BatchOperation>01</BatchOperation><CardAcceptorDetail><CardAcptStreetNm>VilniusCitysav</CardAcptStreetNm><CardAcptNm>PAYTENDEUROPEUAB</CardAcptNm><CardAcptCityNm>Vilnius</CardAcptCityNm><CardAcptCtryCd>440</CardAcptCtryCd><CardAcptRgnCd>58</CardAcptRgnCd><CardAcptPostCd>01113</CardAcptPostCd></CardAcceptorDetail><SubmitterCode>8038464327</SubmitterCode></BatchAdminRequest>
     * <?xml version="1.0" encoding="US-ASCII" standalone="yes"?>
     * <!-- American Express Co., Inc. - BatchAdmin Response V11020000 -->
     * <!-- Content-Type: text/xml -->
     * <BatchAdminResponse>
     *   <Version>12010000</Version>
     *   <MerId>8127921740</MerId>
     *   <MerTrmnlId>80000011</MerTrmnlId>
     *   <BatchID>100000</BatchID>
     *   <BatchStatus>000</BatchStatus>
     *   <BatchStatusText>Open</BatchStatusText>
     * </BatchAdminResponse>
     * @throws Exception
     */
    @Test
    public void testBatchAdminRequest() throws Exception {
        BatchAdminRequest batchAdminRequest = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //open
                .BatchOperation("01")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        System.out.println(batchAdminRequest.toXml());
        String respXml = TransCommUtils.sendXml(url, batchAdminRequest, headersBatchAdmin);
        System.out.println(respXml);
    }
}
