package io.aexp.api.client.core;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.*;
import com.paytend.models.trans.rsp.AuthorizationRsp;
import io.aexp.api.client.core.utils.XmlUtility;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * validationPCO.getAEVVDecryptedData(aevvValue, pan, key)
 * AEVV HEX Value                    = "0800980748123482785768480609560000000000"
 * Authentication Results Code       = "8"
 * Second Factor Authentication Code = "00"
 * AEVV Key Indicator                = "98"
 * AEVV Output                       = "748"
 * Unpredictable Number              = "1234"
 * Authentication Tracking Number    = "8278576848060956"
 * Reserved                          = "0000000000"
 * Step 1 - 16-digit Account Block 1 = "1234450026100100"
 * Unpredictable Number              = "1234"
 * Truncated PAN                     = "450026100100"
 * Step 2 - 16-digit Account Block 2 = "8000000000000000"
 * Authentication Results Code       = "8"
 * Second Factor Authentication Code = "00"
 * Appending Zeros                   = "0000000000000"
 * Step 3 - Final Account Block      = "12344500261001008000000000000000"
 * Step 4 - Encrypted Account Block Value = "BC058A7B3319395999D8ACF92F09B4A7"
 * Used AEVV Key                     = "1234567890ABCDEF1234567890ABCDEF"
 * Triple-DES Result                 = "99D8ACF92F09B4A7"
 * Step 5 - Full HEX Parsed & Converted = "998920947418"
 * 5CSC                              = "99892"
 * 4CSC                              = "0947"
 * 3CSC                              = "418"
 */

public class SafeKeyTest {
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
            .InitPartyInd("1")
            .psd2Exemptions(AcptEnvData.Psd2Exemptions.builder()
                    .EuPsd2SecCorpPayInd("0")
                    .AuthOutageInd("0")
                    .build());

    /**
     * Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 05
     * and convert the SafeKey AEVV value listed in the Host Validations section.
     * A Deny response is sent in the 1110 Authorization Response with AEVV Validation Result. .
     * <p>
     * SafeKey 1.0 - Use PAN:  374500261001009
     * <p>
     * SafeKey 2.0 - Use PAN:  376701078252003
     * <p>
     * Transaction Amount: 1100 - 1500 (e.g. $11.00 - $15.00)
     * ECI: 05
     * AEVV Result: 1
     * Return Action Code: 100
     *
     * <p>
     * STATIC: Use the AEVV Value indicated in the Host Validation section.
     * <p>
     * DYNAMIC: The AEVV(American Express Verification Value ) Value comes from the STL
     * Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=
     * String: AAABAURAWAAAAAAAAEBYAAAAAAA=
     * <p>
     * <p>
     * Check for presence of AESKTransId
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present transaction.
     *
     * @throws IOException
     */

    @Test
    public void testSK00() throws Exception {
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
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;

        long pan = 374500261001009L;
        pan = 376701078252003L;
        long amt = 1101;

        String tmp = String.valueOf(System.currentTimeMillis() - pan);
        tmp = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
    }

    /**
     * Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 05 and convert the SafeKey AEVV value listed in the Host Validations section.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 05.
     * Dynamically convert the AEVV Value received from the STL.
     * <p>
     * <p>
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result.
     * "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0 </span>Use PAN:  374500261001009</p>
     * <p><span style=""font-size: 12px;float: none;"">SafeKey 2.0 Use PAN: </span>376701078252003<br/>
     * Transaction Amount: 1600 - 2000 (e.g. $16.00 - $20.00)
     * <br/>ECI: 05
     * <br/>AEVV Result: 2
     * <br/>Return Action Code: 000
     * <br/></p>
     * <p>STATIC: Use the AEVV Value indicated in the Host Validation section.
     * <br/><br/>DYNAMIC: The AEVV Value comes from the STL<br/
     * >Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/>
     * </p><!--StartFragment-->
     * <p style=""font-size: 12px;""><i><u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u>
     * </i></p><p style=""font-size: 12px;""><i>
     * <u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u>
     * </i></p>
     * <p>
     * host validations
     * <p>
     * ECI: 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code = '0'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 02
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     *
     * @throws Exception
     */
    @Test
    public void testSK001() throws Exception {
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
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;
        long pan = 374500261001009L;
        long amt = 1600;
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
    }

