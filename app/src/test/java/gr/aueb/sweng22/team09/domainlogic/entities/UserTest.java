package gr.aueb.sweng22.team09.domainlogic.entities;

import junit.framework.TestCase;

import org.joda.money.Money;
import org.junit.Assert;

import java.util.NoSuchElementException;

public class UserTest extends TestCase {
    private User testUser;

    public static User generateTestUser() {
        String username = "arsto";
        String password = "123456789";
        String firstName = "Panos";
        String lastName = "Lamproulos";
        String email = "jsdev@yahoo.com";
        String telephone = "6987123416";
        String city = "Athens";
        String address = "Artemis 9 Metamorphosi";

        User.Builder builder = new User.Builder(username);
        return builder.password(password).firstName(firstName).lastName(lastName).email(email).
                telephone(telephone).city(city).address(address).buildNew();
    }

    public static User generateTestUser2() {
        String username = "batraxos_galinis";
        String password = "mou_leipoun_oi";
        String firstName = "babylonia";
        String lastName = "2000 bc";
        String email = "voitheia@yahoo.com";
        String telephone = "698349419";
        String city = "Athens";
        String address = "Iasonos 21 Athens";

        User.Builder builder = new User.Builder(username);
        return builder.password(password).firstName(firstName).lastName(lastName).email(email).
                telephone(telephone).city(city).address(address).buildNew();
    }

    @Override
    public void setUp() {
        testUser = generateTestUser();
    }

    /**
     * Test the constructor for an already defined user by
     * comparing the fields of the already created test user to the
     * new instance using the second "database" constructor.
     */
    public void testConstructor() {
        User oldUser = generateTestUser();
        User.Builder builder = new User.Builder(oldUser.getUsername());
        User newUser = builder.password(oldUser.getPassword()).firstName(oldUser.getFirstName())
                .lastName(oldUser.getLastName()).email(oldUser.getEmail()).
                telephone(oldUser.getTelephone()).city(oldUser.getCity()).
                address(oldUser.getAddress()).buildExisting();

        Assert.assertEquals(oldUser.getUsername(), newUser.getUsername());
        Assert.assertEquals(oldUser.getPassword(), newUser.getPassword());
        Assert.assertEquals(oldUser.getFirstName(), newUser.getFirstName());
        Assert.assertEquals(oldUser.getLastName(), newUser.getLastName());
        Assert.assertEquals(oldUser.getTelephone(), newUser.getTelephone());
        Assert.assertEquals(oldUser.getCity(), newUser.getCity());
        Assert.assertEquals(oldUser.getAddress(), newUser.getAddress());
        Assert.assertEquals(oldUser.getWallet(), newUser.getWallet());
    }

    /**
     * Tests if an invalid password is caught after using the builder.
     */
    public void testBuilderPassword() {
        User.Builder builder = new User.Builder("a").email("test@email.com").password("");
        Assert.assertThrows(User.PasswordInvalidException.class, builder::buildNew);
        Assert.assertThrows(User.PasswordInvalidException.class, builder::buildExisting);
    }

    /**
     * Tests if an invalid email is caught after using the builder.
     */
    public void testBuilderEmail() {
        User.Builder builder = new User.Builder("a").email("@email.com").password("12345678");
        Assert.assertThrows(User.EmailInvalidException.class, builder::buildNew);
        Assert.assertThrows(User.EmailInvalidException.class, builder::buildExisting);
    }

    /**
     * Tests the valid formats of an email address.
     */
    public void testSetEmail() {
        Assert.assertThrows(IllegalArgumentException.class, ()
                -> testUser.setEmail("wrongformatlol"));

        Assert.assertThrows(IllegalArgumentException.class, ()
                -> testUser.setEmail("wrong@format@lol.com"));

        Assert.assertThrows(IllegalArgumentException.class, ()
                -> testUser.setEmail("@wrongformatlol"));

        String email = "test@gmail.com";
        testUser.setEmail(email);
        Assert.assertEquals(email, testUser.getEmail());
    }


    /**
     * Tests if the password's length limits are being checked properly.
     */
    public void testSetPasswordLength() {
        // too small
        Assert.assertThrows(IllegalArgumentException.class, () -> testUser.setPassword("11111"));

        //too big
        Assert.assertThrows(IllegalArgumentException.class, () ->
                testUser.setPassword("111118888888888888888888888"));

        //correct
        testUser.setPassword("123456789");
    }

    /**
     * Tests if the first name is changed properly.
     */
    public void testSetFirstName() {
        String firstName = "Ponos";

        testUser.setFirstName(firstName);

        Assert.assertEquals(firstName, testUser.getFirstName());
    }

    /**
     * Tests if the last name is changed properly.
     */
    public void testSetLastName() {
        String lastName = "Batraxopoulos";

        testUser.setLastName(lastName);

        Assert.assertEquals(lastName, testUser.getLastName());
    }

    /**
     * Tests if the telephone is changed properly.
     */
    public void testSetTelephone() {
        String phone = "2105623987";

        testUser.setTelephone(phone);

        Assert.assertEquals(phone, testUser.getTelephone());
    }

    /**
     * Tests if the city is changed properly.
     */
    public void testSetCity() {
        String newCity = "Kilkis";

        testUser.setCity(newCity);

        Assert.assertEquals(newCity, testUser.getCity());
    }

    /**
     * Tests if the address is changed properly.
     */
    public void testSetAddress() {
        String newAddress = "Kilkis 140";

        testUser.setAddress(newAddress);

        Assert.assertEquals(newAddress, testUser.getAddress());
    }

    /**
     * Tests if money can be correctly added to the user's wallet
     * on the user's currency.
     */
    public void testAddMoneySameCurrency() {
        Money toBeAdded = Money.parse("USD 15");
        testUser.addMoney(toBeAdded);
        Assert.assertEquals(toBeAdded, testUser.getWallet());
    }

    /**
     * Tests if money can be correctly added to the user's wallet
     * on a foreign currency.
     */
    public void testAddMoneyForeignCurrency() {
        Money toBeAdded = Money.parse("EUR 7");
        testUser.addMoney(toBeAdded);
        // we assume conversion rate of 1-1
        Assert.assertEquals(Money.parse("USD 7"), testUser.getWallet());
    }

    /**
     * Tests if a job interest can be added to the user.
     */
    public void testAddJobInterest() {
        Job job = JobTest.generateTestJob();
        testUser.addJobInterest(job);
        Assert.assertTrue(testUser.getInterestingJobs().contains(job));
    }

    /**
     * Tests if a job interest is indeed removed from the user, and
     * if the class detects the removal of a non-existed job interest.
     */
    public void testRemoveJobInterest() {
        Job job = JobTest.generateTestJob();
        Assert.assertThrows(NoSuchElementException.class, () -> testUser.removeJobInterest(job));

        testUser.addJobInterest(job);
        testUser.removeJobInterest(job);
        Assert.assertFalse(testUser.getInterestingJobs().contains(job));

    }

    /**
     * Tests if a rating is indeed added to the user.
     */
    public void testAddRating() {
        Rating rating = RatingTest.generateTestRating();
        testUser.addRating(rating);
        Assert.assertTrue(testUser.getRatings().contains(rating));
    }


}
