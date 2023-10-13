package com.paytend.models.trans.req;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

/**
 * @author Sunny
 * @create 2023/8/23 15:21
 */
@Setter
@Getter
@SuperBuilder
public class BaseSubmitRequest {
    /**
     * Field 3
     * Data Length: 8 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description: This field contains the version number that corresponds to the messages created per this specification.
     * <p>
     * Example:<Version>12010000</Version>
     */
    protected String Version;


    /**
     * Field 4
     * Data Length: 15 bytes, maximum
     * Data Element Type:  Numeric
     * Required Field: Yes
     * Description: This field contains the American Express-assigned Service Establishment (SE) Number that is used to
     * identify and pay Merchants.Each Merchant ID/SE Number can submit transactions in only one currency.
     * A Merchant that intends to submit multiple currencies must request one SE Number for each submission currency.
     * All Service Establishment Numbers should be validated and must pass the Mod 9 check. For details,
     * refer to Cardmember Number Identification in the American Express Global Codes & Information Guide.
     * <p>
     * Example:<MerId>5021011432</MerId>
     */
    protected String MerId;

    /**
     * Field 5
     * Data Length: 8 bytes, maximum
     * Data Element Type: Alphanumeric, upper case
     * Required Field: Yes
     * Description: This field contains a unique code that identifies a specific terminal at a Merchant location.
     * <p>
     * Example:<MerTrmnlId>80000011</MerTrmnlId>
     */
    protected String MerTrmnlId;

    /**
     * Data Length: 6 bytes, maximum
     * Data Element Type: Numeric
     * Required Field: Yes
     * <p>
     * Description: This field contains a unique Merchant- or submitter-assigned number
     * that identifies this batch.
     * <p>
     * Example:<BatchID>481035</BatchID>
     */
    protected String BatchID;

    @Tolerate
    public BaseSubmitRequest() {
    }
}
