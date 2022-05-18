package modules;

import modules.crypterings.Cryptaes;
import modules.crypterings.Cryptres;
import setings.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

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
    ---------- File ---------------------------------------
            5 = in put File
            6 = ou put File
    ----------------------------------------------------------
    -------- ras -----------------------------------------
        4 = keyholder
    --------- String ------------------------------------
            5 = Playne text
    ---------- File ---------------------------------------
            5 = in put File
            6 = ou put File
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
            AESset(settings.getAes());
        } else if (module[0].equals((byte) 2)) {
            RESset(settings.getRes());
        }
        this.settings = settings;
    }

    private void AESset(AESsettings aes) {
        module[3] = new IvParameterSpec(aes.getIv());
        module[4] = aes.getKey();
        if ((boolean) module[1]) {
            module[5] = aes.getPlainText();
        } else {
            Settingsfile fileseter = aes.getFileInOu();
            setFilemod(fileseter);
        }
    }

    private void setFilemod(Settingsfile fileseter) {
        filestrings[0] = fileseter.getFileinstring();
        filestrings[1] = fileseter.getFileoustring();
        File file = fileseter.getIn();
        if (!file.exists()) {
            file = new File(filestrings[0]);
        }
        module[5] = file;
        file = fileseter.getOu();
        if (!file.exists()) {
            file = new File(filestrings[1]);
        }
        module[6] = file;
    }
    private void RESset(RESsettings res) {
        File keyfile = res.getKeyfile();
        if (!keyfile.exists()) {
            keyfile = new File(res.getKeyfilepath());
        }
        getpubOrpri(keyfile, res.isPriORpub());
        if ((boolean) module[1]) {
            module[5] = res.getMesige();
        } else {
            Settingsfile fileseter = res.getFileInOu();
            setFilemod(fileseter);
        }
    }

    private void getpubOrpri(File keyfile, boolean priOrpub) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(keyfile)));
            BigInteger modulus = (BigInteger) inputStream.readObject();
            BigInteger exponent = (BigInteger) inputStream.readObject();
            inputStream.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            ResKeyholder keyholder;
            if (priOrpub) {
                keyholder = new ResKeyholder(keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent)));
            } else {
                keyholder = new ResKeyholder(keyFactory.generatePublic(new RSAPrivateKeySpec(modulus, exponent)));
            }
            module[4] = keyholder;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
            looger.write("string:\n" + module[5] + "\ntiden aes crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();
            String out;

            out = Cryptaes.Stringcry((Boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);

            looger.write(out + '\n');
            looger.flush();
            looger.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
    private void AESf() {
        try {
            looger.write("File: " + filestrings[0] + "\ntiden aes crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade filen är har:\n" + filestrings[1]);
            looger.flush();
            looger.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        Cryptaes.Filebufercry((boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);

        System.exit(0);
    }

    private void RESs() {
        try {
            looger.write("string:\n" + module[5] + "\ntiden res crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();

            String out;
            out = Cryptres.Stringcry((boolean) module[2], (ResKeyholder) module[4], (String) module[5]);

            looger.write(out + '\n');
            looger.flush();
            looger.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
    private void RESf() {
        try {
            looger.write("File: " + filestrings[0] + "\ntiden res crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade filen är har:\n" + filestrings[1]);
            looger.flush();
            looger.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Cryptres.Filebufercry((boolean) module[2], (ResKeyholder) module[4], (File) module[5], (File) module[6]);

        System.exit(0);
    }
}
