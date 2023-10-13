package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <OriginalDataElements>
 * <MsgTypId></MsgTypId>
 * <MerSysTraceAudNbr> </MerSysTraceAudNbr>
 * <TransTs></TransTs>
 * <AcqInstIdCd></AcqInstIdCd>
 * </OriginalDataElements>
 *
 * @author Sunny
 * @create 2023/8/25 15:18
 */
@Setter
@Getter
@Builder
public class OriginalDataElements {
    String MsgTypId;
    String MerSysTraceAudNbr;
    String TransTs;
    String AcqInstIdCd;
}
