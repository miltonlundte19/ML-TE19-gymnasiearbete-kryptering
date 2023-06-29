package modules;

import setings.AESsettings;
import setings.RSAsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.KeyGenerator;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

public class SetingsModelOLD {

    // har myket funktioner och variablar som inte fungerar eller används (måste tita i genome)
    private Settings settings;
    private AESsettings aes;
    private RSAsettings rsa;
    private byte id;
    private boolean[] check = new boolean[7];
    private boolean lastcheck = false;
    /* check[    ]   check lista för att se så att alla nödvendiga settings har ett värde
    0 = id
    1 = EncryptORdecrypt    true = Encrypt  false = Decrypt
    2 = key
    3 = iv
    4 = plainText
    5 = Filein
    6 = Fileou
     */
    private KeyPair keyPair;



    public SetingsModelOLD() {
        settings = new Settings();

        Arrays.fill(check, false);
    }

    public void setID(byte id) {
        this.id = id;
        settings.setId(id);
        check[0] = true;
        if (id == 1) {
            aes = new AESsettings();
            generateIV();
        } else if (id == 2) {
            rsa = new RSAsettings();
        }
    }

    public void setENorDE() {
        settings.setCheekEncryption();
        check[1] = true;
    }

    public void setMesige(String mesige) {
        settings.setCheekString();
        if (id == 1) {
            aes.setPlainText(mesige);
        } else if (id == 2) {
            rsa.setMesige(mesige);
        }
        check[4] = true;
    }

    public void setFiles(File in, File ou) {
        Settingsfile files = new Settingsfile();
        files.setIN(in);
        files.setOU(ou);
        if (id == 1) {
            aes.setFiles(files);
        } else if (id == 2) {
            rsa.setFiles(files);
        }
    }





    // metoder bara för aes
    public void generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        aes.setIv(iv);
        check[3] = true;
    }

    public void generateRkey() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            aes.setKey(kg.generateKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        check[2] = true;
    }


    // metoder bara för res
    public void generateRkeypar() {
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecureRandom sr = new SecureRandom();
        kpg.initialize(2048,sr);
        keyPair = kpg.generateKeyPair();
    }

    public void setkeyFile(File keyfile, boolean priORpub) {

    }
    public boolean storKey(File keyfile, boolean priORpub) {
        if (keyPair == null || !keyfile.exists()) {
            return false;
        }
        rsa.setPriORpub(priORpub);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(keyfile)));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            if (priORpub) {
                RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
                outputStream.writeObject(privateKeySpec.getModulus());
                outputStream.writeObject(privateKeySpec.getPrivateExponent());
            } else {
                RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
                outputStream.writeObject(publicKeySpec.getModulus());
                outputStream.writeObject(publicKeySpec.getPublicExponent());
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        rsa.setKeyfile(keyfile);
        return true;
    }

    public boolean check() {
        if (id == 1) { // 1 = aes
            if (check[1] && check[2] && check[3]) {
                if (check[4] && !check[5] && !check[6]) {
                    lastcheck = true;
                    return true;
                } else if (!check[4] && check[5] && check[6]) {
                    lastcheck = true;
                    return true;
                }
            }
        }
        return false;
    }

    public Settings getSettings() {
        return settings;
    }

}
