package com.bit.lot.flower.auth.common;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

  private static final String AES = "AES";

  private static String secretKey;

      public static String encrypt(String data) {
        try {
            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] validKeyBytes = new byte[16];
            System.arraycopy(keyBytes, 0, validKeyBytes, 0, Math.min(keyBytes.length, validKeyBytes.length));

            SecretKey key = new SecretKeySpec(validKeyBytes, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedValue = cipher.doFinal(data.getBytes());

            String modifiedEncryptedValue = Base64.getEncoder().encodeToString(encryptedValue)
                    .replace('+', '-').replace('/', '_');

            return modifiedEncryptedValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

     public static String decrypt(String encryptedData) {
        try {
            String base64String = encryptedData.replace('-', '+').replace('_', '/');

            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
            byte[] validKeyBytes = new byte[16];
            System.arraycopy(keyBytes, 0, validKeyBytes, 0, Math.min(keyBytes.length, validKeyBytes.length));

            SecretKey key = new SecretKeySpec(validKeyBytes, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decodedValue = Base64.getDecoder().decode(base64String);
            byte[] decryptedValue = cipher.doFinal(decodedValue);

            return new String(decryptedValue, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

  @Value("${encrypt.key.user}")
  private void setSecretKey(String value) {
    secretKey = value;
  }


}