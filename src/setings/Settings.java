package setings;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;

public class Settings implements Serializable {



    private byte id;
    private boolean[] chekOR = new boolean[2];

    private AESsettings aes;
    private RESsettings res;

    public Settings() {
        Arrays.fill(chekOR, false);
    }

    public void setId(byte id) {
        this.id = id;
    }

    public void setChekORstr() {
        chekOR[0] = true;
    }

    public void setChekORen() {
        chekOR[1] = true;
    }

    public void setAes(AESsettings aes) {
        this.aes = aes;
    }

    public void setRes(RESsettings res) {
        this.res = res;
    }

    public byte getId() {
        return id;
    }

    public boolean getStringORfile() {
        return chekOR[0];
    }

    public boolean getEncryptORdecrypt() {
        return chekOR[1];
    }

    public AESsettings getAes() {
        return aes;
    }

    public RESsettings getRes() {
        return res;
    }

}
