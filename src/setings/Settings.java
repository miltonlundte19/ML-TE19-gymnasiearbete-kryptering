package setings;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

public class Settings implements Serializable {
    private byte id;
    private Boolean stringORfile;
    private Boolean encryptORdecrypt;
    private SecretKey key;
    private byte[] iv;
    private String plainText;
    private File in;
    private String fileinstring;
    private File ou;
    private String fileoustring;

    public Settings() {
        key = null;
        iv = null;
        plainText = null;
        in = null;
        ou = null;
        fileinstring = null;
        fileoustring = null;
    }

    public Settings(byte[] iv) {
        this.iv = iv;
        key = null;
        plainText = null;
        in = null;
        ou = null;
        fileinstring = null;
        fileoustring = null;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public void setStringORfile(Boolean stringORfile) {
        this.stringORfile = stringORfile;
    }

    public void setEncryptORdecrypt(Boolean encryptORdecrypt) {
        this.encryptORdecrypt = encryptORdecrypt;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public void setIn(File in) {
        this.in = in;
    }

    public void setFileinstring(String fileinstring) {
        this.fileinstring = fileinstring;
    }

    public void setOu(File ou) {
        this.ou = ou;
    }

    public void setFileoustring(String fileoustring) {
        this.fileoustring = fileoustring;
    }


    public byte getId() {
        return id;
    }

    public Boolean getStringORfile() {
        return stringORfile;
    }

    public Boolean getEncryptORdecrypt() {
        return encryptORdecrypt;
    }

    public SecretKey getKey() {
        return key;
    }

    public byte[] getIv() {
        return iv;
    }

    public String getPlainText() {
        return plainText;
    }

    public File getIn() {
        return in;
    }

    public String getFileinstring() {
        return fileinstring;
    }

    public File getOu() {
        return ou;
    }

    public String getFileoustring() {
        return fileoustring;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "id=" + id +
                ", stringORfile=" + stringORfile +
                ", encryptORdecrypt=" + encryptORdecrypt +
                ", key=" + key +
                ", iv=" + Arrays.toString(iv) +
                ", plainText='" + plainText + '\'' +
                ", in=" + in +
                ", fileinstring='" + fileinstring + '\'' +
                ", ou=" + ou +
                ", fileoustring='" + fileoustring + '\'' +
                '}';
    }
}
