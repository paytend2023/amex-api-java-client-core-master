package com.paytend.models.trans.req;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 * ISO 8583 Bit # / Data Element: Data Length:
 * Data Element Type:
 * Constant:
 * Data Element Requirement:
 * Certification Requirement:
 * Description:
 * AdditionalDataNational CardNotPresentData
 * Bit 47 / Additional Data - National N/A
 * N/A N/A
 * Optional — Merchants in mail-, telephone- and internet-order industries that pass Card Not Present - Internet,
 * Telephone Data (ITD) data with transactions.
 * USA, Canada, EMEA & LA/C
 *
 * Mandatory — Third Party Processors and/or Vendor software must be certified to pass Card Not Present -
 * Internet Telephone Data (ITD) data in this data element.
 * After certification, all Merchant-provided data must be forwarded.
 * This nested container element holds the sub-elements listed in the following example.
 * CardNotPresent sub-elements may contain source data, including the Cardmember's Web and email addresses,
 * host computer name, HTTP browser, product SKU (Stock Keeping Unit) inventory reference number, shipping method and ship-to country.
 * However, not all sub-elements will be used for a typical transaction. For example, an Internet-order would probably not use CustIdPhoneNbr or CallTypId;
 * and a phone-order would probably omit CustHostServerNm and CustIpAddr.
 * See example on the next page.
 * <CardNotPresentData>
 * <CustEmailAddr></CustEmailAddr>
 * <CustHostServerNm></CustHostServerNm>
 * <CustBrowserTypDescTxt>
 * </CustBrowserTypDescTxt>
 * <ShipToCtryCd></ShipToCtryCd>
 * <ShipMthdCd></ShipMthdCd>
 * <MerSKUNbr></MerSKUNbr>
 * <CustIPAddr></CustIPAddr>
 * <CustIdPhoneNbr></CustIdPhoneNbr>
 * <CallTypId></CallTypId>
 * </CardNotPresentData>
 */
@Builder
@Getter
@Setter
public class CardNotPresentData {
    /**
     * ISO 8583 Bit # / Data Element: Bit 47 / Additional Data - National
     *
     * Data Length: 60 bytes, maximum
     * Data Element Type: Alphanumeric & special characters
     *
     * Constant: None
     * Description:
     * This data element contains the Customer's Email Address.
     * Note: If CustEmailAddr is used to submit Enhanced Authorization tool data, VerificationInformation sub-element
     * ReqTypId must be “AE” to receive a response code for Email Address Verification. For more information, see page 35.
     *
     * Example:
     * <CustEmailAddr>
     *  CFFROST@EMAILADDRESS.COM
     * </CustEmailAddr>
     */
    String custEmailAddr;


    /**
     * Data Length:  60 bytes, maximum
     *
     *Data Element Type: Alphanumeric & special characters
     * Description: This data element contains the Customer Host Server Name,
     * which is the name of the server to which the customer is connected.
     *
     *Example:
     * <CustHostServerNm>
     *  PHX.QW.AOL.COM
     * </CustHostServerNm>
     */

    String custHostServerNm;
    /**
     * Data Length: 60 bytes, maximum
     */
    String custBrowserTypDescTxt;

    /**
     * Description:
     *This data element contains the three-digit, numeric, Ship-to Country Code.
     * For example, the country code for a ship-to address in the USA is “840”.
     * For more information on numeric country codes, refer to Country and Currency
     * Codes for Authorizations in the American Express Global Codes & Information Guide.
     */
    String shipToCtryCd;
    /**
     *
     * Data Length: 2 bytes, fixed
     *Description:
     * This data element contains the two-character, Shipping Method Code (a.k.a., shipment-type code). Valid values include:
     * 01 =Same Day
     * 02 = Overnight / Next Day
     * 03 = Priority, 2-3 days
     * 04 = Ground, 4 or more days
     * 05 = Electronic Delivery
     * 06 = Ship-to Store*
     * 07-ZZ = Reserved for future use
     *
     *
     */
    String shipMthdCd;
    /**
     *Data Length: 15 bytes, maximum
     * Description: This data element contains the Merchant SKU Number,
     * which is the product Stock Keeping Unit inventory reference number of the product associated with this transaction.
     * For multiple items, enter the SKU for the single, most expensive item.
     */
    String merSKUNbr;

    /**
     *
     */
    String custIPAddr;

    /**
     *
     */
    String custIdPhoneNbr;
    /**
     *Description:
     * This data element contains the Call Type ID (a.k.a., Customer II Digits),
     * which are the phone company-provided, “ANI ii” (Automatic Number Identification, Information Identifier) coding
     * digits associated with the Customer Identification Phone Number (a.k.a., Customer ANI) in sub-element CustIdPhoneNbr.
     * Example:
     * Typical values include:
     * 24-25 = Toll free
     * 27 = Payphone
     * 61-63 = Cellular
     */
    String callTypId;

    @Tolerate
    private CardNotPresentData() {
    }

}
