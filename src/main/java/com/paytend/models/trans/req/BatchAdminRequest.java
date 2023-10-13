package com.paytend.models.trans.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.paytend.models.trans.XmlRequest;
import io.aexp.api.client.core.utils.XmlUtility;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author Sunny
 * @create 2023/8/19 11:12
 */

@Getter
@Setter
@Builder
@JacksonXmlRootElement(localName = "BatchAdminRequest")
public class BatchAdminRequest implements XmlRequest {

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


    /**
     * Data Length: 2 bytes
     * Data Element Type: Numeric
     * Required Field: Yes
     * Description: This field indicates the operation this administration request message is issuing.
     * <p>
     * The valid values include:
     * 01 = Open
     * 02 = Close
     * 03 = Purge
     * 04 = Status
     * Note: American Express will close and process any batch left open for
     * over twenty-four (24) hours.
     * <p>
     * Example: <BatchOperation>01</BatchOperation>
     */
    protected String BatchOperation;

    /**
     * Data Length: 2 bytes
     * Data Element Type: Numeric
     * Required Field: No
     * <p>
     * Description: This field indicates whether the submitter has requested Batch Summary information be included in
     * the Batch Administration Response Message. This can be sent for any Batch Administration message but totals
     * are set to zero (0) for Batch Open and Batch Purge requests. If this is not provided, then a summary is not provided
     * in the Batch Administration Response message.
     * The valid values include:
     * 00 = No summary
     * 01 = Send summary
     */
    protected String ReturnBatchSummary;


    protected CardAcceptorDetail CardAcceptorDetail;

    /**
     * Data Length: 10 bytes 10 bytes, maximum
     * Data Element Type: Alphanumeric
     * Required Field:
     * Yes - If Batch Open and Batch Close messages
     * No - If Batch Purge and Batch Status messages
     * <p>
     * Description: A unique identifier code assigned by American Express to submitters so they can directly access
     * American Express to deliver real-time settlement data
     * <p>
     * Example: <SubmitterCode>1234567890</SubmitterCode>
     */
    protected String SubmitterCode;

    @Tolerate
    public BatchAdminRequest() {
    }

    @Override
    public String toXml() {
        String xml = XmlUtility.getInstance().getString(this);
        return "AuthorizationRequestParam=<?xml version=\"1.0\" encoding=\"utf-8\"?>" + XmlUtility.getInstance().formatXml(xml);
    }
}
