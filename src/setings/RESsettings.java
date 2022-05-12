package setings;

import java.io.File;
import java.io.Serializable;

public class RESsettings implements Serializable {

    private String mesige;
    private File keyfile;
    private String keyfilepath;
    private Settingsfile fileInOu;


    public String getMesige() {
        return mesige;
    }

    public void setMesige(String mesige) {
        this.mesige = mesige;
    }

    public File getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(File keyfile) {
        this.keyfile = keyfile;
        this.keyfilepath = keyfile.getAbsolutePath();
    }

    public String getKeyfilepath() {
        return keyfilepath;
    }


    public void setFiles(Settingsfile files) {
        fileInOu = files;
    }
}
