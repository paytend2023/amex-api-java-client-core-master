package com.paytend.models.seller.req;


import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Detailed information on the Significant Owner(s) of the Merchant.
 *
 * @author gudongyang
 */
@SuperBuilder
@Getter
public class Owner {


    /**
     * first_name
     * string, max: 20 REQUIRED
     * This alphanumeric field contains the Seller's Significant/Beneficial Owner first name.
     * Required for the following ownership types in the U.S. Region:
     * Corporation Private
     * LLC Private
     * Partnership Private
     * Sole Proprietorship
     * Non-Profit
     * For the United Kingdom and European Economic Area (EEA) countries where the Beneficial Owner has been collected
     * (as required by local regulation or internal policy), it must be included.
     */
    protected String firstName;

    /**
     * last_name
     * string, max: 20 REQUIRED
     * This alphanumeric field contains the Seller's Significant/Beneficial Owner last name.
     * Required for the following ownership types in the U.S. Region:
     * Corporation Private
     * LLC Private
     * Partnership Private
     * Sole Proprietorship
     * Non-Profit
     * For the United Kingdom and European Economic Area (EEA) countries where the Beneficial Owner has been collected
     * (as required by local regulation or internal policy), it must be included.
     */
    protected String lastName;


    /**
     * identification_number
     * string, max: 30 REQUIRED
     * These alphanumeric field contains the Identification Number/SSN of the Seller's Significant/Beneficial Owner.
     * Required for the following ownership types in the U.S. Region:
     * <p>
     * Corporation Private
     * LLC Private
     * Partnership Private
     * Sole Proprietorship
     * Non-Profit
     * For the United Kingdom and European Economic Area (EEA) countries where the Beneficial Owner has been collected
     * (as required by local regulation or internal policy),
     * it must be included. Entry must be significant characters only with no dashes, spaces, or other separators.
     */
    protected String identificationNumber;


    /**
     * date_of_birth
     * string, max: 10 REQUIRED
     * This field contains the date of birth of the person (Authorized Owner or Significant Signer).
     * The format is: YYYY/MM/DD Where: YYYY = Year, MM = Month, DD = Day.
     * <p>
     * Entry must be numeric only, with no dashes, spaces, or other separators.
     * <p>
     * This numeric field contains the date of birth of the Seller's Significant/Beneficial Owner.
     * Required for the following ownership types in the U.S. Region:
     * Corporation Private
     * LLC Private
     * Partnership Private
     * Sole Proprietorship
     * Non-Profit
     * For the United Kingdom and European Economic Area (EEA) countries where the Beneficial Owner has been collected
     * (as required by local regulation or internal policy) it must be included.
     * <p>
     * For example, February 16, 2011, would be entered as: 2011/02/16.
     */
    protected String dateOfBirth;


    /**
     * street_address
     * object REQUIRED
     * Details on the Significant Owner's residential address.
     */
    protected Address streetAddress;
}
