package setings;

public class HYBRIDsettings {
    private boolean keyinfile;
    private boolean storkeyInFile;

    private boolean Aes;
    private boolean Rsa;

    public boolean isKeyinfile() {
        return keyinfile;
    }

    public void setKeyinfile(boolean keyinfile) {
        this.keyinfile = keyinfile;
    }

    public boolean isStorkeyInFile() {
        return storkeyInFile;
    }

    public void setStorkeyInFile(boolean storkeyInFile) {
        this.storkeyInFile = storkeyInFile;
    }

    public boolean isAes() {
        return Aes;
    }

    public void setAes(boolean aes) {
        Aes = aes;
    }

    public boolean isRsa() {
        return Rsa;
    }

    public void setRsa(boolean rsa) {
        Rsa = rsa;
    }
}
