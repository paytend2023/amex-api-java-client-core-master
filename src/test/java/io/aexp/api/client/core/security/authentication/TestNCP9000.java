package io.aexp.api.client.core.security.authentication;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.*;
import com.paytend.models.trans.rsp.AuthorizationRsp;
import io.aexp.api.client.core.utils.XmlUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class TestNCP9000 {
    Authorization.AuthorizationBuilder
            authorizationBuilder = Authorization.builder()
            .MsgTypId("1100")
            .TransProcCd("004000")
            .CardExprDt("2501")
            .AcqInstCtryCd("276")
            .FuncCd("100")
            .MsgRsnCd("1900")
            .MerCtgyCd("4111")
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

    SecureAuthenticationSafeKey.SecureAuthenticationSafeKeyBuilder
            secureAuthenticationSafeKeyBuilder = SecureAuthenticationSafeKey.builder()
            .ScndIdCd("ASK")
            .ElecComrceInd("05")
            .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

    CardAcceptorIdentification.CardAcceptorIdentificationBuilder
            cardAcceptorIdentificationBuilder = CardAcceptorIdentification.builder()
            .MerId("8127921740");

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
            .CustEmailAddr("king.gu@gmail.com")
            .CustHostServerNm("www.baidu.com")
            .CustBrowserTypDescTxt("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)")
            .ShipToCtryCd("440")
            .ShipMthdCd("01")
            .MerSKUNbr("TKDC315U")
            .CustIPAddr("127.142.5.56")
            .CustIdPhoneNbr("13651654626")
            .CallTypId("61");

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
        long pan = 374500262001008L;
        long amt = 1100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        authorizationBuilder.TransCurrCd("156");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * * TEST CASE NAME
     * 9051 - CNP - Remote Transaction
     * <p>
     * TEST CASE DESCRIPTION
     * Customer Initiated Transaction with Safekey.
     * Test a Card Not Present 1100 Authorization Request supporting a CIT transaction including Safekey data.
     * Pre-requisite for this test case is the customer has already certified or is certifying Safekey.
     * <p>
     * INSTRUCTION
     * <p></p><p></p><p>Initiate a CIT (Customer Initiated Transaction)</p>
     * <p>Convert Safekey - Base 64 data listed in Host Validation.</p><p>
     * Use PAN: 374500262001008
     * </p><p>
     * Transaction amount: $1600 - $2000 ($16.00 - $20.00)
     * </p><p>Returned Action Code: 000</p><p><br/></p>
     * American Express application was selected.
     * <p>
     * user validation
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * host validation
     * The transaction reflects a Customer Initiated Transaction
     * SafeKey AEVV Value : AAABBWcSNIdjeUZThmNHAAAAAAA=
     * POS Data Code represents a Card Not Present transaction.
     * InitPartyInd reflects a Customer Initiated Transaction.
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9051() throws Exception {
        long pan = 374500262001008L;
        long amt = 1600;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        .secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * TEST CASE DESCRIPTION
     * 9053 - CNP - Remote Transaction
     * Mail Order Transaction	Test a Card Not Present Customer Initiated Mail Order 1100 Authorization Request.
     * An Approved response is sent in the 1110 Authorization Response.
     * instruction
     * "<p>Initiate a CIT (Customer Initiated Transaction)<br/>Transaction amount: $100 - $1000 (1.00 - 10.00)<br/>
     * Use Pan: 375705004001005<br/>Returned Action Code: 000<br/></p>
     * <p>
     * USER VALIDATION
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was Approved
     * <p>
     * HOST VALIDATION
     * The transaction reflects a Customer Initiated Transaction
     * POS Data Code represents a Card Not Present - Mail Order transaction.
     * InitPartyInd reflects a Customer Initiated Transaction.
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP - Mail Order  transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9053() throws Exception {
        long pan = 375705004001005L;
        long amt = 100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("2");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * TEST CASE DESCRIPTION
     * 9054- CNP - Remote Transaction
     * Merchant Initiated Recurring Billing Transaction
     * Test a Card Not Present 1100 Authorization Request CIT transaction with Recurring Billing MIT Request.
     * An Approved response is sent in the 1110 Authorization Response.
     * "Initiate a CIT (Customer Initiated Transaction) for Recurring Billing
     * Use PAN: 374500261001009
     * Transaction amount: $100 - $1000 ($1.00 - 10.00)
     * Returned Action Code: 000
     * <p>
     * user valdation
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * host validation
     * The transaction reflects a Customer Initiated Transaction
     * POS Data Code represents a Card Not Present transaction.
     * InitPartyInd reflects a Customer Initiated Transaction.
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction.
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */

    //000002493782263
    @Test
    public void test9054CIT() throws Exception {
        long pan = 374500261001009L;
        long amt = 100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("2");
        acptEnvDataBuilder.InitPartyInd("1");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * 9054 - CNP - Remote Transaction
     * Merchant Initiated Recurring Billing Transaction  Test a Card Not Present 1100 Authorization Request CIT transaction with Recurring Billing MIT Request.  An Approved response is sent in the 1110 Authorization Response.
     * <p>
     * MIT
     * N
     * <p>
     * Initiate a MIT (Merchant Initiated Transaction) for Recurring Billing
     * Use PAN: 374500261001009
     * Transaction amount: $100 - $1000 ($1.00 - 10.00)
     * Returned Action Code: 000
     * <p>
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * <p>
     * The transaction reflects a Merchant Initiated Transaction
     * POS Data Code represents a Recurring Billing Card Not Present transaction.
     * DF60.7 (OTID) is equal to DF31 (TID) from CIT response.
     * OTID is equal to TID from CIT response.
     * InitPartyInd reflects a Merchant Initiated Transaction
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP Recurring Billing transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9054MIT() throws Exception {
        long pan = 374500261001009L;
        long amt = 100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("9");
        acptEnvDataBuilder.InitPartyInd("1");
        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * 9056  - CNP - Remote Transaction CIT with subsequent MIT (Non-US Customers Only)
     * <p>
     * Test a Card Not Present 1100 Authorization Request supporting a CIT transaction with subsequent MIT.
     * An Approved response is sent in the 1110 Authorization Response.
     * <p>
     * CIT
     * N
     * <p>
     * Initiate a CIT (Customer Initiated Transaction)
     * Use PAN: 374500262001008
     * Transaction amount:  $600 - $1000 ($6.00 - 10.00)
     * Returned Action Code: 000
     * <p>
     * <p>
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * <p>
     * The transaction reflects a Customer Initiated Transaction
     * POS Data Code represents a Card Not Present transaction.
     * InitPartyInd reflects a Customer Initiated Transaction.
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9056CIT() throws Exception {
        long pan = 374500261001009L;
        long amt = 600;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("9");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * 9056 - CNP - Remote Transaction CIT with subsequent MIT (Non-US Customers Only)

     * Test a Card Not Present 1100 Authorization Request supporting a CIT transaction with subsequent MIT.
     * An Approved response is sent in the 1110 Authorization Response.
     *
     * MIT
     * N
     *
     * Initiate a MIT (Merchant Initiated Transaction)
     * Use PAN: 374500262001008
     * Transaction amount:  $600 - $1000 ($6.00 - 10.00)
     * Returned Action Code: 000
     * <p>
     * <p>
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * <p>
     * The transaction reflects a Merchant Initiated Transaction
     * DF60.7 (OTID) is equal to DF31 (TID) from CIT response.
     * POS Data Code represents a Card Not Present transaction.
     * For EEA Region Only:  Field 22, Position 5 = 1
     * OTID is equal to TID from CIT response.
     * InitPartyInd reflects a Merchant Initiated Transaction
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9056MIT() throws Exception {
        long pan = 374500262001008L;
        long amt = 600;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("9");
        acptEnvDataBuilder.InitPartyInd("1");
        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * 9057 - CNP - Remote Transaction :
     * Telephone Order Transaction	Test a Card Not Present 1100 CIT Telephone Order Authorization Request .
     * An Approved response is sent in the 1110 Authorization Response.
     * <p>
     * CIT
     * N
     * <p>
     * "Initiate a CIT (Customer Initiated Transaction)
     * Transaction amount: $1100 - $2000 ($11.00 - 20.00)
     * Use PAN: 375705004001005
     * Returned Action Code: 000
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was Approved
     * <p>
     * The transaction reflects a Customer Initiated Transaction
     * POS Data Code represents a Card Not Present Telephone transaction.
     * InitPartyInd reflects a Customer Initiated Transaction.
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void test9057() throws Exception {
        long pan = 375705004001005L;
        long amt = 1100;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();

        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");

        pointOfServiceDataBuilder.CMPresentCd("3");

        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));

        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }

    /**
     * 9059 - CNP - Remote Transaction : Customer Initiated Transaction with Authentication Outage Indicator
     * Test a Card Not Present 1100 Authorization Request supporting a CIT transaction including "Authentication Outage Indicator".
     * An Approved response is sent in the 1110 Authorization Response.
     * <p>
     * "<p>Initiate a CIT (Customer Initiiated Transaction)<br/>
     * Use PAN: 374500262001008<br/>Transaction amount = $7000 - $8000 ($70.00 - $80.00)<br/>Return Action Code: 000<br/></p>
     * <p>
     * American Express application was selected.
     * System displayed ""Enter Card"" or similar.
     * Card Expiry date was requested.
     * Transaction amount was requested.
     * Transaction was approved.
     * <p>
     * <p>
     * The transaction reflects a Customer Initiated Transaction.
     * Authentication Outage Indicator represents 'Claimed'
     * Authentication Outage Indicator represents 'Claimed'
     * POS Data Code represents a Card Not Present transaction
     * InitPartyInd reflects a Customer Initiated Transaction.
     * AuthOutageInd represents 'Claimed'
     * Bit 22.1: CardDataInpCpblCd represents a CNP transaction
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     * "
     */
    @Test
    public void test9059() throws Exception {
        long pan = 374500262001008L;
        long amt = 7000;
        AuthorizationFactory.AuthorizationConfig config =
                AuthorizationFactory.AuthorizationConfig.builder()
                        .authorizationBuilder(authorizationBuilder)
                        .pointOfServiceDataBuilder(pointOfServiceDataBuilder)
                        //.secureAuthenticationSafeKeyBuilder(secureAuthenticationSafeKeyBuilder)
                        .cardAcceptorIdentificationBuilder(cardAcceptorIdentificationBuilder)
                        .cardAcceptorDetailBuilder(cardAcceptorDetailBuilder)
                        .cardNotPresentDataBuilder(cardNotPresentDataBuilder)
                        .acptEnvDataBuilder(acptEnvDataBuilder)
                        .build();


        acptEnvDataBuilder.psd2Exemptions(AcptEnvData.Psd2Exemptions.builder()
                .EuPsd2SecCorpPayInd("0")
                .AuthOutageInd("1").build());
        pointOfServiceDataBuilder.CMPresentCd("1");
        secureAuthenticationSafeKeyBuilder.ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");


        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
        Assert.assertNotEquals(response.getTransActCd(), "181");
        Assert.assertEquals(response.getTransActCd(), "000");
    }
}
