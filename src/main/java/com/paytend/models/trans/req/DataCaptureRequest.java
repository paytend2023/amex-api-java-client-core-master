package com.paytend.models.trans.req;

import com.paytend.models.trans.XmlRequest;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @author Sunny
 * @create 2023/8/19 10:41
 */
@Getter
@Setter
@SuperBuilder
public class DataCaptureRequest extends BaseSubmitRequest implements XmlRequest {

    /**
     * Field 8
     * Data Length: 13 bytes
     * Data Element Type: Alphanumeric
     * Required Field:  Yes
     * Description: This is a unique number assigned by the submitter to identify this transaction within a batch.
     * This is important for identifying VOID capture messages.
     * Example: <RefNumber>123456</RefNumber>
     */
    protected String RefNumber;

    /**
     * Field 9
     * Data Length: 30 bytes
     * Data Element Type: Alphanumeric, upper case, left justified, character space filled
     * Required Field:No
     * <p>
     * This field contains a reference number that is used by American Express to obtain supporting information on a
     * charge from a Merchant. This may be the original invoice number that appears on the receipt generated by
     * a Point of Sale (POS) terminal or the reference number associated with a specific transaction in a Merchant’s
     * computerized cash register or order processing system.  This number can be any combination of characters and
     * numerals defined by the Merchant or submitter. However, it must be a cross-reference to the Merchant’s record,
     * so that charge information can be readily retrieved. American Express recommends that for credit transactions,
     * this field contain the value assigned to the corresponding debit transaction.  During certification, Merchants,
     * authorized Third Party Processors and Vendors must demonstrate the ability to provide and transmit appropriate
     * and meaningful information in this optional field, as well as all required fields.
     *
     * <InvRefNbr>123456</InvRefNbr>
     */
    protected String InvRefNbr;


    /**
     * Data Length: 19 bytes, maximum
     * Data Element Type: Numeric
     * Required Field: Yes
     * <p>
     * Description: This field contains the Cardmember's account number.The submitter must ensure that only American
     * Express and/or American Express Partners' transactions appear in the Data Capture Request Message.
     * Invalid account numbers will result in chargebacks or rejections.
     * Merchants, Third Party Processors, and Vendors in JCB- acquired markets must be certified to process JCB transactions.
     * For details, refer to Cardmember Number Identification in the American Express Global Codes & Information Guide.
     * All Primary Account Numbers should be validated. For details, refer to Cardmember Number Identification
     * in the American Express Global Codes & Information Guide.
     * Example: <CardNbr>XXXXXXXXXXXXXXX</CardNbr>
     */
    protected long CardNbr;

    /**
     * Data Length: 4 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: No
     * <p>
     * Description: This field contains the year and month after which a plastic card expires. The format is: YYMM
     * Where:
     * YY = Year
     * MM = Month
     * If required by contractual agreement, this field must be provided; and the Merchant must ensure
     * that the Card Expiration Date has not expired to avoid rejection of this transaction.
     * During certification, Merchants, authorized Third Party Processors and Vendors must demonstrate
     * the ability to provide and transmit appropriate and meaningful information in this optional field,
     * as well as all required fields.
     */
    protected String CardExprDt;


    /**
     * Data Length: 8 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description:This field contains the date when the transaction took place; or for mail and telephone orders,
     * the date when the goods were shipped. This entry will appear on the Cardmember's statement.
     * The format is: CCYYMMDD Where:
     * CC = Century
     * YY = Year
     * MM = Month
     * DD= Day
     * For example, February 16, 2011, would be entered as: 20110216
     * Example: <TransDt>20110216</TransDt>
     */
    protected String TransDt;

    /**
     * Data Length: 6 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: No
     * Description: This field contains the time when the transaction took place; or for mail and telephone orders,
     * the time when the goods were shipped.
     * The format is: HHMMSS
     * Where:
     * HH = Hour (24-hour clock)
     * MM = Minute
     * SS = Second
     * For example, 2:30 p.m. would be entered as: 143000
     * <p>
     * Example: <TransTm>143000</TransTm>
     */
    protected String TransTm;


