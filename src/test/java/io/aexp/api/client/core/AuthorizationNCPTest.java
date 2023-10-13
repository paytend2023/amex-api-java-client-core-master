package io.aexp.api.client.core;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import com.google.common.io.BaseEncoding;
import com.paytend.models.trans.req.*;
import com.paytend.models.trans.req.AcptEnvData.Psd2Exemptions;
import io.aexp.api.client.core.configuration.ConfigurationKeys;
import io.aexp.api.client.core.configuration.ConfigurationProvider;
import io.aexp.api.client.core.configuration.PropertiesConfigurationProvider;
import io.aexp.api.client.core.security.authentication.AuthProvider;
import io.aexp.api.client.core.security.authentication.HmacAuthBuilder;
import io.aexp.api.client.core.security.authentication.QATest;
import io.aexp.api.client.core.utils.XmlUtility;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.*;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.aexp.api.client.core.security.authentication.QATest.createSSLContext;
import static io.aexp.api.client.core.security.authentication.QATest.loadKeyStore;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizationNCPTest {
    private ConfigurationProvider configurationProvider;
    private static final String propertiesFileName = "auth.test.properties";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/xml; charset=utf-8");
    private static final MediaType TEXT_MEDIA_TYPE = MediaType.parse("plain/text;");
    private static AuthProvider authProvider;
    private OkHttpClient httpClient;
    private String baseUrl;
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


    @Before
    public void setup() throws Exception {
        PropertiesConfigurationProvider provider = new PropertiesConfigurationProvider();
        ClassLoader classLoader = QATest.class.getClassLoader();
        provider.loadProperties(Objects.requireNonNull(classLoader.getResource(propertiesFileName)).openStream());
        configurationProvider = provider;
        baseUrl = configurationProvider.getValue(ConfigurationKeys.BASE_URL);
        KeyStore keyStore = loadKeyStore("keystore.jks", "111111");
        SSLContext sslContext = createSSLContext(keyStore, "111111");
        httpClient = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory(), new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
                .build();
        authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();

    }

    /**
     * 4012 - Basic CNP - Approved
     * <p></p>
     * <p>Use PAN: 373953192351004</p>
     * <p>Transaction Amount: 7600 - 7900 (e.g. $76.00 - $79.00)</p>
     * <p>Verify the POS Data Code represents a Card Not Present transaction.</p>
     * <p>Return Action Code : 000</p>
     * <p><br/></p>
     * <p><i>
     * <u>This test case is NOT to be used for AAV (Address Verification) or CID (4DBC) Certification.</u>
     * </i></p><p><i>
     * <u>If AAV and/or CID Data is present an 'Unchecked' response will be sent back in the 1110 Authorization Response.</u>
     * </i></p>
     * <p>
     * <p>
     * POS Data Code represents a Card Not Present transaction.
     * Check for presence of CID (Not Required to be sent)
     * CID Must be 4 Digits
     * 4 Digit CID Data is Present
     * Check for presence of AAV (Not Required to be sent)
     * Zip Code Format
     * CM Billing Address Format
     * AAV Length > = 078
     * AAV Data is Present
     * CM First Name Format
     * CM Last Name Format
     * Check for presence of AAV (205) (Not Required to be sent)
     * AAV Data is present
     * CM First and Last Name should not be character space filled in AAV 078 format
     * CM Billing Phone Number Format
     * Ship -To Postal Code Format
     * Ship-To Address Format
     * Ship-To First Name Format
     * Ship-To Last Name Format
     * Ship-To Phone Number Format
     * Ship-To Country Code Format
     * 205 Byte Format Shipping details should not be completely character space filled.
     * AAV Data is Present
     * Bit 22.1:  CardDataInpCpblCd represents a CNP transaction.
     * Bit 22.4 - OprEnvirCd is represented as a CNP transaction.
     * Bit 22.5 - CMPresentCd represents a CNP transaction.
     * Bit 22.6 - CardPresentCd represents a CNP transaction.
     * Bit 22.7 - CardDataInpModeCd represents a CNP transaction.
     */
    @Test
    public void testBasicCNP4012() throws Exception {
        long pan = 373953192351004L;
        long amt = 7600;
        sendNCP(pan, amt, pointOfServiceDataBuilder, null);
//        sendXML(xml);
    }


    @Test
    public void testNCP4013() throws Exception {
        long pan = 373953192351004L;
        long amt = 8300;
        sendNCP(pan, amt, pointOfServiceDataBuilder, null);
    }


    @Test
    public void testNCP4014() throws Exception {
        long pan = 341111599241001L;  //341111599241000L module 10 check passed
        long amt = 2000;
        //        mod10 检查不能发送请求
//        sendNCP(pan, amt, pointOfServiceDataBuilder, null);

    }


    private String sendXml(String url, String xml, Map<String, String> headers) throws Exception {
        HttpUrl httpUrl = HttpUrl.parse(url);
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        Request.Builder builder = new Request.Builder()
                .url(httpUrlBuilder.build())
                .post(RequestBody.create(TEXT_MEDIA_TYPE, "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml)));
        for (Map.Entry<String, String> header : headers.entrySet()) {
            System.out.println(header.getKey() + " = " + header.getValue());
            builder.addHeader(header.getKey(), header.getValue());
        }
        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        String tmp = response.body().string();
        assertTrue(response.isSuccessful());
        System.out.println("" + response.code());
        try {
            System.out.println("resp:" + XmlUtility.getInstance().xmlBeautifulFormat(tmp));
        } catch (Exception e) {
            System.out.println(tmp);
            e.printStackTrace();
        }
        return tmp;
    }


    public void buildTransTs(Authorization.AuthorizationBuilder authorizationBuilder) {
        //YYMMDDhhmmss
        authorizationBuilder.TransTs(DateUtil.format(new Date(), "yyyyMMddHHmmss").substring(2));

    }

    public void buildDprtDt(InternetAirlineCustome.InternetAirlineCustomeBuilder InternetAirlineCustomeBuilder) {
        //YYMMDDhhmmss
        InternetAirlineCustomeBuilder.DprtDt(DateUtil.format(new Date(), "CCYYMMDD"));
    }

    /**
     * Builds and submits post request. Unit test succeeds if status code is in [200, 300] range.
     * Amex requires the TLS 1.2, The test cases contained in this file employ TLS 1.2 please insure to retain the TLS 1.2 standard for any additions.
     *
     * @param url     Resource URL
     * @param headers Request headers
     * @param payload JSON payload
     * @throws IOException Exception
     */
    private String submitPostRequest(String url, Map<String, String> headers, String payload) throws IOException {
        HttpUrl httpUrl = HttpUrl.parse(url);
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        Request.Builder builder = new Request.Builder()
                .url(httpUrlBuilder.build())
                .post(RequestBody.create(JSON_MEDIA_TYPE, payload));
        for (Map.Entry<String, String> header : headers.entrySet()) {
            System.out.println(header.getKey() + " = " + header.getValue());
            builder.addHeader(header.getKey(), header.getValue());
        }
        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        String tmp = response.body().string();
        System.out.println("Response: " + tmp);
        System.out.println(response.code());
        assertTrue(response.isSuccessful());
        return tmp;
    }


    @Test
    public void testDate() {
        System.out.println(DateUtil.format(new Date(), "yyyymmddHHmmss"));
        System.out.println(DateUtil.format(new Date(), "yyyymmddHHmmss").substring(2));
    }

    private void sendNCP(long pan,
                         long amt,
                         PointOfServiceData.PointOfServiceDataBuilder pointOfServiceDataBuilder,
                         SecureAuthenticationSafeKey.SecureAuthenticationSafeKeyBuilder SecureAuthenticationSafeKeyBuilder) throws Exception {

        Authorization.AuthorizationBuilder authorizationBuilder = Authorization.builder();
        authorizationBuilder.MsgTypId("1100");
        authorizationBuilder.CardNbr(String.valueOf(pan));
        authorizationBuilder.TransProcCd("004000");
        authorizationBuilder.TransAmt(String.valueOf(amt));
        String tmp = "000000" + new Random().nextLong();
        authorizationBuilder.MerSysTraceAudNbr(tmp.substring(tmp.length() - 6));
        buildTransTs(authorizationBuilder);
        authorizationBuilder.CardExprDt("2501");
        authorizationBuilder.AcqInstCtryCd("276");
        authorizationBuilder.PointOfServiceData(pointOfServiceDataBuilder.build());
        authorizationBuilder.FuncCd("100");
        authorizationBuilder.MsgRsnCd("1900");
        authorizationBuilder.MerCtgyCd("4111"); //mcc
        authorizationBuilder.RtrvRefNbr(DateUtil.format(new Date(), "yyyyMMddHHmmss").substring(2)); //refo
        authorizationBuilder.MerTrmnlId("00000001"); //
        CardAcceptorIdentification cardAcceptorIdentification = CardAcceptorIdentification.builder()
                .MerId("8127921740")
                .build();

        authorizationBuilder.CardAcceptorIdentification(cardAcceptorIdentification);
        CardAcceptorDetail cardAcceptorDetail = CardAcceptorDetail.builder()
                .CardAcptNm("PAYTEND EUROPE UAB")
                .CardAcptStreetNm("Vilnius City sav")
                .CardAcptCityNm("Vilnius")
                .CardAcptPostCd("01113")
                .CardAcptRgnCd("58")
                .CardAcptCtryCd("440")
                .build();

        authorizationBuilder.CardAcceptorDetail(cardAcceptorDetail);
        CardNotPresentData cardNotPresentData = CardNotPresentData.builder()
                .custEmailAddr("king.gu@gmail.com")
                .custHostServerNm("www.baidu.com")
                .custBrowserTypDescTxt("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)")
                .shipToCtryCd("440")
                .shipMthdCd("01")
                .merSKUNbr("TKDC315U")
                .custIPAddr("127.142.5.56")
                .custIdPhoneNbr("13651654626")
                .callTypId("61")
                .build();

        AdditionalDataNational additionalDataNational = AdditionalDataNational.builder()
                .CardNotPresentData(cardNotPresentData)
                .build();
        authorizationBuilder.AdditionalDataNational(additionalDataNational);
        authorizationBuilder.TransCurrCd("978");//Euro
        AcptEnvData acptEnvData = AcptEnvData.builder()
                .InitPartyInd("1")
                .psd2Exemptions(Psd2Exemptions.builder()
                        .EuPsd2SecCorpPayInd("0")
                        .AuthOutageInd("0")
                        .build())
                .build();
        authorizationBuilder.AcptEnvData(acptEnvData);

        if (SecureAuthenticationSafeKeyBuilder != null) {
            authorizationBuilder.SecureAuthenticationSafeKey(SecureAuthenticationSafeKeyBuilder.build());
        }

        String xml = XmlUtility.getInstance().getString(authorizationBuilder.build());
        System.out.println("request:" + xml);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "plain/text");
        headers.put("User-Agent", "Application;");
        headers.put("Cashe-Control", "no-cache");
        headers.put("Connection", "Keep-Alive");
        headers.put("Origin", "Paytend");
        headers.put("Country", "276");
        headers.put("region", "EMEA");
        headers.put("Host", baseUrl);
        headers.put("Message", "XML GCAG");
        headers.put("MerchNbr", "3285220521");
        headers.put("RtInd", "050");
        sendXml(baseUrl, xml, headers);
    }


    @Test
    public void testModule10() throws Exception {
        assertTrue(checkModule10(374245005741003L));
        assertFalse(checkModule10(374245005741004L));
        assertFalse(checkModule10(341111599241001L));
        System.out.println(checkModule10(341111599241000L));
    }

    /**
     * modeule 10 check
     *
     * @param pan
     * @return
     * @throws Exception
     */
    private boolean checkModule10(long pan) throws Exception {
        //get last num
        int checkVal = (int) (pan % 10);
        String tmp = String.valueOf(pan);
        char[] panCs = tmp.substring(0, tmp.length() - 1).toCharArray();
        char[] cs = new char[panCs.length];
        System.arraycopy(panCs, 0, cs, 0, cs.length);
        for (int pos = panCs.length - 1; pos >= 0; pos -= 2) {
            int n = (panCs[pos] - 0x30) * 2;
            if (n >= 10) {
                n = n / 10 + n % 10;
            }
            cs[pos] = (char) (n + 0x30);
        }
        int sum = 0;
        for (char c : cs) {
            sum += c - 0x30;
        }
        int calcCheckValue = sum % 10;
        if (calcCheckValue != 0) {
            calcCheckValue = 10 - calcCheckValue;
        }
        return checkVal == calcCheckValue;
    }

    @Test
    public void checkBase64() {
//        0000010567123487637946538663470000000000
        System.out.println(HexUtil.encodeHexStr(Base64.decode("AAABBWcSNIdjeUZThmNHAAAAAAA=")));
         System.out.println(Base64.encode(HexUtil.decodeHex("0000010567123487637946538663470000000000")));
        System.out.println(HexUtil.encodeHexStr(BaseEncoding.base64().decode("AAABAURAWAAAAAAAAEBYAAAAAAA=")));
         System.out.println(HexUtil.encodeHexStr(BaseEncoding.base64().decode("CACYB0gSNIJ4V2hIBglWAAAAAAA=")));
        System.out.println(HexUtil.encodeHexStr(BaseEncoding.base64().decode("BwABB0gSNIJ4V2hIBglWAAAAAAA=")));

    }
}
