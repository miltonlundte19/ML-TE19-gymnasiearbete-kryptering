package setings;

import java.security.PrivateKey;
import java.security.PublicKey;

public class ResKeyholder {
    private PrivateKey privateKey = null;
    private PublicKey publicKey = null;

    public ResKeyholder(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public ResKeyholder(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
