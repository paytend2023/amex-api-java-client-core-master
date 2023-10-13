package io.aexp.api.client.core;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.DataCaptureRequest;
import com.paytend.models.trans.req.Header;
import com.paytend.models.trans.req.PointOfServiceData;
import com.paytend.models.trans.req.TransCommUtils;
import org.junit.Test;

import java.util.Date;
import java.util.Map;

/**
 * @author Sunny
 * @create 2023/8/23 15:17
 */
public class DataCaptureTest {
    String url = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";
    int version = 12010000;
    String merId = "8127921740";
    String termId = "00000001";
    String submitterCode = "8038464327";


    PointOfServiceData.PointOfServiceDataBuilder pointOfServiceDataBuilder = PointOfServiceData.builder()
            .CardDataInpCpblCd("1")
            .CMAuthnCpblCd("6")
            .CardCptrCpblCd("0")
            .OprEnvirCd("0")
            .CMPresentCd("3")
            .CardPresentCd("0")
            .CardDataInpModeCd("1")
            .CMAuthnMthdCd("0")
            .CMAuthnEnttyCd("0")
            .CardDataOpCpblCd("0")
            .TrmnlOpCpblCd("1")
            .PINCptrCpblCd("0");


    Map<String, String> dataCaptureHeaders = Header.builder()
            .origin("Paytend")
            .country("276")
            .region("EMEA")
            .message("GFSG XML DCR")
            .merchNbr("3285220521")
            .rtInd("015")
            .host(url)
            .build().buildHeaders();

    @Test
    public void testDataCaptureRequest() throws Exception {
        int batchID = 100003;
        String RefNumber = "000001";
        DataCaptureRequest dataCaptureRequest = DataCaptureRequest.builder()
                .Version(String.valueOf(version))
                .MerId(merId)
                .BatchID(String.valueOf(batchID))
                .MerTrmnlId(termId)
                .RefNumber(RefNumber)
                .CardNbr(374500261001009L)
                .TransDt(DateUtil.format(new Date(),"yyyyMMdd"))
                .TransAmt(1600)
                //Euro
                .TransCurrCd("978")
                //000000  200000
                .TransProcCd("000000")//debit Purchase
//        230823170512
                .TransId("000002529199380")  //交易唯一标识
                .TransAprvCd("199380") //授权码
                .PointOfServiceData(pointOfServiceDataBuilder.build())
//                .DefPaymentPlan("0005")
                .MerCtgyCd("4111")
                .SellId("1234QR7890")
                .build();

        System.out.println(dataCaptureRequest.toXml());
        String respXml = TransCommUtils.sendXml(url, dataCaptureRequest, dataCaptureHeaders);
        System.out.println("batchStatus1:" + respXml);
    }

}
