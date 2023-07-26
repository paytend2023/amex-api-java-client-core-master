package com.paytend.models.trans.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Builder
@Getter
public class InternetAirlineCustome {
    String DprtDt;

    PassengerName PassengerName;

    AirlineSegmentInformation AirlineSegmentInformation;

    IATACarrierInformation IATACarrierInformation;

    String FareBasisCd1;
    String FareBasisCd2;
    String FareBasisCd3;
    String FareBasisCd4;
    String PassInPartyCnt;
    String CustIPAddr;
    String CustEmailAddr;

    @Tolerate
    public InternetAirlineCustome() {
    }

    @AllArgsConstructor
    @Getter
    class PassengerName {
        String PassLastNm;
        String PassFirstNm;
        String PassMidInitTxt;
        String PassTitleTxt;
    }

    @AllArgsConstructor
    @Getter
    class AirlineSegmentInformation {

        String OrigAirportCd;
        String DestAirportCd;
        String AirportOnTktCnt;
        String RteAirportCd;
    }

    @AllArgsConstructor
    @Getter
    class IATACarrierInformation {
        String IATACarrierCnt;
        String IATACarrierCd;
    }


}
