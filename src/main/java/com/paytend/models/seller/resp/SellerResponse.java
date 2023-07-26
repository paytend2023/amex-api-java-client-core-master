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
 public class SellerResponse {
   /**
    * message_id
    * string, max: 15 REQUIRED
    * This field is a 15-character length unique alphanumeric reference number.
    * It can be used as a request identifier since it is unique for each request.
    */
   String messageId;

   /**
    * number_of_setups_processed
    * integer, max: 4 REQUIRED
    * This field contains a number which tells how many Seller acquisition records are processed successfully.
    */

   String numberOfSetupsProcessed;


   /**
    * number_of_setups_with_warnings
    * integer, max: 4 REQUIRED
    * This field contains a number which tells how many Seller acquisition records are processed with one or more warnings.
    * Consumers can send valid data as updates to clear those warnings later.
    */
   String numberOfSetupsWithWarnings;
   /**
    * number_of_setups_with_errors
    * integer, max: 4 REQUIRED
    * This field contains a number which tells how many Seller acquisition records are rejected without processing.
    * \Below se_setup_error_details section will have more detailed information about the reasons.
    */

   String numberOfSetupsWithErrors;

   /**
    * se_setup_error_details
    * array of objectsREQUIRED
    * Details for errors/warnings that occurred during the request processing.
    */
   List<SeSetupErrorDetail> seSetupErrorDetails;

   @Tolerate
  public SellerResponse() {
  }
}
