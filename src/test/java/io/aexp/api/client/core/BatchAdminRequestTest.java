package io.aexp.api.client.core;

import com.paytend.models.trans.req.BatchAdminRequest;
import com.paytend.models.trans.req.CardAcceptorDetail;
import com.paytend.models.trans.req.Header;
import com.paytend.models.trans.req.TransCommUtils;
import com.paytend.models.trans.rsp.BatchResp;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author Sunny
 * @create 2023/8/21 21:20
 */
public class BatchAdminRequestTest {
    String url = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";
    static final Logger LOGGER = LoggerFactory.getLogger(BatchAdminRequestTest.class);

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


    int version = 12010000;
    String merId = "8127921740";
    String termId = "00000001";
    String submitterCode = "8038464327";

    /**
     * AuthorizationRequestParam=<?xml version="1.0" encoding="utf-8"?><BatchAdminRequest><Version>12010000</Version><MerId>8127921740</MerId><BatchID>100000</BatchID><MerTrmnlId>80000011</MerTrmnlId><BatchOperation>01</BatchOperation><CardAcceptorDetail><CardAcptStreetNm>VilniusCitysav</CardAcptStreetNm><CardAcptNm>PAYTENDEUROPEUAB</CardAcptNm><CardAcptCityNm>Vilnius</CardAcptCityNm><CardAcptCtryCd>440</CardAcptCtryCd><CardAcptRgnCd>58</CardAcptRgnCd><CardAcptPostCd>01113</CardAcptPostCd></CardAcceptorDetail><SubmitterCode>8038464327</SubmitterCode></BatchAdminRequest>
     * <?xml version="1.0" encoding="US-ASCII" standalone="yes"?>
     * <!-- American Express Co., Inc. - BatchAdmin Response V11020000 -->
     * <!-- Content-Type: text/xml -->
     * <BatchAdminResponse>
     * <Version>12010000</Version>
     * <MerId>8127921740</MerId>
     * <MerTrmnlId>80000011</MerTrmnlId>
     * <BatchID>100000</BatchID>
     * <BatchStatus>000</BatchStatus>
     * <BatchStatusText>Open</BatchStatusText>
     * </BatchAdminResponse>
     *
     * @throws Exception
     */
    @Test
    public void testBatchOperation() throws Exception {
        /**
         * 01 = Open
         * 02 = Close
         * 03 = Purge
         * 04 = Status
         */
        int batchID = 100002;
        String respXml = null;
        BatchAdminRequest batchOpen = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //open
                .BatchOperation("01")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchOpen, headersBatchAdmin);
        System.out.println("batchOpen:" + respXml);

        BatchAdminRequest batchPurge = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //Purge
                .BatchOperation("03")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchPurge, headersBatchAdmin);
        System.out.println("purge:" + respXml);

        BatchAdminRequest batchStatus1 = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //Purge
                .BatchOperation("04")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchStatus1, headersBatchAdmin);
        System.out.println("batchStatus1:" + respXml);

        BatchAdminRequest batchClose = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //close
                .BatchOperation("02")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchClose, headersBatchAdmin);
        System.out.println("batchClose:" + respXml);

        BatchAdminRequest batchStatus2 = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //Purge
                .BatchOperation("04")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchStatus2, headersBatchAdmin);
        System.out.println("batchStatus2:" + respXml);
    }

    @Test
    public void testBatchDataOpen() throws Exception {
        int batchID = 100012;
        String respXml = null;
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> batchOpen:{}", batchID);
        BatchAdminRequest batchOpen = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //open
                .BatchOperation("01")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        LOGGER.info("batchOpen:{}", batchOpen.toXml());
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchOpen, headersBatchAdmin);
        BatchResp batchResp = BatchResp.createByXml(respXml);
        LOGGER.info("batchOpenResp:{}", batchResp);
        Assert.assertEquals("000", batchResp.getBatchStatus());
        Assert.assertEquals(batchID + "", batchResp.getBatchID());
    }


    @Test
    public void testBatchDataClose() throws Exception {
        int batchID = 100012;
        String respXml = null;
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> batchClose:{}", batchID);
        BatchAdminRequest batchClosed = BatchAdminRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                //close
                .BatchOperation("02")
                .CardAcceptorDetail(cardAcceptorDetailBuilder.build())
                .SubmitterCode(submitterCode)
                .build();
        LOGGER.info("BatchDataClose:{}", batchClosed.toXml());
        respXml = TransCommUtils.getTestInstance().sendXml(url, batchClosed, headersBatchAdmin);
        LOGGER.info("BatchDataCloseResp:{}", respXml);
        BatchResp batchResp = BatchResp.createByXml(respXml);
        Assert.assertEquals("001", batchResp.getBatchStatus());
        Assert.assertEquals(String.valueOf(batchID), batchResp.getBatchID());
    }
}
