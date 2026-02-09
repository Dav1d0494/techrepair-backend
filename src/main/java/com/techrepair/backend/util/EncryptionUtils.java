package com.techrepair.backend.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {
    private static final String ALGO = "AES";
    private static final byte[] KEY = "16byteslongkey!!".getBytes();

    public static String encrypt(String plain) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGO);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.getEncoder().encodeToString(c.doFinal(plain.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String encrypted) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGO);
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(c.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            return null;
        }
    }
}
