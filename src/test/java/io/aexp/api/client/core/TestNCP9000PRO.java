package io.aexp.api.client.core;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.*;
import com.paytend.models.trans.rsp.AuthorizationRsp;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * 生产环境测试
 */
@Slf4j
public class TestNCP9000PRO {
    private static String url = "https://www359.americanexpress.com/IPPayments/inter/CardAuthorization.do\n";


    Authorization.AuthorizationBuilder
            authorizationBuilder = Authorization.builder()
            .MsgTypId("1100")
            .TransProcCd("004000")
            .CardExprDt("2705")
            .AcqInstCtryCd("440")
            .FuncCd("100")
            .MsgRsnCd("1900")
            .MerCtgyCd("7996")
            .RtrvRefNbr(DateUtil.format(new Date(), "yyyyMMddHHmmss").substring(2))
            .MerTrmnlId("00000001")
            .TransCurrCd("978");

    PointOfServiceData.PointOfServiceDataBuilder
            pointOfServiceDataBuilder = PointOfServiceData.builder()
            .CardDataInpCpblCd("1")
            .CMAuthnCpblCd("6")
            .CardCptrCpblCd("0")
            .OprEnvirCd("0")
            .CMPresentCd("1")
            .CardPresentCd("0")
            .CardDataInpModeCd("1")
            .CMAuthnMthdCd("0")
            .CMAuthnEnttyCd("0")
            .CardDataOpCpblCd("0")
            .TrmnlOpCpblCd("1")
            .PINCptrCpblCd("0");


    CardAcceptorIdentification.CardAcceptorIdentificationBuilder
            cardAcceptorIdentificationBuilder = CardAcceptorIdentification.builder()
            .MerId("900000000000001");

    CardAcceptorDetail.CardAcceptorDetailBuilder
            cardAcceptorDetailBuilder = CardAcceptorDetail.builder()
            .CardAcptNm("PAYTEND EUROPE UAB")
            .CardAcptStreetNm("Vilnius City sav")
            .CardAcptCityNm("Vilnius")
            .CardAcptPostCd("01113")
            .CardAcptRgnCd("58")
            .CardAcptCtryCd("440");

    CardNotPresentData.CardNotPresentDataBuilder
            cardNotPresentDataBuilder = CardNotPresentData.builder()
            .custEmailAddr("king.gu@gmail.com")
            .custHostServerNm("www.baidu.com")
            .custBrowserTypDescTxt("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)")
            .shipToCtryCd("440")
            .shipMthdCd("01")
//            .merSKUNbr("TKDC315U")
            .custIPAddr("127.142.5.56")
            .custIdPhoneNbr("13651654626")
            .callTypId("61");

    AcptEnvData.AcptEnvDataBuilder
            acptEnvDataBuilder = AcptEnvData.builder()
            .InitPartyInd("0")
            .psd2Exemptions(AcptEnvData.Psd2Exemptions.builder()
                    .EuPsd2SecCorpPayInd("0")
                    .AuthOutageInd("0")
                    .build());


    /**
     * 9050 - CNP - Remote Transaction
     * Test case description
     * Customer Initiated Transaction	Test a Card Not Present 1100 Authorization Request supporting a CIT transaction.
     * An Approved response is sent in the 1110 Authorization Response.
     * <p>
     * instructions
     * "<p>Initiate a CIT (Customer Initiiated Transaction)<br/>
     * Use PAN: 374500262001008<br/>
     * Transaction amount: $1100 - $1500 ($11.00 - $15.00)<br/>Return Action Code: 000<br/></p>
     * American Express application was selected.
     * <p>
     * user  validation
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * host validations
     * The transaction reflects a Customer Initiated Transaction.
     * POS Data Code represents a Card Not Present transaction
     * InitPartyInd reflects a Customer Initiated Transaction.
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     * "
     */
    @Test
    public void test9050() throws Exception {
        long pan = 378330600475458L;
        long amt = 100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
//                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
//                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
//                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        authorizationBuilder.TransCurrCd("978");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));


        Header header = Header.builder()
                .origin("PAYTEND EUROPE UAB")
                .country("978")
                .region("EMEA")
                .message("XML GCAG")
                .merchNbr("8127478295")
                .rtInd("000")
                .host("https://www359.americanexpress.com/IPPayments/inter/CardAuthorization.do")
                .build();
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.getProInstance().sendXml(url, authorization, header.buildHeaders());
        log.info("response string:{}", responseStr);

        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }
}
