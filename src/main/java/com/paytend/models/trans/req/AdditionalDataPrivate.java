package com.paytend.models.trans.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author gudongyang
 */

@Getter
@AllArgsConstructor
public class AdditionalDataPrivate {

    AMEXExtendedPaymentIndicator AMEXExtendedPaymentIndicator;

    @Getter
    @AllArgsConstructor
    public static class AMEXExtendedPaymentIndicator {
        String PlanTypCd;
        String InstalPayNbr;
    }
}

/*
<AdditionalDataPrivate>
    <AMEXExtendedPaymentIndicator>
        <PlanTypCd></PlanTypCd>
        <InstalPayNbr></InstalPayNbr>
    </AMEXExtendedPaymentIndicator>
</AdditionalDataPrivate>
 */