package modules;

import modules.crypterings.Cryptaes;
import modules.crypterings.Cryptrsa;
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
import java.time.LocalTime;

public class Crypteringsmodule {
    private final Object[] module = new Object[9];
    /* Visar vilka värden som hamnav vart beroende på vilken krypterings metod som används
     och om det är en text eller fill.
     module[  ]
    ------- globala -------------------------------------
    0 = id
    1 = String or File
    2 = Encrypt or Decrypt
    3 = If the shall stor the first output
    4 = num of repetitions
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
    -------- rsa -----------------------------------------
        5 = null
        6 = keyholder
    --------- String ------------------------------------
            7 = Playne text
    ---------- File ---------------------------------------
            7 = in put File
            8 = ou put File
    ----------------------------------------------------------
    -------- hybrid -----------------------------------------
    (2 = File)
        5 = is the aes key in the file
        6 = if the rsa key vile bi stored in the file
        7 = aes and rsa settings
    (-------- aes and res settings --------------
    0 = iv
    1 = aes key (if its not in the file)
    2 = in put File
    3 = ou put File
    4 = ras keyholder
    ---------------------------------------------)
    ----------------------------------------------------------



     */
    private final String[] filestrings = new String[3];

    private final Object[] hybridmodule = new Object[5];
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
        if (module[0].equals((byte) 0)) {
            AESset(settings.getAes());
        } else if (module[0].equals((byte) 1)) {
            RSAset(settings.getRsa());
        } else if (module[0].equals((byte) 2)) {
            Hybridset(settings.getHybrid(), settings.getAes(), settings.getRsa());
        }
        // titar efter vilken krypterings model som ska användas
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
        if (!file.exists())
            file = new File(filestrings[0]);
        // om inte så görden en ny fil och försöker med filens absoluta väg ((men det kanske inte gör någonting))
        module[7] = file;
        // setter in filen och gör samma sak med filen som den ska skriva till
        file = fileseter.getOu();
        if (!((boolean) module[3]) && filestrings[1] == null && file == null) {
            module[8] = null;
        } else if (file == null && filestrings[1] == null) {
            throw new RuntimeException(new NullPointerException());
        } else {
            if (file == null) {
                file = new File(filestrings[1]);
                if (!file.isFile())
                    throw new RuntimeException("Platsen som fillen pekar på är inte en fill");
            } else if (!file.exists()) {
                file = new File(filestrings[1]);
                if (!file.isFile())
                    throw new RuntimeException("Platsen som fillen pekar på är inte en fill");
            }
            module[8] = file;
        }
        // krypteringen behöver en ut fill eftersom att den skriver inte över orginala fillen utan krypterar/dekrypterar
        // inehålet av fillen (som en lång string)
    }

    private void RSAset(RSAsettings rsa) {
        File keyfile = rsa.getKeyfile();
        if (!keyfile.exists()) {
            keyfile = new File(rsa.getKeyfilepath());
        }// hämtar filen som kykeln till RSA krypteringen

        getpubOrpri(keyfile, rsa.isPriORpub());
        // en funktion som hämtar och bygger i-hop nyckeln

        if ((boolean) module[1]) {
            module[7] = rsa.getMesige();
        } else { // titar om det är en string eller fil
            Settingsfile fileseter = rsa.getFileInOu();
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
            RsaKeyholder keyholder;
            if (priOrpub) {
                keyholder = new RsaKeyholder(keyFactory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent)));
            } else {
                keyholder = new RsaKeyholder(keyFactory.generatePublic(new RSAPublicKeySpec(modulus, exponent)));
            }
            module[6] = keyholder;
        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    private void Hybridset(HYBRIDsettings hybrid, AESsettings aes, RSAsettings rsa) {
        module[5] = hybrid.isKeyinfile();
        module[6] = hybrid.isStorkeyInFile();
        hybridmodule[0] = aes.getIv();
/*
        if (!((boolean) module[5])) {
            hybridmodule[1] =;
        } else
            hybridmodule[1] =;

        hybridmodule[2] =;
        hybridmodule[3] =;
        hybridmodule[4] =;
        module[7] = hybridmodule;
*/
    }

    public void setLooger(FileWriter looger) {
        this.looger = looger;
    } // för över loog fillens skrivare.

    public void start() { // Starten på krypteringen. Funktionerna med en s är för String och f är för File.
        JOptionPane.showMessageDialog(null,"Vänta tills Profiler-programmet har hittat java-programmet.");
        if (module[0].equals((byte) 0)) {
            if ((boolean) module[1]) { // 1 = String or File
                AESs();
            } else {
                AESf();
            }
        } else if (module[0].equals((byte) 1)) {
            if ((boolean) module[1]) {
                RSAs();
            } else {
                RSAf();
            }
        } else if (module[0].equals(2)) {
            HYBRID();
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
            looger.write("keypteringen slutate \n" + System.nanoTime());
            looger.flush();
            looger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (manulesnapshotAlurt)
            JOptionPane.showMessageDialog(null, "Krypteringen slutade, ta snap");
            // Är här för att seja till att programet är färdigt och att ta en sapshot i programet som används
            // för att mäta programet (visa gör det inte automatiskt)
        System.exit(0);
    }
    private void AESf() {
        short nMAX = (short) module[4];
        if (nMAX < 1) {
            System.err.println("Error: nMAX är mindre än 1: " + nMAX);
            try {
                looger.write("\nError: ingen kryptering händer nMAX är mindre än 1\n" + nMAX + '\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            looger.write("File:\n" + filestrings[0] + "\nTiden aes krypteringen började: \n" +
                    LocalTime.now() +
                    "\nDen krypterade filen är har:\n" + filestrings[1] + "\n");
            if (nMAX > 1) {
                looger.write("krypteringen kördes: " + nMAX + " gånger\n");
            }
            looger.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean storchek = (boolean) module[3];
        boolean f = true;
        for (int n = 0; n < nMAX; n++) {
            Cryptaes.Filebufercry((boolean) module[2], storchek, (IvParameterSpec) module[5], (SecretKey) module[6], (File) module[7], (File) module[8]);
            System.out.print(n + ',');
            if (f) {
                try {
                    looger.write("Första krypteringen slutade:\n" + LocalTime.now() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                storchek = false;
                f = false;
            }
        }
        try {
            looger.write("Krypteringen slutade: \n" + LocalTime.now());
            looger.flush();
            looger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (manulesnapshotAlurt)
            JOptionPane.showMessageDialog(null, "Krypteringen slutade, ta snap");
        System.exit(0);
    }

    private void RSAs() {
        try {
            looger.write("string:\n" + module[5] + "\ntiden rsa crypteringen började: \n" + System.nanoTime() +
                    "\nden krypterade strengen blev:\n");
            looger.flush();

            String out;
            out = Cryptrsa.Stringcry((boolean) module[2], (RsaKeyholder) module[6], (String) module[7]);

            looger.write(out + '\n');
            looger.write("keypteringen slutate \n" + System.nanoTime());
            looger.flush();
            looger.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (manulesnapshotAlurt)
            JOptionPane.showMessageDialog(null, "Krypteringen slutade, ta snap");
        System.exit(0);
    }
    private void RSAf() {
        short nMAX  = (short) module[4];
        if (nMAX < 1) {
            System.err.println("Error: nMAX är mindre än 1: " + nMAX);
            try {
                looger.write("\nError: ingen kryptering händer nMAX är mindre än 1\n" + nMAX + '\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            looger.write("File: " + filestrings[0] + "\nTiden rsa krypteringen började: \n" +
                    LocalTime.now() +
                    "\nDen krypterade filen är har:\n" + filestrings[1]);
            if (nMAX > 1) {
                looger.write("krypteringen kördes: " + nMAX + " gånger\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        boolean storchek = (boolean) module[3];
        boolean f = true;
        short i = 1;
        for (int n = 0; n < nMAX; n++) {
            Cryptrsa.Filebufercry((boolean) module[2], storchek,(RsaKeyholder) module[6], (File) module[7], (File) module[8]);
            if (i == 1000) {
                System.out.print(n + 1 + ",");
                i = 0;
            }
            if (f) {
                try {
                    looger.write("Första krypteringen slutade:\n" + LocalTime.now() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                storchek = false;
                f = false;
            }
            i++;
        }
        try {
            looger.write("Krypteringen slutade: \n" + LocalTime.now());
            looger.flush();
            looger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (manulesnapshotAlurt)
            JOptionPane.showMessageDialog(null, "Krypteringen slutade, ta snap");
        System.exit(0);
    }

    private void HYBRID() {
        short nMAX = (short) module[4];
        if (nMAX < 1) {
            System.err.println("Error: nMAX är mindre än 1: " + nMAX);
            try {
                looger.write("\nError: ingen kryptering händer nMAX är mindre än 1\n" + nMAX + '\n');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            looger.write("Hybrid krypteringen startade: " + LocalTime.now()
                        + "\nFiledn som ska krypteras är: " + filestrings[0]
                        + "\nDen krypterade filen är: " + filestrings[1] + '\n'
                        );
            if(!(boolean) module[5]) {
                looger.write("aes nyckeln fins här: " + filestrings[2] + '\n');
            } else
                looger.write("aes nyckeln  fins redan i filen\n");
            if (nMAX > 1)
                looger.write("krypteringen kördes " + nMAX + "gånger");
            looger.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean storchek = (boolean) module[3];
        boolean f = true;
        for (int n = 0; n < nMAX; n++) {
            if (f) {
                try {
                    looger.write("Första krypteringen slutade:\n" + LocalTime.now() + "\n");
                    looger.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                storchek = false;
                f = false;
            }
        }
        if (manulesnapshotAlurt)
            JOptionPane.showMessageDialog(null, "Krypteringen slutade, ta snap");
        System.exit(0);
    }
}
