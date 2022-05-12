package modules;

import setings.AESsettings;
import setings.RESsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.security.*;
import java.util.Arrays;

public class SetingsModel {
    private Settings settings;
    private AESsettings aes;
    private RESsettings res;
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



    public SetingsModel() {
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
            res = new RESsettings();
        }
    }

    public void setENorDE() {
        settings.setChekORen();
        check[1] = true;
    }

    public void setMesige(String mesige) {
        settings.setChekORstr();
        if (id == 1) {
            aes.setPlainText(mesige);
        } else if (id == 2) {
            res.setMesige(mesige);
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
            res.setFiles(files);
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
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecureRandom sr = new SecureRandom();
        kpg.initialize(2048,sr);
        keyPair = kpg.generateKeyPair();
    }

    public void storPublickey(File pubkeyfile) {

    }

    public void storPrivetkey(File prikeyfile) {

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
        if (lastcheck) {
            return settings;
        }
        return null;
    }



}
