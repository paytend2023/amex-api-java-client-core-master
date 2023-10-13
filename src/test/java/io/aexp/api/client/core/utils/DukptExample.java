package io.aexp.api.client.core.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * @author Sunny
 * @create 2023/9/1 15:14
 */
public class DukptExample {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        // Replace this with your actual terminal identifier (e.g., terminal serial number)
        String terminalIdentifier = "1234567890123456";
        String dataToEncrypt = "1234567890123456"; // Replace with your data

        // Generate the IPEK (Initial PIN Encryption Key) using the terminal identifier

        byte[] bdk = hexStringToByteArray("00112233445566778899AABBCCDDEEFF"); // Replace with your BDK
        byte[] bdkLeft = Arrays.copyOfRange(bdk, 0, 8);
        byte[] bdkRight = Arrays.copyOfRange(bdk, 8, 16);
        byte[] terminalBytes = hexStringToByteArray(terminalIdentifier);
        byte[] ksn = new byte[10];
        System.arraycopy(terminalBytes, 2, ksn, 0, 6);
        byte[] dataEncryptionKey = Dukpt.generateDataEncryptionKey(bdkLeft, bdkRight, ksn);

        // Encrypt the data using the generated Data Encryption Key (DEK)
        Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(dataEncryptionKey, "DESede");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedData = cipher.doFinal(hexStringToByteArray(dataToEncrypt));

        System.out.println("Encrypted Data: " + byteArrayToHexString(encryptedData));
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
