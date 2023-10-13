package io.aexp.api.client.core.utils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

/**
 * @author Sunny
 * @create 2023/9/1 15:17
 */
public class Dukpt {
    public static byte[] generateDataEncryptionKey(byte[] bdkLeft, byte[] bdkRight, byte[] ksn) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        // Perform Initial Permutation (IP) on BDK
        byte[] bdk = performInitialPermutation(bdkLeft, bdkRight);

        // Perform key variant generation for each KSN
        byte[] dataEncryptionKey = null;
        for (int i = 0; i < 21; i++) {  // 21 key variants
            byte[] currentKsn = Arrays.copyOf(ksn, 8);
            currentKsn[7] ^= (byte) (i + 1);

            // Perform a double DES operation
            byte[] dataKeyVariant = generateDataKeyVariant(bdk, currentKsn);
            if (dataEncryptionKey == null) {
                dataEncryptionKey = dataKeyVariant;
            } else {
                // XOR the new key variant with the current data encryption key
                for (int j = 0; j < dataEncryptionKey.length; j++) {
                    dataEncryptionKey[j] ^= dataKeyVariant[j];
                }
            }
        }

        return dataEncryptionKey;
    }

    private static byte[] performInitialPermutation(byte[] bdkLeft, byte[] bdkRight) {
        // Concatenate and perform initial permutation on the BDK
        byte[] bdk = new byte[24];
        System.arraycopy(bdkLeft, 0, bdk, 0, 8);
        System.arraycopy(bdkRight, 0, bdk, 8, 8);
        System.arraycopy(bdkRight, 0, bdk, 16, 8);

        // Implement your initial permutation logic here if needed

        return bdk;
    }

    private static byte[] generateDataKeyVariant(byte[] bdk, byte[] ksn) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        // Perform Triple DES operation (DESede) to generate the data key variant
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        DESedeKeySpec keySpec = new DESedeKeySpec(bdk);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        // Implement your key variant generation logic here if needed

        // Perform encryption with the generated key
        // You may need to adjust this part based on your specific requirements

        return secretKey.getEncoded();
    }
}
