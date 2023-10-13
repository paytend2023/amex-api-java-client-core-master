package io.aexp.api.client.core.security.authentication;

import com.paytend.models.seller.req.*;
import com.paytend.models.seller.req.Address;
import com.paytend.models.seller.resp.SellerResponse;
import io.aexp.api.client.core.configuration.ConfigurationKeys;
import io.aexp.api.client.core.configuration.ConfigurationProvider;
import io.aexp.api.client.core.configuration.PropertiesConfigurationProvider;
import io.aexp.api.client.core.utils.JsonUtility;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

/**
 * @author Sunny
 * @create 2023/8/24 13:49
 */
public class ProSMTest {

    private ConfigurationProvider configurationProvider;
    private static final String propertiesFileName = "qa.test.properties";
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient httpClient;
    private String baseUrl;


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
        }).build();
    }

    public static KeyStore loadKeyStore(String keystorePath, String keystorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        ClassLoader classLoader = QATest.class.getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("keystore.jks"), keystorePassword.toCharArray());
        return keyStore;
    }

    public static SSLContext createSSLContext(KeyStore keyStore, String keyPassword)
            throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        return sslContext;
    }


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

    /**
     * 生产环境进件测试
     *
     * @throws IOException
     */
    @Test
    public void qaTest601() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(1).messageId("2023082400000001").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "",
                "",
                "EN",
                "D",
                createSellerBuilder());
        list.add(seSetupBuilder.build());

        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        System.out.println(json);
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);
    }

    private SeSetupRequest.SeSetupRequestBuilder createSeSetupRequestBuilder(String recordNumber,
                                                                             String detailStatusCode,
                                                                             String statusCodeChangeDate,
                                                                             String languagePreferenceCode,
                                                                             String ownershipTypeIndicator,
                                                                             Seller.SellerBuilder sellerBuilder) {
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = SeSetupRequest.builder();
        seSetupBuilder.recordNumber(recordNumber)
                .participantSe("8127921740")
//                .submitterId("SUBMITTER001")
                .seDetailStatusCode(detailStatusCode)
                .seStatusCodeChangeDate(statusCodeChangeDate)
                .languagePreferenceCode(languagePreferenceCode)
//                .japanCreditBureauIndicator("Y")
                .marketingIndicator("N")
                .ownershipTypeIndicator(ownershipTypeIndicator)
//                .sellerTransactingIndicator("Y")
        ;
        com.paytend.models.seller.req.Address address1 = com.paytend.models.seller.req.Address.builder()
                .addressLine1("31 Sancun,")
                .addressLine2("Daiwangcheng town,")
                .addressLine2("Yuxian county,")
                .cityName("Zhangjiakou")
//                .regionCode("58")
                .postalCode("075000")
                .countryCode("CN")
                .build();

        com.paytend.models.seller.req.Address significantAddress = Address.builder()
                .addressLine1("31 Sancun,")
                .addressLine2("Daiwangcheng town,")
                .addressLine2("Yuxian county,")
                .cityName("Zhangjiakou")
                .postalCode("075000")
                .countryCode("CN")
                .build();

        Owner owner1 = Owner.builder()
                .firstName("Junqing")
                .lastName("Li")
                .dateOfBirth("1980/05/03")
//                .identificationNumber("37207011356")
                .streetAddress(address1)
                .build();

        SignificantOwner significantOwner = SignificantOwner.builder()
                .firstOwner(owner1)
                .build();
        seSetupBuilder.significantOwners(significantOwner);

        AuthorizedSigner authorizedSigner = AuthorizedSigner.builder()
                .firstName("Junqing")
                .lastName("Li")
                .dateOfBirth("1980/05/03")
//                .identificationNumber("37207011356")
                .streetAddress(significantAddress)
                .title("CEO")
                .build();

        seSetupBuilder.authorizedSigner(authorizedSigner);
        Sale sale = Sale.builder()
                .channelIndicatorCode("DS")
                .channelName("DIRECT SALES")
                .build();
        seSetupBuilder.sale(sale);
        seSetupBuilder.seller(sellerBuilder.build());
        return seSetupBuilder;
    }


    private Seller.SellerBuilder createSellerBuilder() {
        return createSellerBuilder(false);
    }

    private Seller.SellerBuilder createSellerBuilder(boolean isUpdate) {
        Seller.SellerBuilder sellerBuilder = Seller.builder();

//        内部商户号 有要求吗304730875
        sellerBuilder.sellerId("900000000000001")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                // todo   6012 真实的测试没有过  7996 测试过了非真实
                .sellerMcc("7996")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
                //  Region, Argentina, Mexico, and Australia. 必填其余地区非必须
//                .sellerBusinessRegistrationNumber("304730875")
                // todo
                .sellerBusinessPhoneNumber("37061088300")
                .sellerEmailAddress("fxx@paytend.COM")
                .sellerCurrencyCode("EUR")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                //todo 限额暂时不上送
//                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        if (isUpdate) {
            sellerBuilder.sellerStreetAddress(Address.builder()
                    .cityName("Vilnius City")
                    .regionCode("58")
                    .postalCode("01113")
                    .countryCode("LT")
                    .addressLine1("Vilnius City sav. Vilnius City Butchery 5")
                    .build());
        }
        return sellerBuilder;
    }


}
