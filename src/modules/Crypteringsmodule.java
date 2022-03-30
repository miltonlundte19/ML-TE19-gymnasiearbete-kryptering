package modules;

import modules.crypterings.Cryptaes;
import setings.Settings;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileWriter;
import java.io.IOException;

public class Crypteringsmodule {
    private Settings settings;
    private FileWriter looger;
    private byte[] id;
    private String masige;
    private IvParameterSpec iv;
    private SecretKey key;
    public Crypteringsmodule(Settings settings) {
        this.id = settings.getId();
        this.masige = settings.getPlainText();
        this.iv = settings.getIv();
        this.key = settings.getKey();
        this.settings = settings;
    }

    public void setLooger(FileWriter looger) {
        this.looger = looger;
    }

    public void start() {
        if (id[0] == 1) {
            if (masige != null) {
                try {
                    looger.write("string:" + settings.getPlainText() + "\ntiden crypteringen började: \n" + System.nanoTime() +
                            "\nden krypterade strengen blev:\n");
                    String encryptmesig;
                    if (id[1] == 1) {
                        encryptmesig = Cryptaes.Stringcry(iv, key, masige);
                    } else {
                        encryptmesig = Cryptaes.Stringdicry(iv,key, masige);
                    }
                    looger.write(encryptmesig);
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        }
    }
}