    /**
     * Data Length: 12 bytes, maximum
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description: This field contains the total transaction amount (including tax) in the currency designated by the TransCurrCd field in this Data Capture Request Message.
     * For information on currencies approved for submission, maximum values and decimal point positions, refer to the American Express Global Codes & Information Guide.
     * Note: Zero value transactions are not accepted.
     * <p>
     * Example: <TransAmt>10000</TransAmt>
     */
    protected long TransAmt;

    /**
     * Data Length:3 bytes, fixed
     * Data Element Type: Alphanumeric
     * Required Field: Yes
     * Description: his field contains the code of the currency used to execute the original transaction and corresponds to the currency used for the TransAmt field in this Data Capture Request Message.
     * This field must also correspond to the currency specified for the Service Establishment (SE) Number that appears in the MerId field in this Data Capture Request Message.
     * Merchants, authorized Third Party Processors and software vendors must be certified for all currencies in which they plan to submit settlement data. Submitters that support Merchants in multiple countries must certify prior to using a particular currency code. A unique Merchant ID must be assigned to the submitter for each currency it supports.
     * For more information on submitting multiple currencies and/or to schedule certification, contact your American Express representative.
     * For information on currencies approved for submission, refer to the American Express Global Codes & Information Guide.
     * Note: The American Express currency code format supports both ISO alpha and numeric values. For example, the ISO alpha code for U.S. Dollars is “USD”, and the numeric code is “840”.
     * <p>
     * Example: <TransCurrCd>840</TransCurrCd>
     */
    protected String TransCurrCd;

    /**
     * Data Length:6 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description:This field contains a code that indicates the effect of this transaction on the Cardmember's account.
     * The valid values include:
     * 000000 = Debit (e.g., Purchase)
     * 200000 = Credit (e.g., Refund)
     * There are two (2) Transaction Processing Codes designated to void a debit capture request or to void a credit capture request.
     * A Data Capture Request Message can only be voided before a batch is closed.
     * The valid values include: 999999 = Debit Void
     * 299999 = Credit Void
     * During certification, Merchants, authorized Third Party Processors and
     * Vendors must demonstrate the ability to support all of the values above.
     * The following rules must be applied in order to process a Void Request message.
     * 1  All requested fields of the Data Capture Request Message must be supplied with any Void Request.
     * 2  The Void Request will be an exact replica of the original Data Capture request that is to be voided, except for the TransProcCd.
     * 3  The transaction to be voided will be uniquely identified by the combination of the following fields:
     * a. Merchant SE number (MerId)
     * b. Batch number (BatchID)
     * c. Terminal ID (MerTrmnlId)
     * d. Reference number (RefNumber)
     * Example: <TransProcCd>000000</TransProcCd>
     */
    protected String TransProcCd;

    /**
     * Data Length: 15 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description: This field must contain the Transaction Identifier (TID), a unique tracking number from the Authorization
     * Response Message that corresponds to a debit or authorized credit transaction.
     * For credit transactions, the TransId for the authorized credit transaction must be present.
     * Notes:
     * 1 When a single authorization is obtained for a split shipment submitted transaction,
     * American Express recommends that the Transaction Identifier/Approval Code combination be provided
     * on each of the subsequent financial submission file transactions.
     * 2 When multiple authorizations are obtained for a single submitted transaction,
     * American Express recommends that the first Transaction Identifier/Approval Code combination be provided
     * on the Data Capture Request Message.
     * Example: <TransId>189116240721234</TransId>
     */
    protected String TransId;


    /**
     * Data Length: 2 bytes fixed or 6 bytes fixed
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description:
     * This field should contain the same value that was transmitted from American Express
     * in the original Authorization Response Message.*
     * For credit transactions, the TransAprvCd for the authorized credit transaction must be present.
     * This value must be unique. The format is:
     * NNNNNN = Approval Code for U.S., Canadian and some regional American Express Merchants.
     * Note: All U.S. and Canadian Merchants must comply with the American Express Six-Digit Approval Code policy.
     * And,
     * NN = Approval Code for some regional American Express Merchants.
     * For credit transactions which were not authorized, this field must not be present.
     * Example: <TransAprvCd>123456</TransAprvCd>
     */
    protected String TransAprvCd;


