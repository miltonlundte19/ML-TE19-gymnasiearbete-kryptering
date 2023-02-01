package tests;

import modules.crypterings.Cryptaes;
import modules.crypterings.Cryptres;
import setings.ResKeyholder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class testingcrypt {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String testkleinstring = "vad hander om men enkodar och dekodar en ren string";
        System.out.println(testkleinstring + "\n");
        byte[] testkleinstringbytes = testkleinstring.getBytes(StandardCharsets.UTF_8);
        System.out.println("I bytes:\n" + Arrays.toString(testkleinstringbytes) + "\n");

        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

        String testencodedbytes = encoder.encodeToString(testkleinstringbytes);




        System.out.println("Endoded:\n" + testencodedbytes);


        byte[] testdecodedbytes = decoder.decode(testencodedbytes);
        String testdecodedstring = new String(testdecodedbytes, StandardCharsets.UTF_8);

        System.out.printf("dekoded:\n" + Arrays.toString(testdecodedbytes) + "\ni string form:\n" + testdecodedstring + "\n");

        SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(256, sr);
        SecretKey key = kg.generateKey();
        String enkodedekey = new String(key.getEncoded(), StandardCharsets.UTF_8);
        System.out.println("enkoded key:\n" + enkodedekey + "\n");

        String[] messsr = new String[2];
        messsr[0] = "jag är första plasgfuj";
        messsr[1] = "swfjnifeijo";
        System.out.println(messsr.length);

        for (int i = 1; i < messsr.length; i++) {
            System.out.println(messsr[i] + "\n i valiu " + i + "  h");
        }

    }

    public static void Hybridfilekrypt(boolean enORde, File in, File ou, IvParameterSpec iv, ResKeyholder keyholder, boolean keyinfile, boolean storkeyInFile, SecretKey Key) {
        BufferedReader inputStream;
        try {
            inputStream = new BufferedReader(new FileReader(in));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        SecretKey key;
        if (keyinfile) {
            try {
                String keyenkoded = inputStream.readLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static SecretKey asymctyp(boolean enORde, ResKeyholder keyholder, String symekeyenkoded) {
        String symkeye;
        symkeye = Cryptres.Stringcry(enORde, keyholder, symekeyenkoded);
        return null;
    }
    private static String symcryp(File file) throws FileNotFoundException {
        BufferedReader inputstrim;
        String key;
        try {
            inputstrim = new BufferedReader(new FileReader(file));
            key = inputstrim.readLine();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return null;
    }


}
    //  setingsfile.txt