package io.aexp.api.client.core.security.authentication;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.*;
import io.aexp.api.client.core.configuration.ConfigurationKeys;
import io.aexp.api.client.core.configuration.ConfigurationProvider;
import io.aexp.api.client.core.configuration.PropertiesConfigurationProvider;
import io.aexp.api.client.core.utils.XmlUtility;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.aexp.api.client.core.security.authentication.QATest.createSSLContext;
import static io.aexp.api.client.core.security.authentication.QATest.loadKeyStore;
import static org.junit.Assert.assertTrue;

public class AuthorizationTest {
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
        sendNCP(pan, amt, pointOfServiceDataBuilder);
//        sendXML(xml);
    }


    @Test
    public void testNCP4013() throws IOException {
        long pan = 373953192351004L;
        long amt = 8300;
        sendNCP(pan, amt, pointOfServiceDataBuilder);
    }

    @Test
    public void testNCP4014() throws IOException {
        long pan = 341111599241001L;
        long amt = 2000;
        sendNCP(pan, amt, pointOfServiceDataBuilder);
    }

    private String sendXml(String url, String xml, Map<String, String> headers) throws IOException {
        HttpUrl httpUrl = HttpUrl.parse(url);
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        Request.Builder builder = new Request.Builder()
                .url(httpUrlBuilder.build())
                .post(RequestBody.create(TEXT_MEDIA_TYPE, xml));
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
        return "";
    }

    public String sendXML(String xml) throws Exception {
        URL url = new URL("https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do");
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("ContentType", "plain/text");
        conn.setRequestProperty("UserAgent", "Application");
        conn.setRequestProperty("Host", "qwww318.americanexpress.com:443");
        conn.setRequestProperty("ContentLength", String.valueOf(xml.length()));
        conn.setRequestProperty("CacheControl", "no-cache");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Origin", "Paytend");
        conn.setRequestProperty("country", "276");
        conn.setRequestProperty("region", "EMEA");
        conn.setRequestProperty("message", "XML GCAG");
        conn.setRequestProperty("MerchNbr", "3285220521");
        conn.setRequestProperty("RtInd", "050");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.getRequestProperties().entrySet().stream().forEach(entry -> {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        });

        conn.setConnectTimeout(10 * 1000);
        conn.setReadTimeout(120 * 1000);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(xml);

        out.flush();
        System.out.println("send xml successfully!");
        byte[] tmp = new byte[1024];
        InputStream inputStream = conn.getInputStream();
        int ret = inputStream.read(tmp);
        System.out.println("ret1=" + ret);
        if (ret != -1) {
            System.out.println("ret2=" + new String(tmp, 0, ret));
        }
        return "";
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

    private void sendNCP(long pan, long amt, PointOfServiceData.PointOfServiceDataBuilder pointOfServiceDataBuilder) throws IOException {
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
        authorizationBuilder.MsgRsnCd("1900"); //todo
        authorizationBuilder.MerCtgyCd("4111"); //mcc
        authorizationBuilder.RtrvRefNbr("AB1234567890"); //refo

        authorizationBuilder.MerTrmnlId("00000001"); //mcc

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
                .CustEmailAddr("king.gu@gmail.com")
                .CustHostServerNm("www.baidu.com")
                .CustBrowserTypDescTxt("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)")
                .ShipToCtryCd("440")
                .ShipMthdCd("01")
                .MerSKUNbr("TKDC315U")
                .CustIPAddr("127.142.5.56")
                .CustIdPhoneNbr("13651654626")
                .CallTypId("61")
                .build();

//        InternetAirlineCustome.InternetAirlineCustomeBuilder InternetAirlineCustomeBuilder = InternetAirlineCustome.builder())
//        InternetAirlineCustomeBuilder
//                .FareBasisCd1("ABC123")
//        ;
        AdditionalDataNational additionalDataNational = AdditionalDataNational.builder()
                .CardNotPresentData(cardNotPresentData)
//                .InternetAirlineCustome(InternetAirlineCustomeBuilder.build())
//                .CustEmailAddr(InternetAirlineCustomeBuilder.build())
                .build();

        authorizationBuilder.AdditionalDataNational(additionalDataNational);

//        AdditionalDataPrivate.AMEXExtendedPaymentIndicator AMEXExtendedPaymentIndicator =
//                new AdditionalDataPrivate.AMEXExtendedPaymentIndicator();
//        AdditionalDataPrivate additionalDataPrivate = new AdditionalDataPrivate(AMEXExtendedPaymentIndicator);
//        authorizationBuilder.TransCurrCd("840");
        authorizationBuilder.TransCurrCd("978");//Euro

        AcptEnvData acptEnvData = AcptEnvData.builder()
                .InitPartyInd("1")
                .Psd2Exemptions(AcptEnvData.Psd2Exemptions.builder().EuPsd2SecCorpPayInd("0").AuthOutageInd("0")
                        .build())
                .build();
        authorizationBuilder.AcptEnvData(acptEnvData);

        String url = baseUrl;
        String method = "POST";
        System.out.println(url);
        String xml = XmlUtility.getInstance().getString(authorizationBuilder.build());
        System.out.println(xml);

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

}
