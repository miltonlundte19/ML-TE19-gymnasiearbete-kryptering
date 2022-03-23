package modules.crypterings;

import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

public class aescrypteringall {
    // iv genirator
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    } // från https://www.baeldung.com/java-aes-encryption-decryption och (https://www.geeksforgeeks.org/symmetric-encryption-cryptography-in-java/)


}
