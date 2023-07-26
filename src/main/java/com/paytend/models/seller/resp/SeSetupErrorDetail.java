package com.paytend.models.seller.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * @author gudongyang
 */

@Builder
@Getter
@ToString
public class SeSetupErrorDetail {

    /**
     * submission_se
     * stringREQUIRED
     * This field is a copy of submission_se field of the request.
     */
    String submissionSe;

    /**
     * seller_id
     * string, max: 20REQUIRED
     * This field is a copy of seller_id field of the request.
     */
    String sellerId;

    /**
     * errors
     * array of objectsREQUIRED
     * This section will contain the specific reason for each rejection.
     */
    List<SeError> errors;


    /**
     * warnings
     * array of objectsREQUIRED
     * This section will contain the specific reason for each warning.
     */
    List<SeWarning> warnings;

    @Tolerate
    public SeSetupErrorDetail() {

    }


}
