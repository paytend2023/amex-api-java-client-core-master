package com.paytend.models.seller.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Builder
@Getter
@ToString

public class SeWarning {

    /**
     * warn_cd
     * string, max: 10 REQUIRED
     * This alphanumeric field contains a warning code.
     */
    String warnCd;

    /**
     * warn_msg
     * string, max: 100 REQUIRED
     * This alphanumeric field contains a warning message.
     */
    String warnMsg;

    @Tolerate
    public SeWarning() {
    }
}