    /**
     * test case description
     * Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value listed in the Host Validations section.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.
     * Dynamically convert the AEVV Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * <p>
     * INSTRUCTIONS
     * "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0 
     * </span>Use PAN: 374500262001008</p><p>
     * <span style=""font-size: 12px;float: none;"">SafeKey 2.0 Use PAN: </span>375987000169578<br/>
     * Transaction Amount: 2100 - 3000 (e.g. $21.00 - $30.00)<br/>ECI: 06<br/>AEVV Result: 3<br/>Return Action Code: 000<br/>
     * </p><p>STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * <p>
     * DYNAMIC: The AEVV Value comes from the STL<br/>Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=
     * <br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p>
     * <!--StartFragment--><p style=""font-size: 12px;""><i><u>
     * This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.
     * </u></i></p><p style=""font-size: 12px;""><i><u>
     * If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.
     * </u></i></p>
     * <p>
     * host validation
     * ECI: 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     */
    @Test
    public void testSK02() throws Exception {

        long pan = 374500262001008L;
        long amt = 2100;

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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0700010748123482785768480609560000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;

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

    }

    /**
     * Test case description
     * SK03 - SafeKey -Decline- ECI 06
     * Static:Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value listed in the Host Validations section.
     * A Deny response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * instructions
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.  Dynamically convert the AEVV Value received from the STL.   A Deny response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * Safekey	N	"<p><span style=""font-size: 12px;float: none;"">
     * SafeKey 1.0</span>Use PAN:374500262001008 </p><p><span style=""font-size: 12px;float: none;"">
     * SafeKey 2.0 Use PAN:</span>375987000169719<br/>
     * Transaction Amount: 3100 - 4000 (e.g. $31.00 - $40.00)<br/>
     * ECI: 06<br/>AEVV Result: 4<br/>
     * Return Action Code: 100<br/></p><p>
     * STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * DYNAMIC: The AEVV Value comes from the STL<br/>
     * Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>
     * String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i><u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u></i></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * <p>
     * host validation
     * ECI: 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     *
     * @throws Exception
     */

    @Test
    public void testSK03() throws Exception {
        long pan = 374500262001008L;
        long amt = 3100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0700010748123482785768480609560000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;
        //yyyyMMddhh24miss

        String tmp = "000000" + new Random(System.currentTimeMillis()).nextInt(999999);
        authorizationBuilder.CardNbr(String.valueOf(pan))
                .TransAmt(String.valueOf(amt))
                .MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        AuthorizationFactory factory = new AuthorizationFactory(config);
        Authorization authorization = factory.create();
        System.out.println(authorization.toXMLString());
        String responseStr = TransCommUtils.sendXml(authorization.toXMLString(), Header.defaultHeaders());
        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(responseStr, AuthorizationRsp.class);
        System.out.println(response);
    }

    /**
     * SK04 - SafeKey - Approve - ECI 06	"Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value listed in the Host Validations section.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.
     * Dynamically convert the AEVV Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result.
     *
     *
     * <p><span style=""font-size: 12px;float: none;"">SafeKey 1.0</span>Use PAN:374500262001008 </p>
     * <p><span style=""font-size: 12px;float: none;"">SafeKey 2.0 Use PAN:</span>375987000169578<br/>
     * <p>
     * Transaction Amount: 4100 - 5000 (e.g. $41.00 - $50.00)<br/>
     * ECI: 06<br/>AEVV Result: A<br/>Return Action Code: 000<br/></p><p>
     * <p>
     * STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * DYNAMIC: The AEVV Value comes from the STL<br/>Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=
     * <br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i>
     * <u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u></i></p><p style=""font-size: 12px;"">
     * <i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * <p>
     * ECI: 06
     * SafeKey AEVV Value :  CACYB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  CACYB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     *
     * @throws Exception
     */

