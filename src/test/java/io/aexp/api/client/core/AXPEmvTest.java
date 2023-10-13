package io.aexp.api.client.core;

import cn.hutool.core.date.DateUtil;
import com.paytend.models.trans.req.*;
import com.paytend.models.trans.rsp.AuthorizationRsp;
import io.aexp.api.client.core.utils.XmlUtility;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class AXPEmvTest {
    /**
     * TEST CASE NAME
     * AXP EMV 000 - Pre-requisite Validations
     * <p>
     * TEST CASE DESCRIPTION
     * to ensure the terminals have valid unrevoked supporting documentation.
     * <p>
     * TRANSACTION TYPE: pre-requisites
     * Receipt (Y/N):N
     * <p>
     * INSTRUCTION
     * <p>validate that l1 is current and upload a copy under ‘documents’ tab in the ‘dashboard’ section of your ats project</p>
     * <p>validate that l2 is current and upload a copy under ‘documents’ tab in the ‘dashboard’ section of your ats project</p>
     * <p>if applicable, validate pci is current and upload a copy under ‘documents’ tab in the ‘dashboard’ section of your ats project</p>
     * <p>if applicable, upload copy of letter of attestation for terminal family under ‘documents’ tab in the ‘dashboard’ section of your ats project</p>
     * <p>
     * USER VALIDATIONS
     * TERMINAL HAS A VALID EMVCO L1 THAT HAS NOT EXPIRED (MORE THAN 1 YEAR) OR BEEN REVOKED AND HAS BEEN UPLOADED UNDER
     * THE ‘DOCUMENTS’ TAB IN THE ‘DASHBOARD’ SECTION OF YOUR ATS PROJECT
     * TERMINAL HAS A VALID EMVCO L2 THAT HAS NOT EXPIRED (MORE THAN 1 YEAR) OR BEEN REVOKED AND HAS BEEN UPLOADED UNDER
     * THE ‘DOCUMENTS’ TAB IN THE ‘DASHBOARD’ SECTION OF YOUR ATS PROJECT
     * TERMINAL HAS A VALID PCI THAT IS NOT EXPIRED AND HAS BEEN UPLOADED UNDER THE ‘DOCUMENTS’ TAB IN THE ‘DASHBOARD’
     * SECTION OF YOUR ATS PROJECT OR INCLUDE IN YOUR RESULTS FOR DEVICES THAT SUPPORT PIN
     * EMVCO L1, L2 AND PCI ARE ALL FOR THE DEVICE TESTED OR A LETTER OF ATTESTATION FOR TERMINAL
     * FAMILY HAS BEEN UPLOADED UNDER THE ‘DOCUMENTS’ TAB IN THE ‘DASHBOARD’ SECTION OF YOUR ATS PROJECT
     */
    @Test
    public void testAXPEMV000() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 001 - Offline Enciphered PIN DDA
     * <p>
     * TEST CASE DESCRIPTION
     * To ensure the terminal supports Dynamic Data Authentication (DDA) and can perform the offline enciphered PIN validation.
     * Terminal reads the cards magnetic stripe, prompts for chip insertion and performs and approves an online transaction.
     * <p>
     * <p>
     * TRANSATION TYPE :Sale
     * RECEIPT: Y
     * <p>
     * INSTRUCTION
     * Initiate a sale transaction
     * Transaction amount =30.00 (£/$/€) or 300.00 (Peso)
     * Swipe the AEIPS 20 Card
     * Insert card when prompted
     * When prompted enter PIN 1234
     * <p>
     * USER VALIDATIONS
     * Proceed with transaction
     * On approval, receipt is printed
     * Terminal reads the magnetic stripe and prompts for chip insertion
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Terminal prompts for PIN
     * Receipt shows PIN verified
     * No signature panel present
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘0’ indicates “DDA successful or not performed”
     * In 2nd GEN AC In 2nd GEN AC TVR - Byte 5 bit 7 = ‘0’ indicates “Issuer Authentication successful”
     * In 1st GEN AC In 1st GEN AC TVR - Byte 1 bit 6 = ‘0’ indicates  “ICC data not missing”
     * In 1st GEN AC TVR - Byte 3 bit 8 = 0 indicates  'Cardholder Verification was successful'
     * In 1st GEN AC TVR - Byte 4 bit 8 = 1 indicates  'Transaction exceeds floor limit'
     * VERIFY PIN command requested
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 30.00 or 300.00
     * Transaction amount is 30.00 or 300.00
     * <p>
     * HOST VALIDATION
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV001() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 002 - Signature
     * <p>
     * TEST CASE DESCRIPTION
     * To ensure terminal supports signature CVM.
     * Terminal reads the cards magnetic stripe, prompts for chip insertion and performs and approves an online transaction.
     * <p>
     * TRACTION TYPE :Sale
     * RECEIPT :Y
     * <p>
     * INSTRUCTIONS
     * Initiate a sale transaction
     * Transaction amount = 31.00 (£/$/€) or 310.00 (Peso)
     * Swipe the AEIPS 21/Ver 2.0
     * Insert card AEIPS 21 when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * Terminal reads the magnetic stripe and prompts for chip insertion
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Signature line is printed on receipt or captured electronically
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 7 = ‘0’ indicates “SDA successful or not performed”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 31.00 or 310.00
     * Transaction amount is 31.00 or 310.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV002() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 004 - Script update
     * <p>
     * TEST CASE DESCRIPTIONS
     * To ensure the terminal can receive and pass an issuer script to card for processing.
     * <p>
     * Transaction type:Sale
     * RECEIPT :N
     * <p>
     * INSTRUCTIONS
     * Initiate a sale transaction
     * Transaction amount = 33.00 (£/$/€) or 330.00 (Peso)
     * Insert AEIPS 23 Card  when prompted
     * If prompted for application selection, select 'AMERICAN EXPRESS'
     * If prompted, enter PIN 1234
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Transaction is online approved
     * Device displays the American Express logo while in idle mode
     * <p>
     * CARD LOG VALIDATIONS
     * LCOL Script accepted by card; status words in response '90 00'
     * PUT Data command requested
     * In 1st GEN AC TVR - Byte 1 bit 7 = ‘0’ indicates “SDA successful or not performed”
     * In 1st GEN AC TVR - Byte 2 bit 5 = 0 indicates 'Requested service allowed for card product'
     * In 1st GEN AC TVR - Byte 2 bit 8 = 0 indicates 'ICC and terminal do not have different application versions'
     * In 1st GEN AC TVR - Byte 4 bit 8 = ‘1’ indicates “Transaction exceeds floor limit”
     * In 2nd GEN AC TVR - Byte 5 bit 7 = ‘0’ indicates “Issuer Authentication successful”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 33.00 or 330.00
     * Transaction amount is 33.00 or 330.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * 1 '72'  Issuer (LCOL) script present in response message
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV004() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 005 - Multiple Script Update
     * <p>
     * TEST CASE DESCRIPTIONS
     * To ensure the terminal can receive and pass an issuer script with multiple commands to the card for processing.
     * TRANSACTION TYPE :Sale
     * Receipt:N
     * <p>
     * INSTRUCTIONS
     * Initiate a sale transaction
     * Transaction amount = 34.00 (£/$/€) or 340.00 (Peso)
     * Insert AEIPS 24 Card when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS
     * In ist GEN AC TVR - Byte 1 bit 4 = 0 indicates  'DDA successful or not performed'
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * 3rd LCOL issuer script command accepted by card; status words in response ’90 00’
     * 3rd LCOL issuer script command accepted by card; status words in response ’90 00’
     * 3rd LCOL issuer script command accepted by card; status words in response ’90 00’
     * In 2nd GEN AC TVR - Byte 5 bit 7 = ‘0’ indicates “Issuer Authentication successful”
     * PUT Data command requested 1st of 3 times
     * PUT Data command requested 2nd of 3 times
     * PUT Data command requested 3rd of 3 times
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 34.00 or 340.00
     * Transaction amount is 34.00 or 340.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * 3 '72' Put data issuer scripts (LCOL) returned in the response message
     * 3 '72' Put data issuer scripts (LCOL) returned in the response message
     * 3 '72' Put data issuer scripts (LCOL) returned in the response message
     * 3 '72' Put data issuer scripts (LCOL) returned in the response message
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV005() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 006 - Invalid Offline PIN Entry
     * <p>
     * TEST CASE DESCRIPTIONS
     * To ensure the terminal can read PIN Try Counter and display correct message when one try left .
     * <p>
     * Transaction type :Sale
     * receipt :N
     * <p>
     * instructions
     * Initiate a sale transaction
     * Transaction amount = 35.00 (£/$/€) or 350.00 (Peso)
     * Insert AEIPS 20 Card when prompted
     * When prompted enter PIN 4321
     * When prompted enter PIN 4321
     * When prompted enter PIN 1234
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS
     * Terminal displays proper PIN Error message 2 times
     * Terminal displays proper message notifying the last PIN attempt
     * Transaction is online approved
     * The Terminal requests GET DATA for PIN Try Counter
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘0’ indicates “DDA successful or not performed”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * 1st VERIFY PIN command requested
     * 2nd VERIFY PIN command requested
     * 3rd VERIFY PIN command requested
     * Transaction amount is 35.00 or 350.00
     * Transaction amount is 35.00 or 350.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV006() {

    }

    /**
     * test case name:
     * AXP EMV 007 - Magstripe Fallback Approval
     * <p>
     * descriptions:
     * To ensure the terminal fallback to a magstripe transaction  when a chip cannot be read.
     * <p>
     * Transaction type:Sale
     * receipt:Y
     * <p>
     * INSTRUCTIONS:
     * Initiate a sale transaction
     * Transaction amount =  36.00 (£/$/€) or 360.00 (Peso)
     * Insert AEIPS 28 Card when prompted
     * Re-insert card twice
     * Initiate fallback by swiping card
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS:
     * Terminal cannot read the chip data and prompts for magnetic stripe
     * Signature line is printed on receipt or captured electronically
     * Transaction amount displayed to customer
     * Transaction is online approved
     * <p>
     * host validations:
     * ICC Data NOT present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (ICC Capable)
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '9'(Technical Fallback)
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates Magstripe Used
     */
    @Test
    public void testAXPEMV007() {

    }

    /**
     * TEST CASE NAME:
     * AXP EMV 011 - Invalid Public Key
     * DESCRIPTIONS:
     * To ensure the terminal will request online authorization when Offline Data Authentication fails.
     * <p>
     * TRANSACTION TYPE:
     * Sale
     * RECEIPT :
     * N
     * INSTRUCTIONS:
     * Initiate a sale transaction
     * Transaction amount = 40.00 (£/$/€) or 400.00 (Peso)
     * Insert AEIPS 26 Card when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Transaction is online approved
     * Internal Authenticate command NOT requested
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘1’ indicates “DDA Failed”
     * In 1st GEN AC TVR - Byte 1 bit 6 = ‘0’ indicates  “ICC data not missing”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 40.00 or 400.00
     * Transaction amount is 40.00 or 400.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Approved (Field 39 = '000' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV011() {

    }

    /**
     * TEST CASE NAME:
     * AXP EMV 012 - Online Decline
     * DESCRIPTIONS
     * To ensure the terminal will request online authorization and correctly process a denial from issuer.
     * TRANSCATION TYPE: Sale
     * RECEIPT:N
     * <p>
     * INSTRUCTIONS
     * Initiate a sale transaction
     * Transaction amount = 41.00 (£/$/€) or 410.00 (Peso)
     * Insert AEIPS 24 Card when prompted
     * Proceed with transaction
     * Transaction is declined
     * <p>
     * USER VALIDATIONS
     * Transaction is online declined
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘0’ indicates “DDA successful or not performed”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 00 indicates 'AAC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 35' indicates 'Do not honour'
     * Transaction amount is 41.00 or 410.00
     * Transaction amount is 41.00 or 410.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Declined (Field 39 = '100' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Action Code = Declined
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV012() {

    }

    /**
     * TEST CASE NAME:
     * AXP EMV 015 - Unable to Go Online
     * <p>
     * TEST CASE DESCRIPTIONS
     * To ensure the terminal is unable to go online for approval, and the transaction is declined offline.
     * TRANSACTION TYPE Sale
     * RECEIPT N
     * INSTRUCTIONS
     * Disconnect Terminal from Online Capabilities.
     * Initiate a sale transaction
     * Transaction amount = 440.00 (£/$/€) or 4400.00 (Peso)
     * Insert AEIPS 21 Card when prompted
     * Proceed with transaction
     * Transaction is declined
     * <p>
     * USER VALIDATIONS
     * Transaction is offline declined
     * <p>
     * CARD LOG VALIDATIONS
     * External Authenticate command NOT requested
     * In 1st GEN AC TVR - Byte 1 bit 7 = ‘0’ indicates “SDA successful”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * Terminal sets the ARC tag 8A = '5A 33' in the 2nd Generate AC command
     * In 2nd GEN AC Response, P1 = ‘00’ indicates “AAC”
     * Transaction amount is 440.00 or 4400.00
     * Transaction amount is 440.00 or 4400.00
     */
    @Test
    public void testAXPEMV015() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 016 - ODA Failure
     * DESCRIPTIONS
     * To ensure the terminal properly process a decline when Offline Data Authentication fails.
     * <p>
     * TRANSACTION TYPE:Sale
     * RECEIPT :	N
     * DESCRIPTIONS
     * Initiate a sale transaction
     * Transaction amount = 45.00 (£/$/€) or 450.00 (Peso)
     * Insert AEIPS 27 Card when prompted
     * Proceed with transaction
     * Transaction is declined
     * <p>
     * USER VALIDATIONS
     * Terminal displays Application Label 'AMERICAN EXPRESS' - OR - No Application Label displayed on screen.
     * Transaction amount displayed to customer
     * Transaction online declined
     * Internal Authenticate command NOT requested
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 8 = ‘0’ indicates “Offline data authentication was performed”
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘1’ indicates “DDA Failed”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 00 indicates 'AAC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 35' indicates 'Do not honour'
     * Transaction amount is 45.00 or 450.00
     * Transaction amount is 45.00 or 450.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * Action Code = Declined (Field 39 = '100' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Declined
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV016() {

    }

    /**
     * TEST CASE NAME:
     * AXP EMV 017.2 - Magstripe Fallback Empty Candidate List
     * DESCRIPTIONS
     * To ensure the terminal is able to process a Magstripe Fallback when presented with a card that results in an Empty Candidate List.
     * TRANSACTION TYPE :Sale
     * RECEIPT :N
     * INSTRUCTIONS:
     * Initiate a sale transaction
     * Transaction amount = 77.00 (£/$/€) or 770.00 (Peso)
     * Insert AEIPS 34 Card when prompted
     * Initiate fallback by swiping the card
     * Proceed with transaction
     * Transaction is declined
     * <p>
     * USER VALIDATIONS
     * Terminal prompts for chip insertion
     * Terminal prompts for magnetic stripe
     * Transaction amount displayed to customer
     * Transaction is online declined
     * <p>
     * HOST VALIDATIONS
     * ICC Data NOT present in request message
     * Action Code = Declined (Field 39 = '100' in response message)
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (ICC Capable)
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '9' (Technical fallback)
     * Action Code = Declined
     * POS DC correct in request message or POS Entry Mode indicates Magstripe Used
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     */
    @Test
    public void testAXPEMV017() {

    }

    /**
     * TEST CASE NAME
     * AXP EMV 018 - Blocked Card Application
     * To ensure the terminal is able to read a chip card that is blocked and display correct information.
     * <p>
     * Sale
     * N
     * <p>
     * INSTRUCTIONS
     * If the Physical Test Card AEIPS 29 is used for the first time, you may need to enter an incorrect PIN to block the card.
     * Please do so by initiating a sale transaction for any amount, enter incorrect PIN 4433, and then cancel the transaction.
     * If the Test Card AEIPS 29 is simulated by UL Brand Test Tool, no additional steps are required.
     * <p>
     * USER VALIDATIONS
     * Initiate a sale transaction
     * Transaction amount = 47.00 (£/$/€) or 470.00 (Peso)
     * Insert AEIPS 29 Card when prompted
     * Terminal displays the appropriate message
     * Tester terminates the transaction.
     * Terminal prompts for chip insertion
     * Terminal displays Application Label 'AMERICAN EXPRESS' - OR - No Application Label displayed on screen.
     * Transaction amount displayed to customer
     * Terminal displays the appropriate message
     * Transaction is terminated
     */
    @Test
    public void testAXPEMV018() {

    }

    /**
     * AXP EMV 023 - Reversal Issuer Authentication Failed
     * Terminal is able to perform a reversal due to issuer authentication failure.
     * <p>
     * Sale
     * N
     * <p>
     * DESCRIPTIONS
     * Initiate a sale transaction
     * Transaction amount = 52.00 (£/$/€) or 520.00 (Peso)
     * Insert AEIPS 20 Card when prompted
     * If prompted, enter PIN 1234
     * Proceed with transaction
     * Host approves transaction
     * Terminal declines transaction
     * Reversal performed
     * <p>
     * USER VALIDATIONS:
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Transaction is declined
     * Reversal is performed
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘0’ indicates “DDA successful”
     * In 2nd GEN AC TVR - Byte 5 bit 7 = ‘1’ indicates “Issuer Authentication Failed”
     * In 1st GEN AC TVR - Byte 1 bit 6 = ‘0’ indicates  “ICC data not missing”
     * In 1st GEN AC Command, P1 = ‘80’ indicates “ARQC”
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 52.00 or 520.00
     * Transaction amount is 52.00 or 520.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '000' in response message)
     * Action Code = Approved
     * POS DC or POS Entry Mode indicates ICC Used
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     */
    @Test
    public void testAXPEMVSale023() {

    }

    /**
     * AXP EMV 023 - Reversal Issuer Authentication Failed
     * Terminal is able to perform a reversal due to issuer authentication failure.
     * <p>
     * Reversal
     * N
     * <p>
     * No instructions
     * <p>
     * HOST VALIDATIONS
     * POS DC correct in reversal request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '400' in response to reversal message)
     * ICC Data NOT present in request message
     * ICC Data present in request message
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMVReversal023() {

    }

    /**
     * AXP EMV 026 - Offline Enciphered PIN CDA
     * To ensure the terminal prompts for chip insertion and performs a CDA online transaction.
     * If test fails because CDA mode 3 is being used, enter a waiver comment explaining the failure.
     * <p>
     * Sale
     * N
     * <p>
     * INSTRUCTIONS:
     * "Initiate a sale transaction
     * Transaction amount = 55.00 (£/$/€) or 550.00 (Peso)
     * Insert AEIPS 30 Card when prompted
     * If prompted, enter PIN 1234
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS:
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Terminal prompts for PIN if supported
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS
     * In 2nd GEN AC TVR - Byte 1 bit 3 = ‘0’ indicates “CDA successful”
     * In 2nd GEN AC TVR - Byte 5 bit 7 = ‘0’ indicates “Issuer Authentication successful”
     * In 1st GEN AC TVR - Byte 1 bit 6 = ‘0’ indicates  “ICC data not missing”
     * In 1st GEN AC TVR - Byte 2 bit 5 = ‘0’ indicates “Requested service allowed for card product”
     * In 1st GEN AC TVR - Byte 2 bit 8 = ‘0’ indicates “ICC and terminal do not have different application versions”
     * In 1st GEN AC TVR - Byte 4 bit 8 = ‘1’ indicates “Transaction exceeds floor limit”
     * In 1st GEN AC Request, P1 = 90 indicates 'ARQC/CDA Signature'
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 50 indicates 'TC/CDA Signature'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 55.00 or 550.00
     * Transaction amount is 55.00 or 550.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '000' in response message)
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV026() {

    }

    /**
     * AXP EMV 028 - Chip Downgraded Mode Approval
     * To ensure the terminal performs an online chip sale transaction in downgraded mode and approves transaction.
     * <p>
     * Sale
     * N
     * <p>
     * Initiate a sale transaction
     * Transaction amount = 60.00 (£/$/€) or 600.00 (Peso)
     * Insert AEIPS 21 Card when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS:
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS:
     * In 1st GEN AC TVR - Byte 1 bit 7 = ‘0’ indicates “SDA successful”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command NOT sent by the terminal to the card
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 60.00 or 600.00
     * Transaction amount is 60.00 or 600.00
     * <p>
     * HOST VALIDATIONS:
     * ICC Data present in request message
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '000' in response message)
     * ARPC NOT returned in the response message from American Express
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV028() {

    }

    /**
     * AXP EMV 029 - Chip Downgraded Mode Denial
     * To ensure the terminal performs an online chip sale transaction in downgraded mode and declines transaction.
     * <p>
     * Sale
     * N
     * <p>
     * INSTRUCTIONS
     * Initiate a sale transaction
     * Transaction Amount = 61.00 (£/$/€) or 610.00 (Peso)
     * Insert AEIPS 21 Card when prompted
     * Proceed with Transaction
     * Transaction is declined
     * Transaction is online declined
     * <p>
     * CARD LOG VALIDATIONS
     * In 1st GEN AC TVR - Byte 1 bit 7 = ‘0’ indicates “SDA successful”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command NOT sent by the terminal to the card
     * In 2nd GEN AC Command, P1 = 00 indicates 'AAC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 35' indicates 'Do not honour'
     * Transaction amount is 61.00 or 610.00
     * Transaction amount is 61.00 or 610.00
     * <p>
     * HOST VALIDATIONS
     * ICC Data present in request message
     * POS DC correct in request message:
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Declined (Field 39 = '100' in response message)
     * ARPC NOT returned in the response message from American Express
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Authorization Request, Service Code = '221':
     * Field 35 subfield service code = 221 and/or
     * Field 45 subfield service code = 221.
     * Action Code = Declined
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV029() {

    }

    /**
     * AXP EMV 030 - No CVM Required
     * To ensure the terminal is able to perform a transaction with No CVM.
     * <p>
     * Sale
     * N
     * <p>
     * INSTRUCTIONS:
     * Initiate a sale transaction
     * Transaction amount = 62.00 (£/$/€) or 620.00 (Peso)
     * Insert AEIPS 32 Card when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * USER VALIDATIONS:
     * Terminal prompts for chip insertion
     * Terminal displays NO Application Label - OR - “AMERICAN EXPRESS” on screen.
     * Transaction amount displayed to customer
     * Terminal does not prompt for PIN
     * Transaction is online approved
     * <p>
     * CARD LOG VALIDATIONS:
     * In 1st GEN AC TVR - Byte 1 bit 4 = ‘0’ indicates “DDA successful”
     * In 2nd GEN AC TVR - Byte 5 bit 7 = ‘0’ indicates “Issuer Authentication successful”
     * In 1st GEN AC TVR – Byte 3 bit 8 = 0 indicates, Cardholder Verification successful.
     * In 1st GEN AC, TVR - Byte 1 bit 6 = 0 indicates 'ICC data NOT missing'
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 62.00 or 620.00
     * Transaction amount is 62.00 or 620.00
     * <p>
     * HOST VALIDATIONS:
     * ICC Data present in request message
     * POS DC correct
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '000' in response message)
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Action Code = Approved
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     */
    @Test
    public void testAXPEMV030() {

    }

    /**
     * AXP EMV 031 - Online PIN not supported
     * To ensure the terminal performs and approves using alternative CVM when terminal does not support Online PIN.
     * <p>
     * <p>
     * Sale
     * N
     * <p>
     * <p>
     * INSTRUCTIONS:
     * Initiate a sale transaction
     * Transaction amount = 68.00 (£/$/€) or 680.00 (Peso)
     * Insert AEIPS 31 Card when prompted
     * Proceed with transaction
     * On approval, receipt is printed
     * <p>
     * <p>
     * USER VALIDATIONS:
     * Terminal prompts for chip insertion
     * Transaction is online approved
     * <p>
     * <p>
     * CARD LOG VALIDATIONS:
     * In 1st GEN AC TVR - Byte 3 bit 3 = ‘0’ indicates  “Online PIN not entered”
     * In 1st GEN AC Request, P1 = 80 indicates 'ARQC'.
     * External Authenticate command requested
     * In 2nd GEN AC Command, P1 = 40 indicates 'TC'
     * In 2nd GEN AC TVR - Byte 5 bit 7 = 0 indicates 'Issuer Authentication Successful'
     * In 2nd GEN AC Command, Tag 8A(ARC) = '30 30' indicates 'Approved'
     * Transaction amount is 68.00 or 680.00
     * Transaction amount is 68.00 or 680.00
     * <p>
     * <p>
     * ICC Data present in request message
     * POS DC correct
     * Field 22 Position 1 =  '5' (Integrated Circuit Card (ICC))
     * Field 22 Position 6 =  '1' (Card Present)
     * Field 22 Position 7 =  '5' (Integrated Circuit Card (ICC))
     * Action Code = Approved (Field 39 = '000' in response message)
     * Field 52 is not present in request message
     * Field 53 is not present in request message
     * Action Code = Approved
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * Authorization Request, Service Code = '201':
     * Field 35 subfield service code = 201 and/or
     * Field 45 subfield service code = 201.
     * POS DC correct in request message or POS Entry Mode indicates ICC Used
     * Field 52 is not present in request message
     */
    @Test
    public void testAXPEMV031() {

    }

}
