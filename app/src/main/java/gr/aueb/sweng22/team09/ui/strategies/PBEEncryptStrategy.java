package gr.aueb.sweng22.team09.ui.strategies;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * An encrypt strategy that uses a PBE algorithm without salt.
 *
 * @author Dimitris Tsirmpas
 */
public class PBEEncryptStrategy implements IEncryptStrategy {

    @Override
    public String encrypt(String plainPassword) {
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(plainPassword.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return new String(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ae) {
            throw new RuntimeException("Could not securely encrypt password.", ae);
        }
    }
}
