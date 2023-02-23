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
    private byte numOFrepeteson = 1;
    // varabel som sejer hur många gånger krypteringen ska repiteras
    // om storTOfile är true så lagras bara första operationen
    private AESsettings aes;
    private RESsettings res;
    // under klaser som har variablerna för sin spesifika kryptering

    private HYDRIDsettings hybrid;

    public Settings() {
        Arrays.fill(chekOR, false);
    }

    // nedan är för att sätta in värden i variabeln
    public void setId(byte id) {
        this.id = id;
    }

    public void setChekORstr() {
        chekOR[0] = true;//=string
        // om det är en string eller fill
    }

    public void setChekORen() {
        chekOR[1] = true;//=enkyption
        // om den ska kryptera eller dekryptera
    }

    public void setAes(AESsettings aes) {
        this.aes = aes;
    }

    public void setRes(RESsettings res) {
        this.res = res;
    }

    public void setHybrid(HYDRIDsettings hybrid) {
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

    public AESsettings getAes() {
        return aes;
    }

    public RESsettings getRes() {
        return res;
    }

    public HYDRIDsettings getHybrid() {
        return hybrid;
    }


    // en variabel som seger som programet ska vänta i slutet.
    public void setManulesnapshot() {
        manulesnapshot = true;
    }

    public boolean getManulesnapshot() {
        return manulesnapshot;
    }

    //
    public boolean isStorTOfile() {
        return storTOfile;
    }

    public void setStorTOfile(boolean storTOfile) {
        this.storTOfile = storTOfile;
    }
    public void setStorTOfile() {
        storTOfile = false;
    }

    public byte getNumOFrepeteson() {
        return numOFrepeteson;
    }

    public void setNumOFrepeteson(byte numOFrepeteson) {
        this.numOFrepeteson = numOFrepeteson;
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
        String mode = "";
        if (aes != null) {
            mode = aes.toString();
        } else if (res != null) {
            mode = res.toString();
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
