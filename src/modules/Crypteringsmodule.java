package modules;

import modules.crypterings.Cryptaes;
import modules.crypterings.Cryptres;

import setings.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class Crypteringsmodule {
    private Object[] module = new Object[9];
    /* Visar vilka värden som hamnav vart beroende på vilken krypterings metod som används
     och om det är en text eller fill.
     module[  ]
    ------- globala -------------------------------------
    0 = id
    1 = String or File
    2 = Encrypt or Decrypt
    3 = If the shal stor the first output
    4 = num of repetisons
    ----------------------------------------------------------
    -------- aes -----------------------------------------
        5 = IV
        6 = key
    --------- String ------------------------------------
            7 = Playne text
    ---------- File ---------------------------------------
            7 = in put File
            8 = ou put File
    ----------------------------------------------------------
    -------- ras -----------------------------------------
        5 = null
        6 = keyholder
    --------- String ------------------------------------
            7 = Playne text
    ---------- File ---------------------------------------
            7 = in put File
            8 = ou put File
    ----------------------------------------------------------

     */
    private String[] filestrings = new String[2];
    private final boolean manulesnapshotAlurt;
    private FileWriter looger;

    // startar kass för att få in värderna på rätt stäle.
    public Crypteringsmodule(Settings settings) {
        module[0] = settings.getId();
        module[1] = settings.getStringORfile();
        module[2] = settings.getEncryptORdecrypt();
        module[3] = settings.isStorTOfile();
        module[4] = settings.getNumOFrepeteson();
        manulesnapshotAlurt = settings.getManulesnapshot();
        // hämtar datan från settings till en objekt array så att det ska vara lättare att läga in datan.

        if (module[0].equals((byte) 1)) {
            AESset(settings.getAes());
        } else if (module[0].equals((byte) 2)) {
            RESset(settings.getRes());
        } // titar efter vilken krypterings model som ska användas
    }

    private void AESset(AESsettings aes) {
        module[5] = new IvParameterSpec(aes.getIv());
        module[6] = aes.getKey();
        if ((boolean) module[1]) {
            module[7] = aes.getPlainText();
        } else { // titar om det är en string eller fil
            Settingsfile fileseter = aes.getFileInOu();
            setFilemod(fileseter);
        }
    }

    private void setFilemod(Settingsfile fileseter) {
        filestrings[0] = fileseter.getFileinstring();
        filestrings[1] = fileseter.getFileoustring();
        // Hämtar filernas plats i string format
        File file = fileseter.getIn();// titar om den kan hämta filen från ett fill objekt
        if (!file.exists()) {
            file = new File(filestrings[0]);
        } // om inte så görden en ny fil och försöker med filens absoluta väg ((men det kanske inte gör någonting))
        module[5] = file;
        // setter in filen och gör samma sak med filen som den ska skriva till
        file = fileseter.getOu();
        if (module[3] == null && filestrings[1] == null && file == null) {
            module[8] = null;
        } else if (file == null && filestrings[1] == null) {
            NullPointerException e = new NullPointerException();
            throw new RuntimeException(e);
        } else {
            if (file == null) {
                file = new File(filestrings[1]);
                if (!file.isFile()) {
                    throw new RuntimeException("Platsen som fillen pekar på är inte en fill");
                }
            } else if (!file.exists()) {
                file = new File(filestrings[1]);
                if (!file.isFile()) {
                    throw new RuntimeException("Platsen som fillen pekar på är inte en fill");
                }
            }
            module[8] = file;
        }
        // krypteringen behöver en ut fill eftersom att den skriver inte över orginala fillen utan krypterar/dekrypterar
        // inehålet av fillen (som en lång string)
    }

    private void RESset(RESsettings res) {
        File keyfile = res.getKeyfile();
        if (!keyfile.exists()) {
            keyfile = new File(res.getKeyfilepath());
        }// hämtar filen som kykeln till RES krypteringen

        getpubOrpri(keyfile, res.isPriORpub());
        // en funktion som hämtar och byger i-hop nykeln

        if ((boolean) module[1]) {
            module[7] = res.getMesige();
        } else { // titar om det är en string eller fil
            Settingsfile fileseter = res.getFileInOu();
            setFilemod(fileseter);
        }
    }

    private void getpubOrpri(File keyfile, boolean priOrpub) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(keyfile)));
            // använder en ObjectInputStream eftersom att BigInteger inte är Serializable
            BigInteger modulus = (BigInteger) inputStream.readObject();
            BigInteger exponent = (BigInteger) inputStream.readObject();
            inputStream.close();
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            ResKeyholder keyholder;
            if (priOrpub) {
                keyholder = new ResKeyholder(keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent)));
            } else {
                keyholder = new ResKeyholder(keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent)));
            }
            module[6] = keyholder;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setLooger(FileWriter looger) {
        this.looger = looger;
    } // för över loog fillens skrivare.

    public void start() { // Starten på krypteringen. Funktionerna med en s är för String och f är för File.
        try {
            System.out.println("början på pausen");
            Thread.sleep(900);
            System.out.println("slut på pausen");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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

    // upstarten för AES krypteringen med några skilnader melan dem som mest hanlar om skriva ner resultatet.
    private void AESs() {
        try {
            looger.write("string:\n" + module[7] + "\ntiden aes crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            // skriver ner vad stringen var och tiden det tog att sätta upp allt
            looger.flush();
            String out;

            out = Cryptaes.Stringcry((Boolean) module[2], (IvParameterSpec) module[5], (SecretKey) module[6], (String) module[7]);
            // Här krypteraren och får utt den krypterade strengen som sedan blir nedsriven.

            looger.write(out + '\n');
            if (manulesnapshotAlurt) {
                looger.write("keypteringen slutate \n" + System.nanoTime());
            }
            looger.flush();
            looger.close();
            if (manulesnapshotAlurt) {
                JOptionPane.showMessageDialog(null, "keypteringen slutate, ta snap");
                // Är här för att seja till att programet är färdigt och att ta en sapshot i programet som används
                // för att mäta programet (visa gör det inte automatiskt)
            }
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
            if (!manulesnapshotAlurt) {
                looger.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        boolean storchek = (boolean) module[3];
        boolean f = (boolean) module[3];
        byte nMAX = (byte) module[4];

        for (int n = 0; n < nMAX; n++) {

            Cryptaes.Filebufercry((boolean) module[2], storchek, (IvParameterSpec) module[5], (SecretKey) module[6], (File) module[7], (File) module[8]);

            if (f) {
                if (manulesnapshotAlurt) {
                    try {
                        looger.write("första krypteringen slutade: \n" + System.nanoTime());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                storchek = false;
                f = false;
            }
        }

        if (manulesnapshotAlurt) {
            try {
                looger.write("keypteringen slutate: \n" + System.nanoTime());
                looger.flush();
                looger.close();
                JOptionPane.showMessageDialog(null, "keypteringen slutate, ta snap");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        System.exit(0);
    }

    private void RESs() {
        try {
            looger.write("string:\n" + module[5] + "\ntiden res crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();

            String out;
            out = Cryptres.Stringcry((boolean) module[2], (ResKeyholder) module[6], (String) module[7]);

            looger.write(out + '\n');
            if (manulesnapshotAlurt) {
                looger.write("keypteringen slutate \n" + System.nanoTime());
            }
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
            if (!manulesnapshotAlurt) {
                looger.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Cryptres.Filebufercry((boolean) module[2], (ResKeyholder) module[6], (File) module[7], (File) module[8]);

        if (manulesnapshotAlurt) {
            try {
                looger.write("keypteringen slutate \n" + System.nanoTime());
                looger.flush();
                looger.close();
                JOptionPane.showMessageDialog(null, "keypteringen slutate, ta snap");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        System.exit(0);
    }
}