    /**
     * * Required Field: Yes
     */
    protected PointOfServiceData PointOfServiceData;


    /**
     * Data Length:  2 bytes
     * Data Element Type: Numeric
     * Required Field: Yes - American Express SafeKey transactions
     * Yes - Digital Wallet - application initiated (including application initiated Payment Token) transactions
     * No - All other transactions
     * <p>
     * Description:
     * This field contains the results of an American Express SafeKey or Payment Token authentication attempt during authorization.
     * The valid values include:
     * 05 = Authenticated
     * 06 = Attempted
     * 07 = Not authenticated
     * 20 = Payment Token data present only
     * During certification, authorized Third Party Processors and Vendors are required to demonstrate the ability to populate
     * and transmit appropriate and meaningful information in this field. Certification is required to support American Express SafeKey
     * and/or Digital Wallet (including application initiated Payment Token) transactions prior to transmitting any data in this field.
     * Merchants must certify to support American Express SafeKey and/or Digital Wallet (including application initiated Payment Token)
     * transactions prior to transmitting any data in this field.
     * <p>
     * Example: <ElecComrceInd>05</ElecComrceInd>
     */
    protected String ElecComrceInd;

    /**
     * Data Length: 2 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: No
     * <p>
     * Description:
     * This field contains a code that indicates the media used to initiate the Cardmember transaction.
     * The valid values include:
     * <p>
     * 01 = Cardmember signature on file
     * 02 = Phone mail order
     * 03 = Mail order
     * 04 =Internet order
     * 05 = Recurring billing
     * Example: <MediaCd>01</MediaCd>
     */
    protected String MediaCd;

    /**
     * Data Length: 2 bytes, fixed
     * Data Element Type:Numeric
     * Required Field: No
     * Description: This field contains a code that indicates the Submission Method used to initiate the Cardmember transaction.
     * The valid values include:
     * <p>
     * 01 = POS Terminal - American Express
     * 02 = POS Terminal - Non-American Express
     * 03 = Integrated POS System - Non-American Express
     * 06 = PurchaseExpress
     * 07 =Payflow
     * 10 =Dial Payment System
     * 13 =Automated Bill Payment Platform
     * Not all methods are available for use by all submitters. If you are unsure of which Submission Method to use,
     * contact your American Express representative
     * Example: <SubmMthdCd>01</SubmMthdCd>
     */
    protected String SubmMthdCd;

    /**
     * Data Length:15 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field:No
     * Description: This field contains a Merchant-assigned store, location number or location name that uniquely identifies where the transaction took place.
     * During certification, authorized Third Party Processors and Vendors are required to demonstrate the ability to provide and transmit appropriate
     * and meaningful information in this optional field, as well as all required fields.
     * Example: <MerLocId>123456</MerLocId>
     */
    protected String MerLocId;

    /**
     * Data Length: 40 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: Yes
     * <p>
     * Description:This field contains the Merchant's telephone number, email address, or Web (URL) address.
     * URLs and email addresses may be in lower case, as appropriate. This entry may appear on the descriptive
     * bill on the Cardmember's statement, or be used to resolve billing inquiries and disputes.
     * American Express strongly recommends that Payment Facilitators and Bill Payment Providers always provide this
     * field with the URL, email address or telephone number of the contact responsible for dispute/inquiry resolution.
     * During certification, authorized Third Party Processors, Bill Payment Providers and Vendors are required to
     * demonstrate the ability to provide and transmit appropriate and meaningful information in this optional field,
     * as well as all required fields.
     * See list of allowable special characters on page 16.
     * Example: <MerCtcInfoTxt>WWW.EXAMPLE.COM</MerCtcInfoTxt>
     */
    protected String MerCtcInfoTxt;

