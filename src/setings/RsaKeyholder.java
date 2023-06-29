package setings;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RsaKeyholder {
    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;

    private boolean priORpub;

    public RsaKeyholder(PrivateKey privateKey) {
        this.privateKey = privateKey;
        priORpub = true;
    }

    public RsaKeyholder(PublicKey publicKey) {
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
