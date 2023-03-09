package modules.crypterings;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptaes {

    public static String Stringcry(boolean enORde, IvParameterSpec iv, SecretKey key, String masige) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (enORde) {
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
                byte[] cipherText = cipher.doFinal(masige.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(cipherText);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
                byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(masige));
                return new String(plainText, StandardCharsets.UTF_8);
            }
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void Filebufercry(boolean enORde, boolean s, IvParameterSpec iv, SecretKey key, File in, File ou) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (enORde) {
                cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key, iv);
            }
            FileInputStream inputStream = new FileInputStream(in);
            FileOutputStream outputStream = null;
            if (s) {
                System.out.println("\nskriver till filen");
                outputStream = new FileOutputStream(ou);
            } else {
                System.out.println("skriver inte till filen");
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null && s) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null & s) {
                outputStream.write(outputBytes);
            }
            inputStream.close();
            if (s) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | IOException | BadPaddingException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
