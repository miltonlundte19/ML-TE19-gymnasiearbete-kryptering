package setings;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ResKeyholder {
    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;

    private boolean priORpub;

    public ResKeyholder(PrivateKey privateKey) {
        this.privateKey = privateKey;
        priORpub = true;
    }

    public ResKeyholder(PublicKey publicKey) {
        this.publicKey = publicKey;
        priORpub = false;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public boolean isPriORpub() {
        return priORpub;
    }
}
