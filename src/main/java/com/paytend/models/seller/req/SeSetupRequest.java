package com.paytend.models.seller.req;

import lombok.Builder;
import lombok.Getter;

/**
 * array of objectsREQUIRED
 * Details for the new Seller set-up, maintenance of existing Sellers, and cancelled Sellers.
 *
 * @author gudongyang
 */
@Builder
@Getter
public class SeSetupRequest {

    /**
     * record_number
     * string, max: 11 REQUIRED
     * This numeric field contains a record sequence number that is used to monitor the integrity and continuity of data.
     * The first record is always assigned the value of 1, and each subsequent record in the request must be incremented to an incremental value of 1,
     * more than the record number entry in the preceding record.
     */
    String recordNumber;


    /**
     * participant_se
     * string, max: 10 REQUIRED
     * This alphanumeric field contains the American Express-assigned Service Establishment (SE) number
     * that is used to submit Sponsored Merchant transactions.
     */
    String participantSe;

    /**
     * submitter_id
     * string, max: 32 OPTIONAL
     * This alphanumeric field will contain the submitter ID;
     * a unique identifier code assigned by American Express to the participants.
     */
    String submitterId;

    /**
     * se_detail_status_code
     * string, max: 1 REQUIRED
     * This alphanumeric field is used to notify American Express that a Seller is cancelled with the Participant.
     * It is also used to notify American Express when a Seller is reinstated with the Participant. If there is no change in the Seller's status, leave this field blank. Valid values are:
     * D = Canceled Derogatory
     * N = Canceled Non-Derogatory
     * R = Reinstatement of previously canceled Seller.
     */
    String seDetailStatusCode;

    /**
     * se_status_code_change_date
     * string, max: 10 REQUIRED
     * This alphanumeric field is required when a value has been entered in the SE detail status code
     * field. If there is no change in the Seller's status, leave this field blank.
     * The format is: YYYY/MM/DD where YYYY = Year, MM = Month, DD = Day.
     * <p>
     * For example, February 16, 2011 would be entered as: 2011/02/16.
     */
    String seStatusCodeChangeDate;

    /**
     * language_preference_code
     * string, max: 2 REQUIRED
     * This alphanumeric field is the marketing language preference for the Seller.
     * Valid values are documented in the Language Codes Guide.
     */
    String languagePreferenceCode;

    /**
     * japan_credit_bureau_indicator
     * string, max: 1 OPTIONAL
     * REQUIRED: Australia, Canada, and New Zealand.
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field indicates whether the Seller accepts Japan Credit Bureau (JCB) Cards.
     * Valid values are:
     * Y - JCB acceptor and JCB was offered
     * A - JCB acceptor and JCB was not offered
     * N - Does not accept JCB and JCB was not offered
     * O - Does not accept JCB and JCB was offered
     */
    String japanCreditBureauIndicator;

    /**
     * marketing_indicator
     * string, max: 1 REQUIRED
     * This alphanumeric field contains a single-character indicator which designates the American Express
     * marketing parameters for the Seller. Valid values are:
     * Y = All Marketing (Seller has not opted out of receiving marketing)
     * N = No Marketing (Seller has opted out of receiving marketing).
     * <p>
     * NOTE: Countries in the European Union are required to default the value to N. For U.S. Region
     * - if the value is not provided, American Express will default the value to Y.
     *
     * 营销标识 欧盟国家默认填N 卖家不接受营销
     */
    String marketingIndicator;


    /**
     * ownership_type_indicator
     * string, max: 1 REQUIRED
     * REQUIRED: U.S. Region.
     * <p>
     * OPTIONAL: All other regions and/or countries.
     * <p>
     * This alphanumeric field contains a single-character indicator which identifies the business ownership type of the Seller.
     * <p>
     * Valid values for Corporation:
     * D = Corporation Private
     * E = Corporation Publicly-Traded
     * Valid values for Limited Company:
     * M = LLC Private
     * N = LLC Publicly-Traded
     * Valid values for Partnership:
     * Q = Partnership Private
     * R = Partnership Publicly-Traded
     * Valid value for Sole Trader:
     * S = Sole Proprietorship
     * Valid value for Charities:
     * F = Non-Profit
     * Valid value for Government:
     * G = Government
     * Valid value for Other:
     * X = American Express Reserved Indicator
     * 商户类别 私有 政府 。。。。。
     */
    String ownershipTypeIndicator;

    /**
     * seller_transacting_indicator
     * string, max: 1 OPTIONAL
     * This alphanumeric field contains a single character indicator which identifies
     * if the Seller has accepted bank card transactions in the past three months. Valid values are:
     * Y = Seller has accepted a bank card transaction in the past three months.
     * N = Seller has not accepted a bank card transaction in the past three months.
     * Blank = will be accepted since this is an optional filed.
     *
     * 过去3个月是否接受过银行卡交易
     */
    String sellerTransactingIndicator;

    /**
     * client_defined_code
     * string, max: 18 OPTIONAL
     * This alphanumeric field is intended for Participant use.
     * If unused, it is character-space-filled.
     */
    String clientDefinedCode;

    /**
     * seller
     * object REQUIRED
     * Detailed information on the Seller.
     */
    Seller seller;

    /**
     * significant_owners
     * object REQUIRED
     * Detailed information on the Significant Owner(s) of the Merchant.
     */
    SignificantOwner significantOwners;


    /**
     * authorized_signer
     * object REQUIRED
     * This common model will be used for both, Authorized Signer and Significant Owner sections,
     * as they share common attributes.
     */
    AuthorizedSigner authorizedSigner;

    /**
     *
     * object OPTIONAL
     * Information on signings by the sales channel.
     */
    Sale sale;
}


