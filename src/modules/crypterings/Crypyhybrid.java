package modules.crypterings;

import setings.RsaKeyholder;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

public class Crypyhybrid {
    public static void hybrid(boolean enORde, boolean stor, boolean keystor, IvParameterSpec iv, Object key, File in, File ou, RsaKeyholder keyholder) {
        // Objekt key = File (aes key in the File) || boolean (aes key is in the in File) || SecretKey ;
        ObjectOutputStream outputStream;

        String keystring = "";
        if (key.getClass().equals(SecretKeySpec.class))
            keystring = key.toString();
        if  (key.getClass().equals(Boolean.class))
            keystring = (String) Readaeskeyfromfail(in);
        if (key.getClass().equals(File.class))
            keystring = (String) Readaeskeyfromfail((File) key);

        String kryptkeystring = Cryptrsa.Stringcry(enORde,keyholder, keystring);


        if (enORde) {
            if (stor) {

            } else {

            }
        } else {
            if (stor) {

            } else {

            }
        }

    }

    private static Object Readaeskeyfromfail(File file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
