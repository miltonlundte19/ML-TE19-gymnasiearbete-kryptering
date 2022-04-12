package modules;

import modules.crypterings.Cryptaes;
import setings.Settings;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Crypteringsmodule {
    private Object[] module = new Object[7];
    /* module[  ]
    ------- globala -------------------------------------
    0 = id
    1 = String or File
    2 = Encrypt or Decrypt
    ----------------------------------------------------------
    -------- aes -----------------------------------------
        3 = IV
        4 = key
    --------- String ------------------------------------
            5 = Playne text
            6 = Encrypted text
    ---------- File ---------------------------------------
            5 = in put File
            6 = ou put File
    ----------------------------------------------------------
    -------- ras -----------------------------------------
    ----------------------------------------------------------

     */
    private String[] filestrings = new String[2];
    private Settings settings;
    private FileWriter looger;

    public Crypteringsmodule(Settings settings) {
        module[0] = settings.getId();
        module[1] = settings.getStringORfile();
        module[2] = settings.getEncryptORdecrypt();
        if (module[0].equals((byte) 1)) {
            module[3] = new IvParameterSpec(settings.getIv());
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
        this.settings = settings;
    }

    public void setLooger(FileWriter looger) {
        this.looger = looger;
    }

    public void start() {
        if (module[0].equals((byte) 1)) {
            if ((boolean) module[1]) { // 1 = String or File
                AESs();
            } else {
                AESf();
            }
        } else if (module[0].equals((byte) 2)) {
            if ((boolean) module[1]) {
                RESs();
            } else {
                RESf();
            }
        }
    }

    private void AESs() {
        try {
            looger.write("string:\n" + module[5] + "\ntiden crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();
            String out;
            if ((boolean) module[2]) { // 2 = Encrypt or Decrypt
                out = Cryptaes.Stringcry((Boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);
            } else {
                out = Cryptaes.Stringcry((Boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);
            }
            looger.write(out);
            looger.flush();
            looger.close();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    private void AESf() {
        try {
            looger.write("File: " + filestrings[0] + "\ntiden crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade filen är har:\n" + filestrings[1]);
            looger.flush();
            looger.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        if ((boolean) module[2]) {
            Cryptaes.Filebufercry((byte) 1, (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);
        } else {
            Cryptaes.Filebufercry((byte) 2, (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);
        }
        System.exit(0);
    }

    private void RESs() {
        try {
            if ((boolean) module[2]) {

            }   else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void RESf() {
        if ((boolean) module[2]) {

        }   else {

        }
    }
}
