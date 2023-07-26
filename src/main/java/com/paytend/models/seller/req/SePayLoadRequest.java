package com.paytend.models.seller.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author gudongyang
 * 商户入网报文结构
 */
@Builder
@Getter
public class SePayLoadRequest {
    /**
     * se_setup_request_count
     * integer, max: 4 REQUIRED
     * This numeric field specifies how many records are present in the se_setup_requests section
     * below.his numeric field specifies how many records are present in the se_setup_requests
     * section below.
     */
    Integer seSetupRequestCount;

    /**
     * message_id
     * string, max: 15 REQUIRED
     * This field is a 15-character, unique, alphanumeric reference number.
     * It is unique for each request and can be used as the request identifier.
     */
    String messageId;

    /**
     * se_setup_requests
     */
    List<SeSetupRequest> seSetupRequests;
}
