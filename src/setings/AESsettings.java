package setings;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.Serializable;

public class AESsettings implements Serializable  {

    // en under kalss som har all data som AES krypteringen behöver för att fungera

    private SecretKey key;
    private byte[] iv;

    private String plainText;
    // anvender en av desa två klaserna för att spara det som ska krypteras.
    private Settingsfile fileInOu;


    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public byte[] getIv() {
        return iv;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public void setFiles(Settingsfile files) {
        fileInOu = files;
    }

    public Settingsfile getFileInOu() {
        return fileInOu;
    }
}
