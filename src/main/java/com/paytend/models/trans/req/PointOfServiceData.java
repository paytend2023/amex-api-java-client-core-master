package com.paytend.models.trans.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

/**
 * @author gudongyang
 */
@Data
@Builder
@Getter
@Setter
public class PointOfServiceData {

    /**
     * 0 Unknown
     * 1 Manual, no terminal
     * 2 Magnetic stripe read
     * 3 Bar code
     * 4 Optical Character Recognition (OCR)
     * 5 Integrated Circuit Card (ICC)
     * Note: American Express-certified EMV terminal and link
     * 6 Key entered
     * 7 Reserved for ISO use
     * 8 Reserved for national use
     * 9 Reserved for private use
     * A Credentials on File
     * B-I Reserved for ISO use
     * J-R Reserved for national use
     * S-Z Reserved for private use
     * <p>
     * Bit 22.1
     */
    String CardDataInpCpblCd;


    /**
     * 0 No electronic authentication or unknown
     * 1 PIN
     * 2 Electronic signature analysis
     * 3 Biometrics
     * 4 Biographic
     * 5 Electronic authentication inoperative
     * 6 Other
     * Bit 22.2
     */
    @JacksonXmlProperty(localName = "CMAuthnCpblCd")
    String CMAuthnCpblCd;

    /**
     * 0 Unspecified, unknown, track data present but incomplete or truncated
     * 1 Manual, no terminal
     * 2 Magnetic stripe read.
     * Note: CardDataInpModeCd = 2 only if this transaction contains CardTrack1Data [preferred] and/or CardTrack2Data data captured intact from the magnetic stripe.
     * 3 Bar code
     * 4 Optical Character Recognition (OCR)
     * 5 Integrated Circuit Card (ICC).
     * Notes:
     * 1. CardDataInpModeCd, Position 7, value 5 only if this transaction contains ICCSDataTxt
     * and CardTrack2Data captured intact from the chip (Non-Payment Token transactions).
     * 2. If value Z is present in Position 6 (Digital Wallet - application initiated Payment Token) transactions,
     * then Position 7, value 5 (Integrated Circuit Card ICC) must be read. 3. American Express-certified EMV terminal and link.
     * 6 Key entered
     * 7 Reserved for ISO use
     * 8 Reserved for national use
     * 9 Technical fallback - Transaction initiated as chip but was processed using an alternative technology (such as magnetic stripe).
     * A  Credentials on File
     * B-I Reserved for ISO use
     * J-R Reserved for national use
     * S Manually entered or keyed transaction with keyed CID/4DBC/4CSC. Data Element must be present. For more information, see page 156.
     * T Reserved for private use
     * U Reserved for private use
     * V Reserved for American Express network use
     * W Swiped transaction with keyed CID/4DBC/4CSC. Data Element 53 (Security Related Control Information) must be present.
     * <p>
     * Bit 22.3
     */
    String CardCptrCpblCd;

    /**
     * 0 No terminal used or unknown
     * 1 On premises of card acceptor, attended
     * 2 On premises of card acceptor, unattended (e.g., Oil CAT/Customer Activated Terminals, kiosks, self-checkout, etc.)
     * 3 Off premises of card acceptor, attended (e.g., portable POS device at trade shows, service calls, taxis, etc.)
     * 4 Off premises of card acceptor, unattended (e.g., Food/Beverage vending machines, DVD vending machines, etc.)
     * 5 On premises of Cardmember, unattended
     * 9 Delivery mode unknown, unspecified
     * S Electronic delivery of product (e.g., music, software, electronic tickets, etc., downloaded via Internet)
     * T Physical delivery of product (e.g., music, software, tickets, etc., delivered by mail/courier)
     * Z Transit Access Terminal - TAT
     * Bit 22.4
     */
    String OprEnvirCd;

