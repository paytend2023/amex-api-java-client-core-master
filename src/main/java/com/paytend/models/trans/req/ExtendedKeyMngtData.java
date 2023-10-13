package com.paytend.models.trans.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gudongyang
 */
@Getter
@Setter
public class ExtendedKeyMngtData {
    String ExtDynSesKeyPINData;
    String ExtPINDataTxt1;
    String DynSesKeyChkVal;
    String ExtDUKPTPINData;
    String ExtPINDataTxt2;
    String DUKPTFormat;
    String KeySerNbr;
    String KSNKeySetId;
    String KSNDID;
    String KSNTxnCtr;
}

/*
<ExtendedKeyMngtDataReq>
  <ExtDynSesKeyPINData></ExtDynSesKeyPINData>
    <ExtPINDataTxt></ExtPINDataTxt>
    <DynSesKeyChkVal></DynSesKeyChkVal>
    <ExtDUKPTPINData></ExtDUKPTPINData>
    <ExtPINDataTxt></ExtPINDataTxt>
    <DUKPTFormat></DUKPTFormat>
    <KeySerNbr></KeySerNbr>
    <KSNKeySetId></KSNKeySetId>
    <KSNDID></KSNDID>
    <KSNTxnCtr></KSNTxnCtr>
</ExtendedKeyMngtDataReq>
*/
