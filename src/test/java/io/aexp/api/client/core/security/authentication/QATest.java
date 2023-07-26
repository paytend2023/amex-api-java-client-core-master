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


public class QATest {
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

    public static SSLContext createSSLContext(KeyStore keyStore, String keyPassword) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        return sslContext;
    }

    @Test
    public void qaTest601() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(5).messageId("2023071900000001").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder());
        list.add(seSetupBuilder.build());

        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder1 = createSeSetupRequestBuilder("002",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder1());
        list.add(seSetupBuilder1.build());


        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder2 = createSeSetupRequestBuilder("003",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder2(false));
        list.add(seSetupBuilder2.build());


        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder3 = createSeSetupRequestBuilder("004",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder3());
        list.add(seSetupBuilder3.build());


        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder4 = createSeSetupRequestBuilder("004",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder4());
        list.add(seSetupBuilder4.build());


        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        System.out.println(json);
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);
    }


    @Test
    public void qaTest602() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(2).messageId("2023071900000002").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "D",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder());
        list.add(seSetupBuilder.build());
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder1 = createSeSetupRequestBuilder("002",
                "N",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder1());
        list.add(seSetupBuilder1.build());
        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        System.out.println(json);
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);
    }

    @Test
    public void qaTest603() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(1).messageId("2023071900000003").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilderBlankDbaAndPhone());
        list.add(seSetupBuilder.build());
        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        System.out.println(json);
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);
    }

    @Test
    /**
     * 1) reinstate seller that was cancelled as non derog from test condition 6.02
     */
    public void qaTest604_1() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(1).messageId("2023071900000004").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "R",
                "2023/07/17",
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

    @Test
    public void qaTest604_2() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();

        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(1).messageId("2023071900000005").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilderBlankDbaAndPhone(false));
        list.add(seSetupBuilder.build());
        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        System.out.println(json);
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);
    }

    @Test
    public void qaTest604_3() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();

        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(3).messageId("2023071900000006").seSetupRequests(list);

        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = createSeSetupRequestBuilder("001",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder(true));
        list.add(seSetupBuilder.build());

        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder1 = createSeSetupRequestBuilder("002",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder1());
        list.add(seSetupBuilder1.build());

        SignificantOwner significantOwner = SignificantOwner.builder()
                .firstOwner(Owner.builder()
                        .firstName("ZMUIDINAVICIUS")
                        .lastName("LAIMONAS")
                        .dateOfBirth("1972/07/01")
                        .identificationNumber("37207011356")
                        .streetAddress(Address.builder()
                                .addressLine1("Vilnius City Butchery 5")
                                .cityName("Vilnius")
                                .regionCode("58")
                                .postalCode("01113")
                                .countryCode("LT")
                                .build())
                        .build())
                .build();
        seSetupBuilder.significantOwners(significantOwner);


        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder2 = createSeSetupRequestBuilder("003",
                "R",
                "2023/07/17",
                "EN",
                "D",
                createSellerBuilder2(true));
        list.add(seSetupBuilder2.build());


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
        Address address1 = Address.builder()
                .addressLine1("Vilnius City Butchery 5")
                .cityName("Vilnius")
                .regionCode("58")
                .postalCode("01113")
                .countryCode("LT")
                .build();
        Address significantAddress = Address.builder()
                .addressLine1("Vilnius City Butchery 5")
                .cityName("Vilnius")
                .regionCode("58")
                .postalCode("01113")
                .countryCode("LT")
                .build();
        Owner owner1 = Owner.builder()
                .firstName("ZMUIDINAVICIUS")
                .lastName("LAIMONAS")
                .dateOfBirth("1972/07/01")
                .identificationNumber("37207011356")
                .streetAddress(address1)
                .build();

        SignificantOwner significantOwner = SignificantOwner.builder()
                .firstOwner(owner1)
                .build();
        seSetupBuilder.significantOwners(significantOwner);

        AuthorizedSigner authorizedSigner = AuthorizedSigner.builder()
                .firstName("ZMUIDINAVICIUS")
                .lastName("LAIMONAS")
                .dateOfBirth("1972/07/01")
                .identificationNumber("37207011356")
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
        sellerBuilder.sellerId("304730875")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("7996")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
                //  Region, Argentina, Mexico, and Australia. 必填其余地区非必须
                //.sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber("8005284800")
                .sellerEmailAddress("fxx@paytend.COM")
                .sellerCurrencyCode("USD")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
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

    private Seller.SellerBuilder createSellerBuilderBlankDbaAndPhone() {
        return createSellerBuilderBlankDbaAndPhone(true);
    }

    private Seller.SellerBuilder createSellerBuilderBlankDbaAndPhone(boolean isBlank) {
        Seller.SellerBuilder sellerBuilder = Seller.builder();
        sellerBuilder.sellerId("304730878")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("7999")
                .sellerLegalName("PAYTEND EUROPE UAB")

                .sellerDbaName(" ")
//                .sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber(" ")
                .sellerEmailAddress("fxx@paytend.COM")
                .sellerCurrencyCode("JPY")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        if (!isBlank) {
            sellerBuilder.sellerDbaName("PAYTEND EUROPE UAB").sellerBusinessPhoneNumber("8005284800");
        }
        return sellerBuilder;
    }

    private Seller.SellerBuilder createSellerBuilder1() {
        Seller.SellerBuilder sellerBuilder = Seller.builder();
        sellerBuilder.sellerId("304730876")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("7997")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
//                .sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber("8005284801")
                .sellerEmailAddress("fxx@paytend.com")
                .sellerCurrencyCode("EUR")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        return sellerBuilder;
    }

    private Seller.SellerBuilder createSellerBuilder2(boolean isUpdate) {
        Seller.SellerBuilder sellerBuilder = Seller.builder();
        sellerBuilder.sellerId("304730877")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("7998")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
//                .sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber("8005284800")
                .sellerEmailAddress("fxx@paytend.COM")
                .sellerCurrencyCode("EUR")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        if (isUpdate) {
            sellerBuilder.sellerBusinessPhoneNumber("8005284888");
        }
        return sellerBuilder;
    }


    private Seller.SellerBuilder createSellerBuilder3() {
        Seller.SellerBuilder sellerBuilder = Seller.builder();
        sellerBuilder.sellerId("304730878")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("7999")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
//                .sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber("8005284800")
                .sellerEmailAddress("fxx@paytend.COM")
                .sellerCurrencyCode("JPY")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        return sellerBuilder;
    }


    private Seller.SellerBuilder createSellerBuilder4() {
        Seller.SellerBuilder sellerBuilder = Seller.builder();
        sellerBuilder.sellerId("304730879")
                .sellerUrl("www.paytend.com")
                .sellerStatus("02")
                .sellerMcc("8050")
                .sellerLegalName("PAYTEND EUROPE UAB")
                .sellerDbaName("PAYTEND EUROPE UAB")
//                .sellerBusinessRegistrationNumber("304730875")
                .sellerBusinessPhoneNumber("8005284800")
                .sellerEmailAddress("fxx@paytend.com")
                .sellerCurrencyCode("KRW")
                .sellerStartDate("2017/12/11")
                .sellerTermDate("2099/12/11")
                .sellerChargeVolume("0000000000010000")
                .sellerStreetAddress(Address.builder()
                        .cityName("Vilnius City")
                        .regionCode("58")
                        .postalCode("01113")
                        .countryCode("LT")
                        .addressLine1("Vilnius City Butchery 5")
                        .build());
        return sellerBuilder;
    }


    @Test
    public void _sellers() throws IOException {
        AuthProvider authProvider = HmacAuthBuilder.getBuilder().setConfiguration(configurationProvider).build();
        String url = baseUrl + configurationProvider.getValue("SELLERS_RESOURCE");
        String method = "POST";
        List<SeSetupRequest> list = new ArrayList<>();
        SePayLoadRequest.SePayLoadRequestBuilder sePayLoadRequestBuilder = SePayLoadRequest.builder();
        sePayLoadRequestBuilder.seSetupRequestCount(1).messageId("X100MSGAPI1").seSetupRequests(list);
        SeSetupRequest.SeSetupRequestBuilder seSetupBuilder = SeSetupRequest.builder();
        seSetupBuilder.recordNumber("001")
                .participantSe("1230001001")
                .submitterId("SUBMITTER001")
                .seDetailStatusCode("R")
                .seStatusCodeChangeDate("2020/10/31")
                .languagePreferenceCode("EN")
                .japanCreditBureauIndicator("")
                .marketingIndicator("Y")
                .ownershipTypeIndicator("D")
                .sellerTransactingIndicator("Y")
                .clientDefinedCode("36500")
        ;

        Seller.SellerBuilder sellerBuilder = Seller.builder();

        sellerBuilder.sellerId("SAMPLESELLERID1015")
                .sellerUrl("WWW.DEVELOPER.AMERICANEXPRESS.COM/SAMPLE")
                .sellerStatus("02")
                .sellerMcc("7996")
                .sellerLegalName("SAMPLE LEGAL NAME 1")
                .sellerDbaName("SAMPLE DBA NAME 1")
                .sellerBusinessRegistrationNumber("301235511")
                .sellerBusinessPhoneNumber("8005284800")
                .sellerEmailAddress("TEAMEMAIL@SAMPLEMERCHANT.COM")
                .sellerCurrencyCode("USD")
                .sellerStartDate("1984/01/20")
                .sellerTermDate("2023/10/31")
                .sellerChargeVolume("0000000000000101")
                .sellerTransactionCount("100")
                .sellerChargebackCount("2")
                .sellerChargebackAmount("0000000000001000")
                .sellerStreetAddress(Address.builder()
                        .addressLine1("200 VESEY ST")
                        .addressLine2("SELLERSTADDLN2001")
                        .addressLine3("SELLERSTADDLN3001")
                        .addressLine4("SELLERSTADDLN4001")
                        .addressLine5("SELLERSTADDLN5001")
                        .cityName("NEW YORK")
                        .regionCode("NY")
                        .postalCode("10285")
                        .countryCode("US")
                        .build())
        ;

        seSetupBuilder.seller(sellerBuilder.build());


        Address address1 = Address.builder()
                .addressLine1("18850 N 56TH ST")
                .addressLine2("FOSTADDLN2001")
                .addressLine3("FOSTADDLN3001")
                .addressLine4("FOSTADDLN4001")
                .addressLine5("FOSTADDLN5001")
                .cityName("PHOENIX")
                .regionCode("AZ")
                .postalCode("85050")
                .countryCode("US")
                .build();

        Address address2 = Address.builder()
                .addressLine1("3434 N 50th STREET")
                .addressLine2("SECONDOSTADDLN2001")
                .addressLine3("SECONDOSTADDLN3001")
                .addressLine4("SECONDOSTADDLN4001")
                .addressLine5("SECONDOSTADDLN5001")
                .cityName("NEW YORK")
                .regionCode("NY")
                .postalCode("10103")
                .countryCode("US")
                .build();

        Address address3 = Address.builder()
                .addressLine1("4479 N 50th STREET")
                .addressLine2("THIRDOSTADDLN2001")
                .addressLine3("THIRDOSTADDLN3001")
                .addressLine4("THIRDOSTADDLN4001")
                .addressLine5("THIRDOSTADDLN5001")
                .cityName("NEW YORK")
                .regionCode("NY")
                .postalCode("10103")
                .countryCode("US")
                .build();

        Address address4 = Address.builder()
                .addressLine1("4479 N 50th STREET")
                .addressLine2("FOURTHOSTADDLN2001")
                .addressLine3("FOURTHOSTADDLN3001")
                .addressLine4("FOURTHOSTADDLN4001")
                .addressLine5("FOURTHOSTADDLN5001")
                .cityName("NEW YORK")
                .regionCode("NY")
                .postalCode("10103")
                .countryCode("US")
                .build();

        Address significantAddress = Address.builder()
                .addressLine1("6690 N 50th STREET")
                .addressLine2("AUTHSIGNSTREETADD2001")
                .addressLine3("AUTHSIGNSTREETADD3001")
                .addressLine4("AUTHSIGNSTREETADD4001")
                .addressLine5("AUTHSIGNSTREETADD5001")
                .cityName("NEW YORK")
                .regionCode("NY")
                .postalCode("10103")
                .countryCode("US")
                .build();


        Owner owner1 = Owner.builder()
                .firstName("C.F.")
                .lastName("FROST")
                .dateOfBirth("1984/01/20")
                .streetAddress(address1)
                .build();

        Owner owner2 = Owner.builder()
                .firstName("SECONDOFIRSTNM001")
                .lastName("SECONDOLASTNM001")
                .identificationNumber("916204293")
                .dateOfBirth("1984/01/20")
                .streetAddress(address2)
                .build();

        Owner owner3 = Owner.builder()
                .firstName("THIRDOFIRSTNM001")
                .lastName("THIRDOLASTNM001")
                .identificationNumber("368591149")
                .dateOfBirth("1984/01/20")
                .streetAddress(address3)
                .build();

        Owner owner4 = Owner.builder()
                .firstName("FOURTHOFIRSTNM001")
                .lastName("FORUTHOLASTNM001")
                .identificationNumber("916204293")
                .dateOfBirth("1984/01/20")
                .streetAddress(address4)
                .build();


        SignificantOwner significantOwner = SignificantOwner.builder()
                .firstOwner(owner1)
                .secondOwner(owner2)
                .thirdOwner(owner3)
                .fourthOwner(owner4)
                .build();
        seSetupBuilder.significantOwners(significantOwner);


        AuthorizedSigner authorizedSigner = AuthorizedSigner.builder()
                .firstName("AUSGNFIRSTNM001")
                .lastName("AUSGNLASTNM001")
                .identificationNumber("672659926")
                .dateOfBirth("1984/01/20")
                .streetAddress(significantAddress)
                .title("CEO")
                .build();
        seSetupBuilder.authorizedSigner(authorizedSigner);

        Sale sale = Sale.builder()
                .channelIndicatorCode("DS")
                .channelName("DIRECT SALES")
                .representId("36500")
                .isoRegisterNumber("0000036500")
                .build();
        seSetupBuilder.sale(sale);
        list.add(seSetupBuilder.build());
        JsonUtility jsonUtility = JsonUtility.getInstance();
        String json = jsonUtility.getString(sePayLoadRequestBuilder.build());
        String respStr = submitPostRequest(url, authProvider.generateAuthHeaders(json, url, method), json);
        SellerResponse response = jsonUtility.getObject(respStr, SellerResponse.class);
        System.out.println(response);

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
}
