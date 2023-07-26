package com.paytend.models.seller.req;

import lombok.Builder;
import lombok.Getter;

/**
 * @author gudongyang
 */
@Builder
@Getter
public class SignificantOwner {

    /**
     * first_owner
     * object REQUIRED
     * This common model will be used for both,
     * Authorized Signer and Significant Owner sections, as they share common attributes.
     */
    Owner firstOwner;

    /**
     * second_owner
     * object REQUIRED
     * This common model will be used for both,
     * Authorized Signer and Significant Owner sections, as they share common attributes.
     */
    Owner secondOwner;


    /**
     * third_owner
     * object REQUIRED
     * This common model will be used for both, Authorized Signer and Significant Owner sections,
     * as they share common attributes.
     */
    Owner thirdOwner;

    /**
     * fourth_owner
     * object REQUIRED
     * This common model will be used for both, Authorized Signer and Significant Owner sections,
     * as they share common attributes.
     */
    Owner fourthOwner;

}