    /**
     * Data Length: 2 bytes, fixed
     * Data Element Type:  Numeric
     * Required Field: No
     * <p>
     * Description:This field indicates the types of supplementary data routed to American Express outside of
     * the standard transactional GFSG format.  Certain submitters in EMEA and Mexico are required to send this data
     * to American Express will be instructed on how to provide this field.
     * If the MatchKeyTypeCd field is provided, the MatchKeyId field must also contain data.
     * Example:
     * The valid values include:
     * 02 = Enhanced Business Travel Account
     * 99 = Mexico Submissions Identifier
     * Example:
     * <MatchKeyTypeCd>01</MatchKeyTypeCd>
     */
    protected String MatchKeyTypeCd;

    /**
     * Data Length: 21 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: No
     * Description: This field contains a value used to match this Data Capture Request Message with supplementary data routed to American
     * Express outside of the standard transactional XML GFSG format.
     * Certain submitters in EMEA and Mexico are required to send this data to American Express will be instructed on how to provide this field.
     * If the MatchKeyId field is provided, the MatchKeyTypeCd field must also contain data.
     * <p>
     * Example:<MatchKeyId>123456789012345678901</MatchKeyId>
     */
    protected String MatchKeyId;


    /**
     * Data Length: 4 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes - if “NumDefPayments” is provided No - all other transactions
     * <p>
     * Description:Deferred Payment Plans are used when the Issuer or Acquirer have agreed to support installment payment arrangements as an option to Cardmembers.
     * This field contains a code that indicates the type of Deferred Payment Plan applicable to this transaction.
     * The valid values include:
     * 0003 = Acquirer sponsored plan (Plan-N)
     * 0005 = Issuer sponsored plan (DPP)
     * This information may become part of the descriptive bill on the Cardmember’s statement or be used to resolve inquiries and disputes.
     * Note: Support of Plan-N is required for those submitters transacting in the Mexico and Argentina markets.
     * Plan-N transactions are subject to certain restrictions (by country) and must comply with the terms specified
     * in the contractual agreement between American Express and the Service Establishment.
     * For additional information on Plan-N, contact your American Express representative.
     * Authorized Third Party Processors, Merchants, Vendors and Payment Facilitators that support deferred payment
     * plans are required to demonstrate the ability to provide and transmit appropriate and meaningful information in this record.
     * Example :<DefPaymentPlan>0003</DefPaymentPlan>
     */
    protected String DefPaymentPlan;

    /**
     * Data Length: 2 bytes
     * Data Element Type: Numeric
     * Required Field: Yes - if “DefPaymentPlan” is provided No - all other transactions
     * Description: Deferred Payment Plans are used when the Issuer or Acquirer have agreed to support installment payment arrangements as
     * an option to Cardmembers.
     * This field contains the Number of Installments applicable to this Deferred Payment Plan transaction.
     * This information may become part of the descriptive bill on the Cardmember’s statement or be used to resolve inquiries and disputes.
     * The valid values include 2 up to and including 99.
     * Authorized Third Party Processors, Merchants, Vendors and Payment Facilitators that support deferred payment
     * plans are required to demonstrate the ability
     * to provide and transmit appropriate and meaningful information in this record.
     * <p>
     * Example:<NumDefPayments>12</NumDefPayments>
     */
    protected String NumDefPayments;

