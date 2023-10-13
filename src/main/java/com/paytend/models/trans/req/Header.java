package com.paytend.models.trans.req;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gudongyang
 */
@Builder
public class Header {

    private String origin;
    private String country;
    private String region;
    private String message;
    private String merchNbr;
    private String rtInd;


    public Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>(6);
        headers.put("Origin", origin);
        headers.put("Country", country);
        headers.put("region", region);
        headers.put("Message", message);
        headers.put("MerchNbr", merchNbr);
        headers.put("RtInd", rtInd);
        return headers;
    }

    public static Map<String, String> defaultHeaders() {
        return Header.builder()
                .origin("Paytend")
                .country("276")
                .region("EMEA")
                .message("XML GCAG")
                .merchNbr("3285220521")
                .rtInd("050")
                .build().buildHeaders();
    }
}
