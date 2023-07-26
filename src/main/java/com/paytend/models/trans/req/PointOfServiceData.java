package com.paytend.models.trans.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Data
@Builder
@Getter
public class PointOfServiceData {

    String CardDataInpCpblCd;
    @JacksonXmlProperty(localName = "CMAuthnCpblCd")

    String CMAuthnCpblCd;

    String CardCptrCpblCd;

    String OprEnvirCd;

    @JacksonXmlProperty(localName = "CMPresentCd")
    String CMPresentCd;

    String CardPresentCd;

    String CardDataInpModeCd;

    @JacksonXmlProperty(localName = "CMAuthnMthdCd")
    String CMAuthnMthdCd;

    @JacksonXmlProperty(localName = "CMAuthnEnttyCd")
    String CMAuthnEnttyCd;

    String CardDataOpCpblCd;

    String TrmnlOpCpblCd;

    @JacksonXmlProperty(localName = "PINCptrCpblCd")
    String PINCptrCpblCd;

    @Tolerate
    public PointOfServiceData() {
    }
}
