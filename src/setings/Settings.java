package setings;

import java.io.Serializable;
import java.util.Arrays;

public class Settings implements Serializable {

/* ()Dena klassen är så att man kan lätt spara ner instälningara till krypteringen.
   Den är uppdelad i en underklass så att dena klassen inte blir så stor med oanvenda variabler
*/
    private byte id;
    // Är till för att spesifiera vilken typ av kryptering det är med en typ indes id
    // id: 1=aes, 2=res
    private boolean[] chekOR = new boolean[2];
    /* Används för att spesifiera i två olika fält:
        om det är en String eller fill som ska användas,
        om den ska kryptera eller dekryptera
    */
    private boolean manulesnapshot;
    // används bara om man ska manuelt spara data vid ()data insamling
    private boolean storTOfile = true;
    // variabel som dikterar om hur utkastet från krypteringen ska hanteras
    // true = "normal operation" fals = "spara inte den krypterade utkastet"/"titar inte om det fins en fill ut"
    private short numOFrepeteson = 1;
    // varabel som sejer hur många gånger krypteringen ska repiteras
    // om storTOfile är true så lagras bara första operationen
    private AESsettings aes;
    private RESsettings res;
    private HYBRIDsettings hybrid;
    // under klaser som har variablerna för sin spesifika kryptering

    public Settings() {
        Arrays.fill(chekOR, false);
    }

    // nedan är för att sätta in värden i variabeln
    public void setId(byte id) {
        this.id = id;
    }
    public void setCheekString() {
        setCheekString(true);
    }
    public void setCheekString(boolean string) {
        chekOR[0] = string;//=string
        // om det är en string eller fill
    }
    public void setCheekEncryption() {
        setCheekEncryption(true);
    }
    public void setCheekEncryption(boolean encryption) {
        chekOR[1] = encryption;//=encryption
        // om den ska kryptera eller dekryptera
    }
    public void setManulesnapshot() {
        manulesnapshot = true;
    }
    public void setManulesnapshot(boolean b) {
        manulesnapshot = b;
    }
    public void setStorTOfile(boolean storTOfile) {
        this.storTOfile = storTOfile;
    }
    public void setStorTOfile() {
        storTOfile = true;
    }
    public void setNumOFrepeteson(short numOFrepeteson) {
        if (numOFrepeteson < 0) {
            numOFrepeteson *= -1;
        }
        this.numOFrepeteson = numOFrepeteson;
    }
    public void setNumOFrepeteson(int numOFrepeteson) {
        short shortnumrep = (short) numOFrepeteson;
        setNumOFrepeteson(shortnumrep);
    }
    public void setAes(AESsettings aes) {
        this.aes = aes;
    }

    public void setRes(RESsettings res) {
        this.res = res;
    }

    public void setHybrid(HYBRIDsettings hybrid) {
        this.hybrid = hybrid;
    }

    // nedan är hämta från variabeln
    public byte getId() {
        return id;
    }
    public boolean getStringORfile() {
        return chekOR[0];
    }
    public boolean getEncryptORdecrypt() {
        return chekOR[1];
    }
    public boolean getManulesnapshot() {
        return manulesnapshot;
    }
    public boolean isStorTOfile() {
        return storTOfile;
    }
    public short getNumOFrepeteson() {
        return numOFrepeteson;
    }

    public AESsettings getAes() {
        return aes;
    }
    public RESsettings getRes() {
        return res;
    }
    public HYBRIDsettings getHybrid() {
        return hybrid;
    }


    @Override
    public String toString() {
        String typ;
        if (chekOR[0]) {
            typ = ", type=String";
        } else {
            typ = ", type=File";
        }
        String modeEnOrDe;
        if (chekOR[1]) {
            modeEnOrDe = ", encryption";
        } else {
            modeEnOrDe = ", decryption";
        }
        String mode = "ERROE set mod";
        if (id == 0) {
            if (aes != null) {
                mode = aes.toString();
            } else {
                mode = "aes ERROR!";
            }
        }
        if (id == 1) {
            if (res != null) {
                mode = res.toString();
            } else {
                mode = "res ERROR!";
            }
        }
        return "Settings{" +
                "id=" + id +
                typ +
                modeEnOrDe +
                ", manulesnapshot=" + manulesnapshot +
                ", output=" + storTOfile +
                ", repetisons=" + numOFrepeteson +
                ", mode=" + mode +
                '}';
    }
}
