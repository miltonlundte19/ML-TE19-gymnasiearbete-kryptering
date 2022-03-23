package modules.crypterings;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class aescrypteringall {
    // iv genirator
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    } // från https://www.baeldung.com/java-aes-encryption-decryption och (https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/)

    // aes cryptering string med Secret Key
    public static String aesencryptstringseckey(String algorithm, String plainText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm); // algorithm = AES/CBC/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    } // från https://www.baeldung.com/java-aes-encryption-decryption och (https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/)
    public static String aesdecryptstringseckey(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(algorithm); // algorithm = AES/CBC/PKCS5Padding
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    // aes cryptering fil med Secret Key och för stor fill
    public static void aesEncryptFilInOutSecKey(String algorithm, SecretKey key, IvParameterSpec iv, File in, File ou) {
        Cipher cipher = Cipher.getInstance(algorithm); // algorithm = AES/CBC/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        FileInputStream inputStream = new FileInputStream(in);
        FileOutputStream outputStream = new FileOutputStream(ou);
        byte[] buffer = new byte[64];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            if (output != null) {
                outputStream.write(output);
            }
        }
        byte[] outputBytes = cipher.doFinal();
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        }
        inputStream.close();
        outputStream.close();
        }
    }
}
