package setings;

import java.io.File;
import java.io.Serializable;

public class Settingsfile implements Serializable {
    private File in;
    private String fileinstring;
    private File ou;
    private String fileoustring;


    public void setIN(File in) {
        this.in = in;
        fileinstring = in.getAbsolutePath();
    }

    public void setOU(File ou) {
        this.ou = ou;
        fileoustring = ou.getAbsolutePath();
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
}
