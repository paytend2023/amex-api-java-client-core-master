package com.paytend.models.trans.req;

import io.aexp.api.client.core.utils.XmlUtility;
import okhttp3.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author gudongyang
 */
public class TransCommUtils {

    private static String baseUrl = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";
    private static Map<String, String> headers = new HashMap<>();

    private TransCommUtils() {
    }

    private static OkHttpClient httpClient;

    static {
        KeyStore keyStore = null;
        try {
            keyStore = loadKeyStore("keystore.jks", "111111");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSLContext sslContext = null;
        try {
            sslContext = createSSLContext(keyStore, "111111");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        headers.put("Content-Type", "plain/text");
        headers.put("User-Agent", "Application;");
        headers.put("Cashe-Control", "no-cache");
        headers.put("Connection", "Keep-Alive");
        headers.put("Host", baseUrl);
    }

    private static final MediaType TEXT_MEDIA_TYPE = MediaType.parse("plain/text;");


    public static String toRequestString(String xml) {
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }

    public static String sendXml(String xml, Map<String, String> customHeaders) throws Exception {
        HttpUrl httpUrl = HttpUrl.parse(baseUrl);
        HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
        Request.Builder builder = new Request.Builder()
                .url(httpUrlBuilder.build())
                .post(RequestBody.create(TEXT_MEDIA_TYPE, toRequestString(xml)));
        for (Map.Entry<String, String> header : headers.entrySet()) {
            System.out.println(header.getKey() + " = " + header.getValue());
            builder.addHeader(header.getKey(), header.getValue());
        }
        for (Map.Entry<String, String> header : customHeaders.entrySet()) {
            System.out.println(header.getKey() + " = " + header.getValue());
            builder.addHeader(header.getKey(), header.getValue());
        }

        Request request = builder.build();
        Response response = httpClient.newCall(request).execute();
        String tmp = response.body().string();
        System.out.println("" + response.code());
        System.out.println(XmlUtility.getInstance().xmlBeautifulFormat(tmp));
        return tmp;
    }

    private static KeyStore loadKeyStore(String keystorePath, String keystorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        ClassLoader classLoader = TransCommUtils.class.getClassLoader();
        keyStore.load(classLoader.getResourceAsStream(keystorePath), keystorePassword.toCharArray());
        return keyStore;
    }

    private static SSLContext createSSLContext(KeyStore keyStore, String keyPassword) throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyPassword.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        return sslContext;
    }

}
