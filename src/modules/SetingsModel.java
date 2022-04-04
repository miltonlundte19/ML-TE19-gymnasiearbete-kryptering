package modules;

import setings.Settings;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SetingsModel {
    private Settings settings;
    private byte id;
    private boolean[] check = new boolean[6];
    private boolean lastcheck = false;
    /* check[    ]   check lista för att se så att alla nödvendiga settings har ett värde
    0 = id
    1 = key
    2 = iv
    3 = plainText
    4 = Filein
    5 = Fileou
     */

    public SetingsModel() {
        Arrays.fill(check, false);
        generateIV();
    }

    public void generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        settings = new Settings(new IvParameterSpec(iv));
        check[2] = true;
    }

    public void generateRkey() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            settings.setKey(kg.generateKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        check[1] = true;
    }

    public void setID(byte id) {
        this.id = id;
        settings.setId(id);
        check[0] = true;
    }

    public void setMesige(String mesige) {
        settings.setPlainText(mesige);
        settings.setStringORfile(true);
        check[3] = true;
    }

    public void setFiles(File in, File ou) {
        setInfile(in);
        setOufile(ou);
    }
    public void setInfile(File in) {
        settings.setIn(in);
        settings.setFileinstring(in.getAbsolutePath());
        settings.setStringORfile(false);
        check[4] = true;
    }
    public void setOufile(File ou) {
        settings.setOu(ou);
        settings.setFileoustring(ou.getAbsolutePath());
        check[5] = true;
    }

    public boolean check() {
        if (id == 1) {
            if (check[1] && check[2]) {
                if (check[3] && !check[4] && !check[5]) {
                    lastcheck = true;
                    return true;
                } else if (!check[3] && check[4] && check[5]) {
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
