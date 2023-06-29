package modules;

import setings.AESsettings;
import setings.Settings;
import setings.Settingsfile;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SettingsModel {

    private final Settings settings;
    private AESsettings aes;

    private byte id;

    private Settingsfile settingsfile = new Settingsfile();

    private final boolean[] check = new boolean[10];

    /* ------------------------------------------------------------
    0 = V
    1 = V
    2 = V
    3 = V
    4 = V
    5 = V
    6 = V
    7 = V
    8 = V
    9 = V
    ------------------------------------------------------------ */

    public SettingsModel() {
        settings = new Settings();
        Arrays.fill(check, false);
    }

    public void setId(byte id) {
        if (!(Objects.equals(id, this.id))) {
            Arrays.fill(check, false);
            this.id = id;
        }
        settings.setId(id);
        check[0] = true;
        if (id == 0)
            aes = new AESsettings();
    }

    public void setEncryption(boolean b) {
        settings.setCheekEncryption(b);
        check[2] = true;
    }

    public void setmesigetyp(boolean b) {
        settings.setCheekEncryption(b);
        check[1] = true;
    }
    public void setManusnapshot()  {
        settings.setManulesnapshot();
        check[9] = true;
    }
    public void setManusnapshot(boolean b)  {
        settings.setManulesnapshot(b);
        check[9] = true;
    }

    public void setNumofRepit(int num) {
        short numshort = (short) num;
        if (numshort < 0)
            numshort = 0;
        setNumofRepit(numshort);
    }
    public void setNumofRepit(short num) {
        settings.setNumOFrepeteson(num);
        check[4] = true;
    }

    public boolean isCheckTrue() {
        for (boolean Check : check) {
            if (!Check) {
                return false;
            }
        }
        return true;
    }
    public byte[] getNumofNonCheck() {
        ArrayList<Byte> byteArrayList = new ArrayList<>();
        for (byte i = 0; i < 10; i++) {
            if (!check[i]) {
                byteArrayList.add(i);
            }
        }
        if (byteArrayList.size() > 0) {
            byte[] bytes = new byte[byteArrayList.size()];
            for (int i = 0; i < byteArrayList.size(); i++) {
                bytes[i] = byteArrayList.get(i);
            }
            return bytes;
        }
        return new byte[]{-1};
    }

    public boolean[] getCheck() {
        return check;
    }
    public void updattSettings() {
        if (id == 0) {
            aes.setFiles(settingsfile);
            settings.setAes(aes);
        }
    }
    public Settings getSettings() {
        return settings;
    }
    public AESsettings getAes() {
        return aes;
    }
    public Settingsfile getSettingsfile() {
        return settingsfile;
    }
    // ---------------------- AES ----------------------------------------------------------------------------------- //

    public boolean setAESiv(int aesIv) {
        byte newAesIv = (byte) aesIv;
        if (aesIv >= 0) {
            setAESIv(newAesIv);
            return true;
        }
        return false;
    }

    private static final byte[][] AesIv = new byte[][]{
            {-100, -7, -35, 82, -71, -79, 53, 91, 88, 79, -106, -16, 71, -14, 83, -1},
            {-58, -113, 84, 89, 45, 126, 41, 0, 122, 37, 42, 101, -16, -30, -112, 79},
            {97, -85, -111, 38, -17, 123, 73, 37, 7, 42, -49, 9, 6, 67, -62, 38},
            {-81, -99, 54, -102, -66, -105, -66, -57, 52, 57, 121, 111, 57, 47, 23, 119},
            {62, -89, 125, 126, 6, -111, -84, -93, -114, -65, -58, -83, 70, 10, 0, 76},
            {38, 110, 50, -59, -96, 123, -94, -11, 65, 35, 52, -128, -96, -128, -1, 6},
            {-9, 49, -77, -41, 98, 63, 85, 39, -61, -119, -113, 14, 97, 102, 66, -69},
            {-94, -35, 103, -12, -10, -49, -10, 107, 109, 110, 101, -17, -113, -127, -124, -107},
            {-37, -70, -123, 71, -88, 18, -2, -86, -24, 5, -50, -61, -127, -15, -122, -66},
            {120, -101, 126, 106, 111, -13, 45, 99, -87, 106, 16, 116, 90, -54, 104, 52},
            {-80, 65, 125, 85, -124, 70, 90, 52, -118, 116, 107, -12, -109, 89, -72, -6},
            {41, -75, 67, -52, 107, 93, -55, 104, -9, 124, 49, 31, -34, 65, 18, 92},
            {-112, -9, 86, -63, 78, -64, -91, 53, -47, -108, -27, 27, -105, 99, -60, 89}
    };

    public boolean setAESIv(byte aesIv) {
        byte max = (byte) AesIv.length;
        if ((aesIv >= 0) & (aesIv <= max)) {
            setAESIv(AesIv[aesIv]);
            return true;
        }
        return false;
    }

    public void setAESIv(byte[] aesIv) {
        aes.setIv(aesIv);
        check[5] = true;
    }

    public boolean setAESkey(File file) {
        SecretKey key;
        try {
            ObjectInputStream temkeyred = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            key = (SecretKey) temkeyred.readObject();
            temkeyred.close();
        } catch (IOException | ClassNotFoundException e) {
            return false;
            //throw new RuntimeException(e);
        }
        if (key != null) {
            if (Objects.equals(key.getAlgorithm(), "AES")) {
                setAeskey(key);
                return true;
            }
        }
        return false;
    }

    public void setAeskey(SecretKey key) {
        aes.setKey(key);
        check[6] = true;
    }

    public SecretKey getAESkey() {
        return aes.getKey();
    }

    //---------------------------------------------------------------------------------------------------------------//

    public void setSettingsFile(File in, File ou) {
        settingsfile = new Settingsfile(in,ou);
    }
    public void setSettingsFile(Settingsfile settingsfile) {
        this.settingsfile = settingsfile;
    }

    public void setSettingsFileIn(File in) {
        settingsfile.setIN(in);
        check[7] = true;
    }
    public void setSettingsFileOu(File ou) {
        settingsfile.setOU(ou);
        check[8] = true;
    }
    public void setSettingsFileOuNull() {
        settingsfile.setOuToNull();
        check[8] = true;
    }
    public void setSettingsFileOutStor(boolean b) {
        settings.setStorTOfile(b);
        check[3] = true;
    }
    // ---------------------- RSA ----------------------------------------------------------------------------------- //

    // -------------------------------------------------------------------------------------------------------------- //
}
