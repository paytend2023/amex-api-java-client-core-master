package com.paytend.models.seller.req;

import lombok.Builder;
import lombok.Getter;

/**
 * @author gudongyang
 */
@Builder
@Getter
public class Seller {

    /**
     * seller_id
     * string, max: 20 REQUIRED
     * This alphanumeric field contains the Seller ID,
     * a Merchant-assigned Seller code that uniquely identifies a participant's specific Seller.
     * 收单系统商户号
     */
    String sellerId;

    /**
     * seller_url
     * string, max: 254 OPTIONAL
     * This alphanumeric field contains the primary business web address of the Seller.
     * This field should only be populated for Internet Sellers. In all other cases, this field should be left blank.
     */
    String sellerUrl;

    /**
     * seller_status
     * string, max: 2 OPTIONAL
     * This field contains the code indicating the status of the agreement of the Seller. Valid codes and descriptions are:
     * Code    Description
     * 02   Active
     * 03   Reinstated
     * 04   Terminated
     * 05   Pending Approval
     * 07   Suspended
     * 08   Cancelled/Pay
     * 09   Cancelled/Suspended
     * 00   Non-Agreement
     */
    String sellerStatus;


    /**
     * seller_mcc
     * string, max: 4 REQUIRED
     * This numeric field contains the Merchant Category Code (MCC) that corresponds to
     * the Seller's business type.
     * The MCC must reflect the classification of the Seller rendering the goods or services.
     */
    String sellerMcc;

    /**
     * seller_legal_name
     * seller_legal_name
     * string, max: 60 REQUIRED
     * This alphanumeric field contains the Seller's legal name.
     */
    String sellerLegalName;

    /**
     * seller_dba_name
     * string, max: 60 REQUIRED
     * This alphanumeric field contains the Seller's business name that appears on the store front and/or Customer receipts,
     * commonly referred to as the Doing Business As (DBA) name. If the name is more than 60 characters,
     * use a proper and meaningful abbreviation, when possible. Do not truncate.
     * <p>
     * NOTE: Only the actual business name of the Seller should be provided.
     * Do not include the Participant or Payment facilitator name or delimiter.
     */
    String sellerDbaName;

    /**
     * seller_business_registration_number
     * string, max: 15 REQUIRED
     * REQUIRED: U.S. Region, Argentina, Mexico, and Australia.
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric, left-justified field contain the Seller's Tax Identification Number or
     * their functional equivalents. Entry must be significant characters only with no dashes,
     * spaces, or other separators.
     * <p>
     * The U.S. Region: This field must be populated with the Seller's Federal Tax ID.
     * If the Seller's business is a sole proprietorship and the business does not have a Federal Tax ID,
     * then the Seller's SSN must be populated.
     * <p>
     * Argentina: This field must be populated with the Seller's Unique Tax Identification Code (CUIT).
     * <p>
     * Mexico: This field must be populated with the Seller's Federal Taxpayer Registry Code (RFC).
     * <p>
     * Australia: This field must be populated with the Seller's Australian Business Number (ABN).
     * <p>
     * Region, Argentina, Mexico, and Australia. 必填其余地区非必须
     */
    String sellerBusinessRegistrationNumber;


    /**
     * seller_business_phone_number
     * string, max: 24 REQUIRED
     * This alphanumeric field contains the phone number for the Seller's physical location.
     * Entry must be numerical characters only, with no dashes, spaces, or other separators.
     * Phone numbers must contain the international telephone country code.
     * <p>
     * Example: 52+ (Area Code) + (Phone Number).
     */
    String sellerBusinessPhoneNumber;


    /**
     * seller_email_address
     * string, max: 254REQUIRED
     * REQUIRED: United States
     * <p>
     * CONDITIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field contains the Seller's primary business email address.
     * A default, generic email address cannot be provided by the Participant.
     * <p>
     * This field must be populated for all U.S. Sellers.
     * For all other countries and/or regions,
     * this field must be populated for Internet Sellers and should be provided for all other Sellers
     * if the Seller has provided the data to the Participant.
     * <p>
     * NOTE: Only one Seller email address is allowed in this field.
     * If multiple email addresses are submitted, only the first email address will be retained.
     */
    String sellerEmailAddress;

