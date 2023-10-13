package com.paytend.models.trans.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

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
     * 05= Authenticated with AEVV1
     * 06= Attempted with AEVV
     * 07= Not Authenticated
     * 20= Payment Token data
     */
    String ElecComrceInd;
    /**
     * This data element contains the American Express Verification Value (AEVV)
     */
    String AmexExpVerificationValTxt;
    /**
     * This data element contains the American Express SafeKey Transaction Identifier,
     * which is determined during American Express SafeKey payment authentication.
     *
     * Note: The American Express SafeKey Transaction Identifier in this data element is not the same as
     * the Transaction Identifier (TID), defined in data element TransId for Authorization Response (1110),
     * Reversal Advice Request (1420)and Reversal Advice Response (1430)messages
     */
    String AESKTransId;
    String TokenDataBlockA;
    String TokenDataBlockB;

    @Tolerate
    public SecureAuthenticationSafeKey() {
    }

}

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