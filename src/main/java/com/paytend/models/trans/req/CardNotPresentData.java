package com.paytend.models.trans.req;


import lombok.Builder;
import lombok.Getter;
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
 * Optional — Merchants in mail-, telephone- and internet-order industries that pass Card Not Present - Internet, Telephone Data (ITD) data with transactions.
 * USA, Canada, EMEA & LA/C
 * Mandatory — Third Party Processors and/or Vendor software must be certified to pass Card Not Present - Internet Telephone Data (ITD) data in this data element. After certification, all Merchant-provided data must be forwarded.
 * This nested container element holds the sub-elements listed in the following example.
 * CardNotPresent sub-elements may contain source data, including the Cardmember's Web and email addresses, host computer name, HTTP browser, product SKU (Stock Keeping Unit) inventory reference number, shipping method and ship-to country. However, not all sub-elements will be used for a typical transaction. For example, an Internet-order would probably not use CustIdPhoneNbr or CallTypId; and a phone-order would probably omit CustHostServerNm and CustIpAddr.
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
public class CardNotPresentData {

    String CustEmailAddr;
    String CustHostServerNm;
    String CustBrowserTypDescTxt;
    String ShipToCtryCd;
    String ShipMthdCd;
    String MerSKUNbr;
    String CustIPAddr;
    String CustIdPhoneNbr;
    String CallTypId;

    @Tolerate
    private CardNotPresentData() {
    }

}