    /**
     * seller_currency_code
     * string, max: 3 REQUIRED
     * This field contains the Seller's currency code, which is a three-character,
     * alphabetic code that corresponds to the currency applicable for the transaction amounts submitted on the Seller's behalf.
     * Valid values are found in the Global Codes & Information Guide; Section 3.2 Currency-Alpha field.
     */
    String sellerCurrencyCode;

    /**
     * seller_start_date
     * string, max: 10 OPTIONAL
     * This alphanumeric field contains the date when the Participant acquired the Seller.
     * The format is: YYYY/MM/DD where YYYY = Year, MM = Month, DD = Day.
     */
    String sellerStartDate;

    /**
     * seller_term_date
     * string, max: 10 OPTIONAL
     * This alphanumeric field contains the date when the Seller agreement is terminated.
     * The format is: YYYY/MM/DD where YYYY = Year, MM = Month, DD = Day.
     */
    String sellerTermDate;

    /**
     * seller_charge_volume
     * string, max: 16 REQUIRED
     * REQUIRED: India
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field contains the Seller's daily charge volume.
     * This value with an implied decimal must be in the currency designated by the Seller's currency code field.
     * Debit amounts may optionally be signed positive by inserting a plus sign (+) in position 1. However, credit amounts must be signed negative (-).
     * See examples of debit and credit entries.
     * <p>
     * For information on currencies and decimal point positions,
     * see the Global Codes & Information Guide.
     * <p>
     * NOTE: Seller's charge volume, Seller's currency code, and Seller's transaction count are mutually inclusive,
     * which means that if one field contains data, all must be populated.
     * <p>
     * Examples of typical entries in this field:
     * <p>
     * For US Dollar (USD), European Union Euro (EUR) and United Kingdom Pound Sterling (GBP) amounts,
     * two decimal places are implied. Thus, $100.00 USD and €100.00 EUR and a credit for £100.00 GBP would be entered as:
     * <p>
     * 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6
     * 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Debit)
     * + 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Debit)
     * - 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Credit).
     */
    String sellerChargeVolume;

    /**
     * seller_transaction_count
     * string, max: 8 REQUIRED
     * REQUIRED: India
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field contains the Seller's transaction count, which is the total number of transactions submitted by the Seller during the daily period.
     * <p>
     * NOTE: Seller's charge volume, Seller's currency code and Seller's transaction count are mutually inclusive, which means that if one field contains data, all must be populated.
     */
    String sellerTransactionCount;

    /**
     * seller_chargeback_count
     * string, max: 8 OPTIONAL
     * This alphanumeric field contains the Seller's chargeback count.
     */
    String sellerChargebackCount;

    /**
     * seller_chargeback_amount
     * string, max: 16 OPTIONAL
     * This alphanumeric field contains the Seller's chargeback amount for the daily period.
     * This value with an implied decimal must be in the currency designated by the Seller's currency code field.
     * <p>
     * Debit amounts may optionally be signed positive by inserting a plus sign (+) in position 1.
     * However, credit amounts must be signed negative (-). S
     * ee examples of debit and credit entries on next page.
     * <p>
     * For information on currencies and decimal point positions,
     * see the Global Codes & Information Guide.
     * <p>
     * Examples of typical entries in this field:
     * <p>
     * For US Dollar (USD), European Union Euro (EUR) and United Kingdom Pound Sterling (GBP) amounts,
     * two decimal places are implied. Thus, $100.00 USD and €100.00 EUR and a credit for £100.00 GBP would be entered as:
     * <p>
     * 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6
     * 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Debit)
     * + 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Debit)
     * - 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 (Credit).
     */
    String sellerChargebackAmount;

    /**
     * seller_street_address
     * object REQUIRED
     * Details on the Seller's business address.
     */
    Address sellerStreetAddress;

}
