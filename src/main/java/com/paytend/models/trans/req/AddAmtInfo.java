package com.paytend.models.trans.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sunny
 * @create 2023/8/22 15:33
 */
@Setter
@Getter
public class AddAmtInfo {


    /**
     * Data Length: 3 bytes, fixed
     * Data Element Type: Numeric
     * Required Field: Yes - if CardTransDetail is provided
     * Description: This field contains the type code that corresponds to the amount.
     * The AddAmtTypeCd field contains the type code that corresponds to
     * the amount in the AddAmt field that immediately follows this entry.
     * During certification, authorized Third Party Processors and Vendors are required to demonstrate the ability to provide and transmit appropriate and meaningful information in these optional fields, as well as all required fields.
     * If the AddAmt field is provided, the AddAmtTypeCd field must also contain data.
     * See Additional Amount Type Codes on next page.
     * <p>
     * Example: <AddAmtTypeCd>016</AddAmtTypeCd>
     */
    protected String AddAmtTypeCd;

    /**
     * Field 46
     * Data Length: 12 bytes, maximum
     * Data Element Type: Numeric
     * Required Field: Yes - if CardTransDetail is provided
     * Description: This field contains the amount that corresponds to the AddAmtTypeCd entries in this Additional Amount field set. This entry must be in the currency designated by the TransCurrCd field in this Data Capture Request Message.
     * During certification, authorized Third Party Processors and Vendors are required to demonstrate the ability to provide and transmit appropriate and meaningful information in these optional fields, as well as all required fields.
     * The sign in the SignInd field that immediately follows this entry indicates whether this amount is a debit (+) or credit (-) as applied to the Cardmember’s account.
     * For information on currencies approved for submission, maximum values and decimal point positions, refer to the American Express Global Codes & Information Guide.
     * If the AddAmtTypeCd field is provided, the AddAmt field must also contain data.
     * See Additional Amount Type Codes starting on page 106.
     * Example: <AddAmt>10000</AddAmt>
     */
    protected String AddAmt;

    /**
     * Data Length:  1 byte
     * Data Element Type: Alphanumeric
     * Required Field: Yes - if CardTransDetail is provided
     * Description:  This field contains a sign that indicates whether the corresponding AddAmtTypeCd field that immediately precedes this entry is a debit (+) or credit (-) as applied to the Cardmember’s account.
     * During certification, authorized Third Party Processors and Vendors are required to demonstrate the ability to provide and transmit appropriate and meaningful information in these optional fields, as well as all required fields.
     * The valid values include “+” or “-”.
     * <p>
     * Example:<SignInd>+</SignInd>
     */
    protected String SignInd;

}
