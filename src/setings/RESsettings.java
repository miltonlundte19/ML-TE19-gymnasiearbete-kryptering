package setings;

import java.io.File;
import java.io.Serializable;

public class RESsettings implements Serializable {

    // en under kalss som har alltför att RES krypteringen ska fungera

    private boolean priORpub; // true = privet
    private File keyfile;
    // Nykeln är sparad i en separrat fill så att men kan spara den på en separat plats och återanvända den.
    // Man kan också hämta publika och privata nykeln från samma fill (typ)
    private String keyfilepath;

    private String mesige;
    // anvender en av desa två klaserna för att spara det som ska krypteras.
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

    public boolean isPriORpub() {
        return priORpub;
    }

    public void setPriORpub(boolean priORpub) {
        this.priORpub = priORpub;
    }

    public Settingsfile getFileInOu() {
        return fileInOu;
    }

    @Override
    public String toString() {
        String PudOrPri;
        if (priORpub) {
            PudOrPri = "Privet";
        } else {
            PudOrPri = "Publik";
        }
        String Mesige;
        if (mesige != null) {
            Mesige = ", mesige='" + mesige + '\'';
        } else if (fileInOu != null) {
            Mesige = ", file-in=" + fileInOu.getFileinstring() +
                    ", file-ou=" + fileInOu.getFileoustring();
        } else {
            Mesige = "Eror - mesige";
        }
        return "RESsettings{" +
                "PriORpub=" + PudOrPri +
                ", keyfilepath='" + keyfilepath + '\'' +
                Mesige +
                '}';
    }
}
