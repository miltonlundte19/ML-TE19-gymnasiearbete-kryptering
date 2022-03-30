package setings;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.Serializable;

public class Settings implements Serializable {
    private byte[] id;
    private SecretKey key;
    private IvParameterSpec iv;
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

    public void setId(byte[] id) {
        this.id = id;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public void setIv(IvParameterSpec iv) {
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


    public byte[] getId() {
        return id;
    }

    public SecretKey getKey() {
        return key;
    }

    public IvParameterSpec getIv() {
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

}
