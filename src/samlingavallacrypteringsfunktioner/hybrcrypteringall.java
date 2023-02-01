package samlingavallacrypteringsfunktioner;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class hybrcrypteringall { // använder nog inte mycket från dena men den gjorde så att jag förstod mer

    // ( --------------------------------------------------------
    private BigInteger mod;
    private BigInteger pubExp;
    private BigInteger pvrExp;
    // -----------------------------------------------------------
    public hybrcrypteringall(BigInteger mod, BigInteger pubExp, BigInteger pvrExp) {
        this.mod = mod;
        this.pubExp = pubExp;
        this.pvrExp = pvrExp;
    }
    // ) --------------------------------------------------------

    // rsa get pvr or pub key from mod and exp
    public PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mod,pvrExp);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePrivate(keySpec);
    }

    public PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod, pubExp);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        return fact.generatePublic(keySpec);
    }

    // aes models
    public void hAESkeygen(String secretKeyAlgo, int secretKeySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(secretKeyAlgo);
        keyGenerator.init(secretKeySize);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] secretKeyByte = secretKey.getEncoded(); // -
    }
    // red file
    public void hRedfile(File file) throws IOException {
        // File file = new File(path);   String path
        FileReader reader = new FileReader(file);
        BufferedReader buffReader = new BufferedReader(reader);

        char[] buffer = new char[100];
        int nChar = buffReader.read(buffer);
        StringBuilder sb = new StringBuilder();
        while (nChar>0) {
            for (int i = 0; i < nChar; i++) {
                sb.append(buffer[i]);
            }
            nChar = buffReader.read(buffer);
        }
        String content = sb.toString();
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        System.out.println(Arrays.toString(contentBytes));
    }
    // encrypt mesige     ?
    public void hencrypmes(String content, File file,
                           String symmetricAlgo, SecretKey secretKey,
                           byte[] secretkeyByte, String asymmetricAlgo, PublicKey publicKey) throws IOException {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);

        byte[] encDataBytes = SymmetricCipher.encrypt(contentBytes, symmetricAlgo, secretKey);

        byte[] encSecretkey = AsymmetricCipher.encrypt(secretkeyByte, asymmetricAlgo, publicKey);


        Base64.Encoder encoder = Base64.getEncoder();

        byte[] base64EncSecKey = encoder.encode(encSecretkey);
        byte[] base64EncData = encoder.encode(encDataBytes);

        String encSecretkeyString = new String(base64EncSecKey, StandardCharsets.UTF_8);
        String encDataString = new String(base64EncData, StandardCharsets.UTF_8);

        // file out        (?)
        StringBuilder sb = new StringBuilder();
        sb.append(encSecretkeyString).append(System.lineSeparator()).append(encDataString);

        String encFilePath = file.getPath().concat(".enc");

        File encryptedFile = new File(encFilePath);
        FileWriter writer;
        writer = new FileWriter(encryptedFile);
        BufferedWriter bufWriter = new BufferedWriter(writer);
        bufWriter.write(sb.toString());
        bufWriter.flush();
        bufWriter.close();
    }
    // red en file
    public void hRedencfile(File inFile,
                            String asymmetricAlgo, PrivateKey pvtKey,
                            String secretKeyAlgo, String symmetricAlgo) throws IOException {
        String[] contents = new String[2];
        contents = extractDataElements(inFile);

        for (int i = 0; i < contents.length; i++) {
            contents[i] = filterNewlineChars(contents[i]);
        }

        // decrypting key
        Base64.Decoder decoder = Base64.getDecoder();

        byte[] encSecretKeyByte = decoder.decode(contents[0]);

        byte[] secretKeyBytes = AsymmetricCipher.decrypt(encSecretKeyByte, asymmetricAlgo, pvtKey);

        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, 0, secretKeyBytes.length, secretKeyAlgo);

        // decrypting mesige
        ArrayList<byte[]> dataList = new ArrayList<byte[]>();
        for (int i = 1; i < contents.length; i++) {
            byte[] encStrByte = decoder.decode(contents[i]);

            byte[] messageByte = SymmetricCipher.decrypt(encStrByte, symmetricAlgo, secretKey);

            dataList.add(messageByte);
            contents[i] = new String(messageByte, StandardCharsets.UTF_8);
        }

        // riting to file
        int lastIndx = inFile.getPath().lastIndexOf(".");
        String outpath = inFile.getPath().substring(0, lastIndx);

        lastIndx = outpath.lastIndexOf(".");
        outpath = outpath.substring(0, lastIndx).concat(".dat");
        outpath = outpath.substring(0, lastIndx).concat(".dat");

        File nwFile = new File(outpath);
        OutputStream os = new FileOutputStream(nwFile);

        for (byte[] data : dataList) {
            os.write(data);
        }
        os.flush();
        os.close();

        // return nwFile;
    }

    private String filterNewlineChars(String data) {
        if (data == null) {
            return null;
        }
        char carriageReturn = '\r';
        char newLine = '\n';
        int length = data.length();
        int indx = 0;

        while (indx < length && (data.charAt(indx) == carriageReturn || data.charAt(indx) == newLine)) {
            indx++;
        }

        int startIndx = indx;
        indx = length - 1;
        while (indx >= startIndx && (data.charAt(indx) == carriageReturn || data.charAt(indx) == newLine)) {
            indx--;
        }
        int endIndx = indx+1;

        String cleanData = data.substring(startIndx, endIndx);

        return  cleanData;
    }

    private String[] extractDataElements(File inFile) {
        String[] dataArr = new String[2];

        FileReader reader;
        BufferedReader bufReader;

        char nwLine = '\n';
        char cr = '\r';
        try {
            reader = new FileReader(inFile);
            bufReader = new BufferedReader(reader);

            // reding enc key
            String encSecretKey = bufReader.readLine();

            // reding rest
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[10];
            int nChars = bufReader.read(buffer);
            while (nChars>0) {
                for (int i = 0; i < nChars; i++) {
                    sb.append(buffer[i]);
                }
                nChars = bufReader.read(buffer);
            }
            dataArr[0] = encSecretKey;
            dataArr[1] = sb.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataArr;
    }

    private static class SymmetricCipher {
        public static byte[] encrypt(byte[] contentBytes, String symmetricAlgo, SecretKey secretKey) {
            return new byte[0];
        }

        public static byte[] decrypt(byte[] encStrByte, String symmetricAlgo, SecretKey secretKey) {
            return new byte[0];
        }
    }

    private static class AsymmetricCipher {
        public static byte[] encrypt(byte[] secretkeyByte, String asymmetricAlgo, PublicKey publicKey) {
            return new byte[0];
        }

        public static byte[] decrypt(byte[] encSecretKeyByte, String asymmetricAlgo, PrivateKey pvtKey) {
            return new byte[0];
        }
    }
}
// https://dzone.com/articles/security-implementation-of-hybrid-encryption-using