    /**
     * Data Length: 256 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: Yes - EMV/Chip Card Transactions No - All other transactions
     * <p>
     * Description:EMV is the abbreviation for Europay/MasterCard/VISA, who are joint sponsors of the global standard for
     * electronic financial transactions using “Chip Card” (a.k.a., smart card) technology.
     * Data present in the fields that originate from the Chip Card or terminal must be formatted as defined on the following pages.
     * Card and terminal original data formats are further defined in the AEIPS Chip Card and Terminal Specifications.
     * XML only supports the EMV unpacked data format.
     * During certification, authorized Third Party Processors and vendors that support EMV (Chip Card) are required to
     * demonstrate the ability to provide and transmit appropriate and meaningful information in some optional fields, as well as all required fields.
     * Merchants not certified to accept ICC transactions must not transmit this field to American Express.
     * Data present in this field originates from the Chip Card or terminal, and it must be formatted as defined in the AEIPS Chip Card and Terminal Specifications.
     * Unpacked Format (ASCII)
     * In unpacked format, the entries in all numeric subfields are represented as either decimal characters (0-9) or hexadecimal characters (0-9, A-F, a-f). These are identified in the Field Type column as either “Numeric” or “Alphanumeric“, “hexadecimal”, respectively.
     * Examples:
     * Numeric, right justified, zero filled - The Transaction Amount value “10000” would appear as decimal characters “000000010000”.
     * Alphanumeric, hexadecimal, right justified, zero filled - The Application Transaction Counter (ATC) value “26” (decimal) would appear as hexadecimal characters “001A”.
     * Example:<EMVData>AGNS00019708807D8D7EF</EMVData>
     */
    protected String EMVData;


    /**
     * Field:41
     * Data Length:4 bytes, fixed
     * Data Element Type:Numeric
     * Required Field:Yes
     * Description: This field contains the MCC/Merchant Category Code that corresponds to the Merchant's business type.
     * Payment Facilitators, Bill Payment Providers, Installment Payment Providers and OptBlue Participants
     * For Payment Facilitators, OptBlue Participants and Bill Payment Providers, the MCC must reflect the classification for the specific entity actually rendering the goods or services. Therefore, this value may vary for each transaction, depending on the category applicable to the Payment Facilitators, OptBlue Participant or Bill Pay Provider’s specific sellers or vendors.
     * Installment Payment Providers are required to use Merchant Category Code 5999.
     * Note: This same Merchant Category Code must be populated in the corresponding authorization message.
     * For a list of Merchant Category Codes (MCC), refer to the American Express Global Codes & Information Guide.
     * Example: <MerCtgyCd>1750</MerCtgyCd>
     */
    protected String MerCtgyCd;
    /**
     * Field:42
     * Data Length: 20 bytes, maximum
     * Data Element Type: Alphanumeric
     * Required Field: Yes - Payment Facilitators and Bill Payment Providers only No - All other submitters
     * Description: This field contains the Seller ID, 20-byte, code that uniquely identifies a Payment Facilitators, OptBlue Participants or Bill Pay Provider’s specific seller or vendor.
     * During certification, authorized Third Party Processors, OptBlue Participants, Bill Payment Providers and Vendors are required to demonstrate the ability to provide and transmit appropriate and meaningful information in this field.
     * This should be the same unique value used to identify a particular Seller when requested in any other American Express message.
     * Example: <SellId>1234QR7890</SellId>
     */
    protected String SellId;

    /**
     * Field 43
     */
    protected CardTransDetail CardTransDetail;


    /**
     * Field 48
     * Data Element Type: Data Length:2 bytes, fixed
     * Required Field:  Numeric
     * Required Field: Yes - if industry-specific detail is to be included with this Data Capture Request Message
     * No - all other transactions
     * Description: This field contains a code that indicates the type of industry detail that appears in this Data Capture Request Message.
     * Submitters wanting to transmit industry information must certify for the appropriate industry-related fields.
     * See Sections 8.1 through 8.10 for specific industry details.
     * Note: The transaction will be rejected if the TransAddCd tag is not provided and industry-specific addendum information is provided.
     * Data Capture Request Messages can only contain information related to one industry.
     * The valid values include:
     * <p>
     * 01 = Airline
     * 04 = Insurance
     * 05 = Auto Rental
     * 06 = Rail
     * 11 = Lodging
     * 13 = Communication Services
     * 14 = Travel/Cruise
     * 20 = Retail
     * 22 = Entertainment/Ticketing
     * Example: <TransAddCd>01</TransAddCd>
     */
    protected String TransAddCd;


    @Tolerate
    public DataCaptureRequest() {
    }

    @Override
    public String toXml() {
        String xml = XmlUtility.getInstance().getString(this);
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }
}
