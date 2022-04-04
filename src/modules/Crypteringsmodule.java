package modules;

import modules.crypterings.Cryptaes;
import setings.Settings;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Crypteringsmodule {
    private Object[] module = new Object[6];
    /* module[  ]
    0 = id
    1 = String or File
    2 = Encrypt or Decrypt
    3 = IV
    4 = key
    -----------------
    5 = Playne text
    6 = Encrypted text
    ------------------
    5 = in put File
    6 = ou put File
    ----------------
     */
    private String[] filestrings = new String[2];
    private Settings settings;
    private FileWriter looger;
    private byte id; //
    private String masige; //
    private IvParameterSpec iv; //
    private SecretKey key; //
    private File in, ou; //
    private boolean enORde; //
    public Crypteringsmodule(Settings settings) {
        module[0] = settings.getId();
        module[1] = settings.getStringORfile();
        module[2] = settings.getEncryptORdecrypt();
        if (module[0].equals((byte) 1)) {
            module[3] = settings.getIv();
            module[4] = settings.getKey();
            if ((boolean) module[1]) {
                module[5] = settings.getPlainText();
            } else {
                filestrings[0] = settings.getFileinstring();
                filestrings[1] = settings.getFileoustring();
                File file = settings.getIn();
                if (!file.exists()) {
                    file = new File(filestrings[0]);
                }
                module[5] = file;
                file = settings.getOu();
                if (!file.exists()) {
                    file = new File(filestrings[1]);
                }
                module[6] = file;
            }
        }
        // --------------
        this.id = settings.getId();
        this.enORde = settings.getEncryptORdecrypt();
        this.masige = settings.getPlainText();
        this.iv = settings.getIv();
        this.key = settings.getKey();
        this.in = settings.getIn();
        this.ou = settings.getOu();
        if (!in.exists()) {
            in = new File(settings.getFileinstring());
        }
        if (!ou.exists()) {
            ou = new File(settings.getFileoustring());
        }
        // ----------------
        this.settings = settings;
    }

    public void setLooger(FileWriter looger) {
        this.looger = looger;
    }

    public void startt() {
        if (id == 1) {
            if (masige != null) {
                try {
                    looger.write("string:" + masige + "\ntiden crypteringen började: \n" + System.nanoTime() +
                            "\nden krypterade strengen blev:\n");
                    String encryptmesig;
                    if (enORde) {
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
            } else {
                try {
                    looger.write("File: " + settings.getFileinstring() + "\ntiden crypteringen började: \n" + System.nanoTime() +
                            "\nden krypterade filen är har:\n" + settings.getFileoustring());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (enORde) {
                    Cryptaes.Filebufercry((byte) 1, iv, key, in, ou);
                } else {
                    Cryptaes.Filebufercry((byte) 2, iv, key, in, ou);
                }
            }
        } //
    }

    public void start() {
        if (module[0].equals((byte) 1)) {
            if ((boolean) module[1]) {
                try {
                    looger.write("string:" + module[5] + "\ntiden crypteringen började: \n" + System.nanoTime() +
                            "\nden krypterade strengen blev:\n");
                    if ((boolean) module[2]) {
                        module[6] = Cryptaes.Stringcry((IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);
                    } else {
                        module[6] = Cryptaes.Stringdicry((IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);
                    }
                    looger.write((String) module[6]);
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            } else {
                try {
                    looger.write("File: " + filestrings[0] + "\ntiden crypteringen började: \n" + System.nanoTime() +
                            "\nden krypterade filen är har:\n" + filestrings[1]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ((boolean) module[2]) {
                    Cryptaes.Filebufercry((byte) 1, (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);
                } else {
                    Cryptaes.Filebufercry((byte) 2, (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);
                }
            }
        } //
    }
}
