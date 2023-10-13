package com.paytend.models.trans.req;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
/*
American Express SafeKey
             <SecureAuthenticationSafeKey>
              <ScndIdCd></ScndIdCd>
              <ElecComrceInd></ElecComrceInd>
              <AmexExpVerificationValTxt> </AmexExpVerificationValTxt>
              <AESKTransId></AESKTransId>
             </SecureAuthenticationSafeKey>
American Express Payment Token
             <SecureAuthenticationSafeKey>
              <ScndIdCd></ScndIdCd>
              <ElecComrceInd></ElecComrceInd>
              <TokenDataBlockA></TokenDataBlockA>
              <TokenDataBlockB></TokenDataBlockB>
             </SecureAuthenticationSafeKey>
 */

/**
 * @author gudongyang
 */
@Builder
@Setter
@Getter
public class SecureAuthenticationSafeKey {

    /**
     * ASK = American Express SafeKey
     * TKN = Payment Token transactions
     */
    String ScndIdCd;
    /**
     * This data element contains the American Express SafeKey Electronic Commerce Indicator (ECI) or Payment Token data.
     * This data element indicates the level of security used when the Cardmember provided payment information to the
     * Merchant during an American Express SafeKey or Payment Token transaction. Valid values include:
     * <p>
     * 05= Authenticated with AEVV1
     * 06= Attempted with AEVV
     * 07= Not Authenticated
     * 20= Payment Token data
     */
    String ElecComrceInd;


    /**
     * This data element contains the American Express Verification Value (AEVV),
     * a cryptographic value derived by the Issuer during the American Express SafeKey payment authentication
     * that can provide evidence of the results of payment authentication during an online purchase.
     */
    String AmexExpVerificationValTxt;
    /**
     * This data element contains the American Express SafeKey Transaction Identifier,
     * which is determined during American Express SafeKey payment authentication.
     * <p>
     * Note: The American Express SafeKey Transaction Identifier in this data element is not the same as
     * the Transaction Identifier (TID), defined in data element TransId for Authorization Response (1110),
     * Reversal Advice Request (1420)and Reversal Advice Response (1430)messages
     */
    @JacksonXmlProperty(localName = "AESKTransId")
    String AESKTransId;

    /**
     * This sub-element contains bytes 1-20 of the cryptographic value.
     */
    String TokenDataBlockA;

    /**
     * This sub-element contains bytes 21-80 of the cryptographic value.
     */
    String TokenDataBlockB;

    @Tolerate
    public SecureAuthenticationSafeKey() {
    }

}

