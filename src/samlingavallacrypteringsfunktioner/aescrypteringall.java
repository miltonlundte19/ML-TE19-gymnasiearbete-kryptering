package samlingavallacrypteringsfunktioner;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class aescrypteringall {
    // iv genirator
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    } // från https://www.baeldung.com/java-aes-encryption-decryption och (https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/)

    // key genirator
    public static SecretKey generatekeyrng() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    } // från https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/ och https://www.baeldung.com/java-aes-encryption-decryption
    public static SecretKey generatekeypas(String password, String salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    } // från https://www.baeldung.com/java-aes-encryption-decryption

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
    public static void aesEncryptFilInOutSecKey(String algorithm, SecretKey key, IvParameterSpec iv, File in, File ou) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException {
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



/*
alla länkar:
https://www.novixys.com/blog/rsa-file-encryption-decryption-java/
https://www.tutorialspoint.com/java_cryptography/java_cryptography_encrypting_data.htm
https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/?ref=lbp
https://www.baeldung.com/java-rsa
https://programmer.group/rsa-encryption-and-decryption-java.html
https://www.javainterviewpoint.com/rsa-encryption-and-decryption/
 */