    /**
     * 0 Cardmember present
     * 1 Cardmember not present, unspecified, unknown
     * 2 Cardmember not present, mail order
     * 3 Cardmember not present, telephone
     * 4 Cardmember not present, standing authorization —
     * To be used for situations where Cardmember information is on record (card on file);
     * however, the billing frequency and amount are variable (e.g., travel, car rental, lodging, preferred clubs,
     * frequent customer, delayed shipment, split bill transactions).
     * <p>
     * 5-6 Reserved for ISO use
     * 7-8 Reserved for national use
     * 9  Cardmember not present, recurring billing — Used for regular recurring transactions,
     * such as periodic billings (e.g., membership dues, subscribed services, insurance premiums,
     * wireless services, newspaper and other regularly scheduled charges).
     * The recurring billing amount can vary.
     * <p>
     * A-I Reserved for ISO use
     * J-R Reserved for national use
     * S Cardmember not present, electronic transaction (e.g., Internet)
     * T Reserved for American Express network use
     * U-Z Reserved for private use
     * <p>
     * Bit 22.5
     */
    @JacksonXmlProperty(localName = "CMPresentCd")
    String CMPresentCd;
    /**
     * 0 Card not present
     * 1 Card present
     * 2-4 Reserved for ISO use
     * 5-7  Reserved for national use
     * 8-9  Reserved for private use
     * A-I  Reserved for ISO use
     * J-R  Reserved for national use
     * S-V Reserved for private use
     * W Transponder (RFID token) — For transactions initiated by an electronic, radio-frequency device
     * (transponder or RFID, e.g., Speedpass), CardPresentCd Value W may be used alone, or in conjunction with a Security Identification Code in SecIdCd. Alternately, a Security Identification Code may be entered in SecIdCd without Value W in CardPresentCd. Ideally, both items are transmitted. For more details, see page 186.
     * Note: Do not use this value for American Express Expresspay transactions. For more information, see page 18.
     * X Contactless transactions, including American Express Expresspay. For more information, see page 18.
     * Y Mobile Proximity Payment - American Express internal use only
     * Z Digital Wallet - application initiated (including application initiated Payment Token) transactions Note: Position 6, value Z must be used with CardDataInpModeCd, Position 7, value 5.
     * <p>
     * Bit 22.6
     */
    String CardPresentCd;


    /**
     * 0 Unspecified, unknown, track data present but incomplete or truncated
     * 1 Manual, no terminal
     * 2 Magnetic stripe read.
     * Note: CardDataInpModeCd = 2 only if this transaction contains CardTrack1Data [preferred] and/or CardTrack2Data data captured intact from the magnetic stripe.
     * 3 Bar code
     * 4 Optical Character Recognition (OCR)
     * 5 Integrated Circuit Card (ICC).
     * Notes:
     * 1. CardDataInpModeCd, Position 7, value 5 only if this transaction contains ICCSDataTxt
     * and CardTrack2Data captured intact from the chip (Non-Payment Token transactions).
     * 2. If value Z is present in Position 6 (Digital Wallet - application initiated Payment Token) transactions,
     * then Position 7, value 5 (Integrated Circuit Card ICC) must be read. 3. American Express-certified EMV terminal and link.
     * 6 Key entered
     * 7 Reserved for ISO use
     * 8 Reserved for national use
     * 9 Technical fallback - Transaction initiated as chip but was processed using an alternative technology (such as magnetic stripe).
     * A Credentials on File
     * B-I Reserved for ISO use
     * J-R Reserved for national use
     * S Manually entered or keyed transaction with keyed CID/4DBC/4CSC. Data Element must be present. For more information, see page 156.
     * T Reserved for private use
     * U Reserved for private use
     * V Reserved for American Express network use
     * W Swiped transaction with keyed CID/4DBC/4CSC. Data Element 53 (Security Related Control Information) must be present.
     * <p>
     * Bit 22.7
     */
    String CardDataInpModeCd;

    /**
     * 0 Not authenticated, unknown
     * 1 PIN
     * 3 Electronic signature analysis
     * 4 Biometrics
     * 5 Biographic
     * S Electronic Ticket Environment
     * <p>
     * Bit 22.8
     */
    @JacksonXmlProperty(localName = "CMAuthnMthdCd")
    String CMAuthnMthdCd;

    /**
     * 0 Not authenticated, unknown
     * 1 Integrated Circuit Card (ICC)
     * Note: American Express-certified EMV terminal and link
     * 2 Card Acceptor Device (CAD)
     * 3 Authorizing agent (identified in authorizing agent institution identification code)
     * 4 By Merchant
     * 5 Other
     * 6 Reserved for ISO use
     * 7 Reserved for national use
     * 8-9 Reserved for private use
     * A-I Reserved for ISO use
     * J-R Reserved for national use
     * S-Z Reserved for private use
     * <p>
     * Bit 22.2
     */
    @JacksonXmlProperty(localName = "CMAuthnEnttyCd")
    String CMAuthnEnttyCd;

    /**
     * Bit 22.2
     */
    String CardDataOpCpblCd;

    /**
     * Bit 22.2
     */
    String TrmnlOpCpblCd;
    /**
     * Bit 22.2
     */
    @JacksonXmlProperty(localName = "PINCptrCpblCd")
    String PINCptrCpblCd;

    @Tolerate
    public PointOfServiceData() {
    }
}
