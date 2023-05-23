package modules;

import setings.AESsettings;
import setings.RESsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class SettingsModel {
    private Settings settings;
    private AESsettings aes;
    private RESsettings res;
    private Byte id;
    private Settingsfile settingsfile = null;


    private final boolean[] check = new boolean[9];


    public SettingsModel() {
        settings = new Settings();
        Arrays.fill(check, false);
    }
    public void setId(Byte id) {
        boolean b = true;
        if (!(Objects.equals(id, this.id))) {
            Arrays.fill(check, false);
        }
        check[0] = true;
        if (id == 0) {
            aes = new AESsettings();
            settings.setAes(aes);
            settings.setRes(null);
        } else if (id == 1) {
            res = new RESsettings();
            settings.setRes(res);
            settings.setAes(null);
        }
        this.id = id;
    }
    public void setencrypton() {
        setencryption(true);
    }
    public void setencryption(boolean enkripton) {
        settings.setCheekEncryption(enkripton);
        check[2] = true;
    }
    public void setcheckstring() {
        setcheckstring(true);

    }
    public void setcheckstring(boolean stringcheck) {
        settings.setCheekString(stringcheck);
        check[1] = true;
    }
    public void setnumrepet(int repetison) {
        short shortnumrep = (short) repetison;
        setnumrepet(shortnumrep);
    }
    public void setnumrepet(short repetison) {
        if (repetison < 0)
            repetison *= -1;
        settings.setNumOFrepeteson(repetison);
    }


    // ---------------------- AES ----------------------------------------------------------------------------------- //
    public void setAesiv(byte aesiv) {
        if (aesiv == 0)
            setAesiv(new byte[]{-80, 65, 125, 85, -124, 70, 90, 52, -118, 116, 107, -12, -109, 89, -72, -6});
        if (aesiv == 1)
            setAesiv(new byte[]{41, -75, 67, -52, 107, 93, -55, 104, -9, 124, 49, 31, -34, 65, 18, 92});
        if (aesiv == 2)
            setAesiv(new byte[]{-112, -9, 86, -63, 78, -64, -91, 53, -47, -108, -27, 27, -105, 99, -60, 89});
    }
    public void setAesiv(byte[] aesiv){
        aes.setIv(aesiv);
        check[5] = true;
    }
    public boolean setAeskey(File keyfile) {
        SecretKey key = null;
        try {
            ObjectInputStream temkeyred = new ObjectInputStream(new BufferedInputStream(new FileInputStream(keyfile)));
            key = (SecretKey) temkeyred.readObject();
            temkeyred.close();
        } catch (IOException | ClassNotFoundException e) {
            key = null;
            throw new RuntimeException(e);
        }
        if (key != null)
            return setAeskey(key);
        return false;
    }
    public boolean setAeskey(SecretKey key) {
        aes.setKey(key);
        return true;
    }

    // -------------------------------------------------------------------------------------------------------------- //
    private void setfiles() {
        if (id == 0)
            aes.setFiles(settingsfile);
        if (id == 1)
            res.setFiles(settingsfile);
    }
    public void setfiles(File in, File ou) {
        settingsfile = new Settingsfile(in, ou);
        setfiles();
    }
    public void setfileIN(File in) {
        if (settingsfile == null)
            settingsfile = new Settingsfile();
        settingsfile.setIN(in);
        setfiles();
    }
    public void setfileOU(File ou) {
        if (settingsfile == null)
            settingsfile = new Settingsfile();
        settingsfile.setOU(ou);
        setfiles();
    }

    public void setfileOU() {
        if (settingsfile == null)
            settingsfile = new Settingsfile();
        settingsfile.setOuToNull();
        setfiles();
    }

    // ---------------------- RES ----------------------------------------------------------------------------------- //



    // -------------------------------------------------------------------------------------------------------------- //
}
