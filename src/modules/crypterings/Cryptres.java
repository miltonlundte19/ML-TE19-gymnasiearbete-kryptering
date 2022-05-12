package modules.crypterings;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Cryptres {
    // kommer ändra Key till en egen som har antingen en pub eller en pri key
    // i if satsen ändrar till respective key type
    public static String Stringcry(boolean enORde, Key key, String input) {
        try {
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
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public static void Filebufercry(boolean enORde, Key key, File in, File ou) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            if (enORde) {
                cipher.init(Cipher.ENCRYPT_MODE, key);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, key);
            }
            FileInputStream inputStream = new FileInputStream(in);
            FileOutputStream outputStream = new FileOutputStream(ou);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer,0,bytesRead);
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
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
