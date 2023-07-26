package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Builder
@Getter
public class CardAcceptorIdentification {

    String MerId;
    String IATAAirlineCd;
    String IATATravAgtId;

    @Tolerate
    public CardAcceptorIdentification() {
    }
}
