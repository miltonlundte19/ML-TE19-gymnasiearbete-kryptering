package setings;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Arrays;

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

    @Override
    public String toString() {
        String mesige;
        if (plainText != null) {
            mesige = ", plainText='" + plainText + '\'';
        } else if (fileInOu != null) {
            mesige = ", file-in=" + fileInOu.getFileinstring() +
                    ", file-ou=" + fileInOu.getFileoustring();
        } else {
            mesige = ", Eror - mesige";
        }
        return "AESsettings{" +
                "key=" + key +
                ", iv=" + Arrays.toString(iv) +
                mesige +
                '}';
    }
}
