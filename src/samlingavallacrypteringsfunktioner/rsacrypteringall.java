package samlingavallacrypteringsfunktioner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class rsacrypteringall {
    // Key pair genirator
    public static KeyPair generatekeyrng() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        SecureRandom sr = new SecureRandom();
        kpg.initialize(2048,sr);
        return kpg.generateKeyPair();
    } // från: https://www.tutorialspoint.com/java_cryptography/java_cryptography_encrypting_data.htm och https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/?ref=lbp

    // save key to file
    public static void storpubkey(PublicKey key, File file) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(key, RSAPublicKeySpec.class);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(publicKeySpec.getModulus());
        outputStream.writeObject(publicKeySpec.getPublicExponent());
        outputStream.flush();
        outputStream.close();
    } // från: https://www.javainterviewpoint.com/rsa-encryption-and-decryption/
    public static void storprikey(PrivateKey key, File file) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(key, RSAPrivateKeySpec.class);
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        outputStream.writeObject(privateKeySpec.getModulus());
        outputStream.writeObject(privateKeySpec.getPrivateExponent());
        outputStream.flush();
        outputStream.close();
    }

    // read key from file
    public static Key readkey(File keyfile, boolean pubORpri) throws IOException {
        ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(keyfile)));
        String filename = keyfile.getName();
        try {
            BigInteger modulus = (BigInteger) inputStream.readObject();
            BigInteger exponent = (BigInteger) inputStream.readObject();
            inputStream.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (filename.startsWith("public")) {
                return keyFactory.generatePublic(new RSAPublicKeySpec(modulus,exponent));
            } else if (filename.startsWith("private")) {
                return keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus,exponent));
            }
        } catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    } // från: https://www.javainterviewpoint.com/rsa-encryption-and-decryption/


    // res encrypt/decrypt string med privet eller public key (ingen chek på storlek)
    public static String resencryptstring(Key key, String input, boolean enORde) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        if (enORde) {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return new String(result, StandardCharsets.UTF_8);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(result, StandardCharsets.UTF_8);
        }
    } // från: https://www.baeldung.com/java-rsa och https://www.geeksforgeeks.org/asymmetric-encryption-cryptography-in-java/?ref=lbp


    // res encrypt/decrypt file (ingen chek på storlek)
    public static void resEncryptFilInOut(Object key, File in, File ou, int enORde) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        if (enORde == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, (Key) key);
        } else if (enORde == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, (Key) key);
        } else {
            System.exit(-1);
        }
        FileInputStream inputStream = new FileInputStream(in);
        FileOutputStream outputStream = new FileOutputStream(ou);
        byte[] buffer = new byte[1024];
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
        outputStream.flush();
        outputStream.close();

    } // från: https://www.novixys.com/blog/rsa-file-encryption-decryption-java/
    // namne på variablerna från:
}


/*
alla länkar:
https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/
https://www.baeldung.com/java-cipher-input-output-stream
https://www.baeldung.com/java-aes-encryption-decryption
används inte:
https://www.codejava.net/coding/file-encryption-and-decryption-simple-example
https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
 */