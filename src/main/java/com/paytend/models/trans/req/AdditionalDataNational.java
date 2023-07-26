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
 * 112 April 2023
 * AdditionalDataNational
 * Bit 47 / Additional Data - National N/A
 * N/A
 * N/A
 * See CardNotPresentData, InternetAirlineCustomer and CardPresentData sub-element details on pages 115, 126, and 146 respectively.
 * See CardNotPresentData, InternetAirlineCustomer and CardPresentData sub-element details on pages 115, 126, and 146 respectively.
 * This container element holds the nested container elements and sub-elements listed in the example on the next page.
 * CardNotPresentData and InternetAirlineCustomer sub-elements are used only for transactions where the Cardholder is not present. Inappropriate use of these data elements (e.g., transactions where the Cardholder is present) may cause message rejection. Specifically, CardTrack1Data and/or CardTrack2Data cannot be present in Authorization Request (1100) messages that utilize these sub-elements.
 * The three nested container elements represent the following:
 * • The first format is for Merchants that submit Card Not Present - Internet, Telephone Data (ITD) Data specific to mail-, telephone- and Internet-order industries.
 * • The second format is specific to airline industry Merchants that submit Card Not Present-Internet Airline Customer (IAC) Data.
 * • The third format is for Merchants that submit Card Present - Goods Sold data passed in a Card Present transaction.
 * Description (continued):
 * Example:
 *
 * Note that these three container elements and their respective sub-elements are mutually exclusive, which means that only one of these formats may be used for a given transaction.
 * Merchants that could utilize CardNotPresent and Internet AirlineCustomersub-elementsshould contact their American Express representative to determine which format is appropriate for their business.
 *
 * <AdditionalDataNational>
 *  <CardNotPresentData>
 *    <CustEmailAddr></CustEmailAddr>
 *    <CustHostServerNm></CustHostServerNm>
 *    <CustBrowserTypDescTxt>
 *    </CustBrowserTypDescTxt>
 *    <ShipToCtryCd></ShipToCtryCd>
 *    <ShipMthdCd></ShipMthdCd>
 *    <MerSKUNbr></MerSKUNbr>
 *    <CustIPAddr></CustIPAddr>
 *    <CustIdPhoneNbr></CustIdPhoneNbr>
 *    <CallTypId></CallTypId>
 *  </CardNotPresentData>
 *  <InternetAirlineCustomer>
 *    <DprtDt></DprtDt>
 *    <PassengerName>
 *      <PassLastNm></PassLastNm>
 *      <PassFirstNm></PassFirstNm>
 *      <PassMidInitTxt></PassMidInitTxt>
 *      <PassTitleTxt></PassTitleTxt>
 *    </PassengerName>
 *    <AirlineSegmentInformation>
 *      <OrigAirportCd></OrigAirportCd>
 *      <DestAirportCd></DestAirportCd>
 *      <AirportOnTktCnt></AirportOnTktCnt>
 *      <RteAirportCd></RteAirportCd>
 *    </AirlineSegmentInformation>
 *    <IATACarrierInformation>
 *      <IATACarrierCnt></IATACarrierCnt>
 *      <IATACarrierCd></IATACarrierCd>
 *    </IATACarrierInformation>
 *    <FareBasisCd></FareBasisCd>
 *    <PassInPartyCnt></PassInPartyCnt>
 *    <CustIPAddr></CustIPAddr>
 *    <CustEmailAddr></CustEmailAddr>
 *  </InternetAirlineCustomer>
 *  <CardPresentData>
 *    <VersionNo></VersionNo>
 *    <GoodSoldProdCd></GoodSoldProdCd>
 *  </CardPresentData>
 * </AdditionalDataNational>
 */

@Builder
@Getter
public class AdditionalDataNational {


    CardNotPresentData CardNotPresentData;

    InternetAirlineCustome InternetAirlineCustome;

    CardPresentData CardPresentData;

    @Tolerate
    public AdditionalDataNational(){}

    @Builder
    public static class CardPresentData {
        String VersionNo;
        String GoodSoldProdCd;
    }
}
