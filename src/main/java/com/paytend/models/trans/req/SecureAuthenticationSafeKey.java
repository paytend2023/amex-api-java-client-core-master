package com.paytend.models.trans.req;

/**
 * @author gudongyang
 */
public class SecureAuthenticationSafeKey {
    String ScndIdCd;
    String ElecComrceInd;
    String AmexExpVerificationValTxt;


    String TokenDataBlockA;
    String TokenDataBlockB;
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