package setings;

import java.io.File;
import java.io.Serializable;

public class Settingsfile implements Serializable {

    // en under klass som har platsen för fillen in och utt,
    // och om File objektet mislykas så fins absoluta platsen för fillen i tekst format.
    private File in;
    private String fileinstring;
    private File ou;
    private String fileoustring;

    public Settingsfile() {
    }
    public Settingsfile(File in) {
        setIN(in);
    }
    public Settingsfile(File in, File ou) {
        setIN(in);
        setOU(ou);
    }


    public void setIN(File in) {
        this.in = in;
        fileinstring = in.getAbsolutePath();
    }

    public void setOU(File ou) {
        this.ou = ou;
        fileoustring = ou.getAbsolutePath();
    }

    public void setOuToNull() {
        ou = null;
        fileoustring = null;
    }

    public File getIn() {
        return in;
    }

    public String getFileinstring() {
        return fileinstring;
    }

    public File getOu() {
        return ou;
    }

    public String getFileoustring() {
        return fileoustring;
    }
    public String toString() {
        return "Settingsfile{" +
                "File in=" + in +
                ", File in String=" + fileinstring +
                ", File out=" + ou +
                ", File out String=" + fileoustring +
                "} ";
    }
}
