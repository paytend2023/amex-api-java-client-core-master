package com.paytend.models.trans.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gudongyang
 */

@Getter
@AllArgsConstructor
@Setter
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