    @Test
    public void testSK04() throws Exception {
        long pan = 374500262001008L;
        long amt = 4100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0800980748123482785768480609560000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;

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
    }

    /**
     * Test case description
     * SK05 - SafeKey - Deny - ECI 06	"Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value listed in the Host Validations section.  A Deny response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * instructions
     * <p>
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.  Dynamically convert the AEVV Value received from the STL.   A Deny response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * Safekey	N	"<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0 </span>Use
     * PAN:374500262001008 </p><p><span style=""font-size: 12px;float: none;"">
     * SafeKey 2.0 Use PAN:</span>375987000169719<br/>Transaction Amount: 5100 - 6000 (e.g. $51.00 - $60.00)<br/>
     * ECI: 06<br/>AEVV Result: 9<br/>Return Action Code: 100<br/></p><p>STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * DYNAMIC: The AEVV Value comes from the STL<br/>Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i><u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u></i></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * <p>
     * host validation
     * ECI: 06
     * SafeKey AEVV Value :  CACYB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  CACYB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * EVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     *
     * @throws Exception
     */
    @Test
    public void testSK05() throws Exception {
        long pan = 374500262001008L;
        long amt = 5100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0800980748123482785768480609560000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;
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
    }


    /**
     * Test case description
     * SK06 - SafeKey - Approve - ECI 07   "Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 07.  SafeKey AEVV Value / Table ID is Not Present.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * instructions
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 07.  AEVV Value is not returned  from the STL.
     * A Deny response is sent in the 1110 Authorization Response with AEVV Validation Result.
     * Safekey N   "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0/2.0</span>
     * Use PAN:374500261001009 </p><p>Transaction Amount: 6100 - 7000 (e.g. $61.00 - $70.00)
     * <br/>ECI: 07<br/>
     * Return Action Code: 000<br/></p><p><i><u><br/></u></i></p><p><i><u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u></i>
     * <br/></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * <p>
     * host validation
     * ECI: 07
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 07
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * Safekey data is NIL or empty
     */
    @Test
    public void testSK06() throws Exception {
        long pan = 374500261001009L;
        long amt = 6100;

        SecureAuthenticationSafeKey.SecureAuthenticationSafeKeyBuilder
                secureAuthenticationSafeKeyBuilder = SecureAuthenticationSafeKey.builder()
                .ScndIdCd("ASK")
                .ElecComrceInd("07") // 07  AmexExpVerificationValTxt must not be present
                ;

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
     * Test case description
     * SK07 - SafeKey - Approve - ECI 05   "Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 05 and convert the SafeKey AEVV value listed in the Host Validations section.
     * An Approve  Response is sent in the 1110 Authorization Response  with AEVV Validation Result.
     * <p>
     * instructions
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 05.  Dynamically convert the AEVV Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * Safekey N   "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0 </span>
     * Use PAN: 375705004001005</p><p><span style=""font-size: 12px;float: none;"">
     * SafeKey 2.0 Use PAN:</span>375987000169545<br/>Transaction Amount: 7600 - 8000 (e.g. $76.00 - $80.00)<br/>
     * ECI: 05<br/>AEVV Result: U<br/>Return Action Code: 000<br/></p><p>STATIC: Use the AEVV Value indicated in the Host Validation section.
     * <br/><br/>DYNAMIC: The AEVV Value comes from the STL<br/>Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>
     * String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i><u>
     * This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.
     * </u></i></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.
     * </u></i></p>
     * <p>
     * host validation
     * ECI: 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code = '0'
     * HOST_DATA[""Second Factor Authentication Code""]
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 02
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     * "
     *
     * @throws Exception
     */
    @Test
    public void testSK07() throws Exception {
        long pan = 375705004001005L;
        long amt = 7600;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;
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
    }

    /**
     * SK08 - SafeKey - Approve - ECI 06   "Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value listed in the Host Validations section.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.  Dynamically convert the AEVV Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * Safekey N   "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0</span>
     * Use PAN:374500262001008 </p><p><span style=""font-size: 12px;float: none;"">
     * SafeKey 2.0 Use PAN: </span> 375987000169578<br/>Transaction Amount: 8100 - 9000 (e.g. $81.00 - $90.00)<br/>ECI: 06<br/>
     * AEVV Result: U<br/>Return Action Code: 000<br/></p><p>
     *     STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * DYNAMIC: The AEVV Value comes from the STL<br/>
     * Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i><u>
     *     This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.
     * </u></i></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * ECI: 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * XID Value is not present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  BwABB0gSNIJ4V2hIBglWAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     * "
     *
     * @throws Exception
     */
    @Test
    public void testSK08() throws Exception {
        long pan = 374500262001008L;
        long amt = 8100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0700010748123482785768480609560000000000");
        secureAuthenticationSafeKeyBuilder
                .AESKTransId("3132333435363738393031323334353637383930")
        ;
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
    }

    /**
     * Test case description
     * SK09 - SafeKey AEVV & XID - ECI 06 - Third Party Processor  Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 06 and convert the SafeKey AEVV value and
     * XID value listed in the Host Validations section. An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * instructions
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.
     * Dynamically convert the AEVV and XID  Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result."
     * Safekey N   "<p><span style=""font-size: 12px;float: none;"">SafeKey 1.0 </span>Use PAN:  374500262001008 </p><p><span style=""font-size: 12px;float: none;"">
     * SafeKey 2.0 Use PAN: </span> 375987000169578<br/>
     * Transaction Amount: 9100 - 9500 (e.g. $91.00 - $95.00)<br/>
     * ECI: 06<br/>AEVV Result: 2
     * <br/>Return Action Code: 000<br/></p><p>
     *     STATIC: Use the AEVV Value indicated in the Host Validation section.
     *     <br/><br/>DYNAMIC: The AEVV Value comes from the STL<br/>
     *     Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/>
     * </p><p style=""font-size: 12px;""><i><u>
     *     This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.
     * </u></i></p><p style=""font-size: 12px;""><i><u>
     *     If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.
     * </u></i></p>
     * <p>
     * host validation
     * ECI: 06
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 06
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     *
     * @throws Exception
     */
    @Test
    public void testSK09() throws Exception {
        long pan = 374500262001008L;
        long amt = 9100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("06")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000")
                .AESKTransId("3132333435363738393031323334353637383930")
        ;

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
    }

    /**
     * Test case description
     * SK10 - SafeKey AEVV & XID - ECI 05 - Third Party Processor  "Static:
     * Test a Card Not Present SafeKey 1100 Authorization Request with ECI 05 and convert the SafeKey AEVV value and XID value listed in the Host Validations section.
     * An Approve response is sent in the 1110 Authorization Response  with AEVV Validation Result. .
     * <p>
     * instructions
     * Dynamic:
     * Test a Card Not Present SafeKey 1100 Authorization Request  with ECI 06.
     * Dynamically convert the AEVV and XID  Value received from the STL.
     * An Approve response is sent in the 1110 Authorization Response with AEVV Validation Result." Safekey N   "<p>
     * <span style=""font-size: 12px;float: none;"">SafeKey 1.0 </span>Use PAN:  374500261001009
     * </p><p><span style=""font-size: 12px;float: none;"">SafeKey 2.0 Use PAN: </span> 376701078252003<br/>
     * Transaction Amount: 10100 - 11000 (e.g. $101.00 - $110.00)<br/>ECI: 05<br/>AEVV Result: 2<br/>Return Action Code: 000<br/></p><p>STATIC: Use the AEVV Value indicated in the Host Validation section. <br/><br/>
     * DYNAMIC: The AEVV Value comes from the STL<br/>Example: PARes - AmEx Verification Value=AAABAURAWAAAAAAAAEBYAAAAAAA=<br/>String: AAABAURAWAAAAAAAAEBYAAAAAAA=<br/><br/></p><!--StartFragment--><p style=""font-size: 12px;""><i><u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u></i></p><p style=""font-size: 12px;""><i><u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u></i></p>
     * <p>
     * host validation
     * ECI: 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of SafeKey Transaction Id (XID)
     * Check for presence of XID Value
     * XID  contains spaces
     * XID is present
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * Error(s) found in CID Sent.  Please review the specification for details.
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Lat Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format.
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * Shipping details are character space filled in AAV 205 format
     * AAV Data is Present
     * ScndIdCd is correct
     * ElecComrceInd = 05
     * SafeKey AEVV Value :  AAABBWcSNIdjeUZThmNHAAAAAAA=
     * Check for presence of AESKTransId
     * AESKTransId is present
     * Bit 22.1: CardDataInpCpblCd represents a Card Not Present transaction.
     * Bit 22.4 - OprEnvirCd is represented as a Card Not Present transaction.
     * Bit 22.5 - CMPresentCd represents a Card Not Present transaction.
     * Bit 22.6 - CardPresentCd represents a Card Not Present transaction.
     * Bit 22.7 - CardDataInpModeCd represents a Card Not Present  transaction.
     * AEVV: Authentication Results Code >= '7'
     * AEVV: Authentication Results Code <= '8'
     * AEVV: Second Factor Authentication Code = '00'
     * AEVV: AEVV Key Indicator > 00
     * AEVV: AEVV Key Indicator <= 99
     * AEVV: AEVV output is equals to expected
     * AEVV: Unpredictable Number is Valid
     * Unpredictable Number matches the four least significant digits of the Authentication Tracking Number
     * "
     */
    @Test
    public void testSK10() throws Exception {
        long pan = 374500261001009L;
        long amt = 10100;
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

        secureAuthenticationSafeKeyBuilder
                .ScndIdCd("ASK")
                .ElecComrceInd("05")
                .AmexExpVerificationValTxt("0000010567123487637946538663470000000000")
                .AESKTransId("1234567890ABCDEF12341234567890ABCDEF1234")
        ;

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
    }


    @Test
    public void testReadXml() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><CardTransaction>\n" +
                "    <MsgTypId>1110</MsgTypId>\n" +
                "    <CardNbr>374500262001008</CardNbr>\n" +
                "    <TransProcCd>004000</TransProcCd>\n" +
                "    <TransAmt>2100</TransAmt>\n" +
                "    <MerSysTraceAudNbr>657820</MerSysTraceAudNbr>\n" +
                "    <TransTs>230729211051</TransTs>\n" +
                "    <TransId>000002490544093</TransId>\n" +
                "    <RtrvRefNbr>230729211051</RtrvRefNbr>\n" +
                "    <TransAprvCd>544093</TransAprvCd>\n" +
                "    <MerTrmnlId>00000001</MerTrmnlId>\n" +
                "    <CardAcceptorIdentification>\n" +
                "        <MerId>8127921740</MerId>\n" +
                "    </CardAcceptorIdentification>\n" +
                "    <TransCurrCd>978</TransCurrCd>\n" +
                "    <TransActCd>000</TransActCd>\n" +
                "    <AcptEnvData>\n" +
                "        <Psd2Exemptions>\n" +
                "            <EuPsd2SecCorpPayInd>0</EuPsd2SecCorpPayInd>\n" +
                "            <AuthOutageInd>0</AuthOutageInd>\n" +
                "        </Psd2Exemptions>\n" +
                "        <InitPartyInd>1</InitPartyInd>\n" +
                "    </AcptEnvData>\n" +
                "    <SecureAuthenticationSafeKey>\n" +
                "        <ScndIdCd>ASK</ScndIdCd>\n" +
                "        <AEVVRsltInd>3</AEVVRsltInd>\n" +
                "    </SecureAuthenticationSafeKey>\n" +
                "    <VerificationInformation>\n" +
                "        <ServId>AX</ServId>\n" +
                "        <ReqTypId>AE</ReqTypId>\n" +
                "    </VerificationInformation>\n" +
                "</CardTransaction>";

        AuthorizationRsp response = XmlUtility.getInstance().readFromXML(xml, AuthorizationRsp.class);
        System.out.println(response.toString());
        System.out.println(response.getAcptEnvData().toString());

    }
}
