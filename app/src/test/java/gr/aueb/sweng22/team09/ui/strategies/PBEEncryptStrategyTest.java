package gr.aueb.sweng22.team09.ui.strategies;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * Tests if encryption works as intended.
 *
 * @author Dimitris Tsirmpas
 */
public class PBEEncryptStrategyTest extends TestCase {
    private IEncryptStrategy strategy;

    public void setUp() throws Exception {
        super.setUp();
        strategy = new PBEEncryptStrategy();
    }

    /**
     * Tests if a password is being encrypted in any way after being set.
     */
    public void testEncrypt() {
        String password = "123456789";
        String encrypted = strategy.encrypt(password);

        // must be encrypted
        Assert.assertNotEquals(password, encrypted);
    }

}