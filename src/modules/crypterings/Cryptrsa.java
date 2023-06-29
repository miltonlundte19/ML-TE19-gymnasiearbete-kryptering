package modules.crypterings;

import setings.RsaKeyholder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptrsa {
    // kommer ändra Key till en egen som har antingen en pub eller en pri key
    // i if satsen ändrar till respective key type
    public static String Stringcry(boolean enORde, RsaKeyholder keyholder, String input) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (enORde) {
                if (keyholder.isPriORpub()) {
                    cipher.init(Cipher.ENCRYPT_MODE, keyholder.getPrivateKey());
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, keyholder.getPublicKey());
                }
                byte[] result = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(result);
            } else {
                if (keyholder.isPriORpub()) {
                    cipher.init(Cipher.DECRYPT_MODE, keyholder.getPrivateKey());
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, keyholder.getPublicKey());
                }
                byte[] result = cipher.doFinal(Base64.getDecoder().decode(input));
                return new String(result, StandardCharsets.UTF_8);
            }
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Filebufercry(boolean enORde, boolean s, RsaKeyholder keyholder, File in, File ou) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (enORde) {
                if (keyholder.isPriORpub()) {
                    cipher.init(Cipher.ENCRYPT_MODE, keyholder.getPrivateKey());
                } else {
                    cipher.init(Cipher.ENCRYPT_MODE, keyholder.getPublicKey());
                }
            } else {
                if (keyholder.isPriORpub()) {
                    cipher.init(Cipher.DECRYPT_MODE, keyholder.getPrivateKey());
                } else {
                    cipher.init(Cipher.DECRYPT_MODE, keyholder.getPublicKey());
                }
            }
            FileInputStream inputStream = new FileInputStream(in);
            FileOutputStream outputStream = null;
            if (s) {
                System.out.println("\nskriver till filen");
                outputStream = new FileOutputStream(ou);
            }
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer,0,bytesRead);
                if (output != null && s) {
                    outputStream.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null && s) {
                outputStream.write(outputBytes);
            }
            inputStream.close();
            if (s) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
