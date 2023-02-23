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
    private Object[] module = new Object[7];
    /* Visar vilka värden som hamnav vart beroende på vilken krypterings metod som används
     och om det är en text eller fill.
     module[  ]
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
    private boolean manulesnapshotAlurt;
    private Settings settings;
    private FileWriter looger;

    // startar kass för att få in värderna på rätt stäle.
    public Crypteringsmodule(Settings settings) {
        module[0] = settings.getId();
        module[1] = settings.getStringORfile();
        module[2] = settings.getEncryptORdecrypt();
        manulesnapshotAlurt = settings.getManulesnapshot();
        // hämtar datan från settings till en objekt array så att det ska vara lättare att läga in datan.

        if (module[0].equals((byte) 1)) {
            AESset(settings.getAes());
        } else if (module[0].equals((byte) 2)) {
            RESset(settings.getRes());
        } // titar efter vilken krypterings model som ska användas

        // anvends inte men är fortfarande kvar (komer att ta bort).
        this.settings = settings;
    }

    private void AESset(AESsettings aes) {
        module[3] = new IvParameterSpec(aes.getIv());
        module[4] = aes.getKey();
        if ((boolean) module[1]) {
            module[5] = aes.getPlainText();
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
        if (!file.exists()) {
            file = new File(filestrings[1]);
        }
        module[6] = file;
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
            module[5] = res.getMesige();
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
            module[4] = keyholder;
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
            looger.write("string:\n" + module[5] + "\ntiden aes crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            // skriver ner vad stringen var och tiden det tog att sätta upp allt
            looger.flush();
            String out;

            out = Cryptaes.Stringcry((Boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (String) module[5]);
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

        Cryptaes.Filebufercry((boolean) module[2], (IvParameterSpec) module[3], (SecretKey) module[4], (File) module[5], (File) module[6]);

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

    private void RESs() {
        try {
            looger.write("string:\n" + module[5] + "\ntiden res crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();

            String out;
            out = Cryptres.Stringcry((boolean) module[2], (ResKeyholder) module[4], (String) module[5]);

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
        Cryptres.Filebufercry((boolean) module[2], (ResKeyholder) module[4], (File) module[5], (File) module[6]);

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
