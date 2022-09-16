package gr.aueb.sweng22.team09.domainlogic.entities;

import androidx.annotation.NonNull;

import org.joda.money.Money;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;

import gr.aueb.sweng22.team09.ui.strategies.DefaultMoneyAdditionStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IEncryptStrategy;
import gr.aueb.sweng22.team09.ui.strategies.IMoneyAdditionStrategy;
import gr.aueb.sweng22.team09.ui.strategies.PBEEncryptStrategy;


/**
 * An entity holding all the required information about a User.
 *
 * @author Dimitris Tsirmpas
 */
public class User implements Serializable {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 18;
    private static final IMoneyAdditionStrategy moneyAdditionStrategy =
            new DefaultMoneyAdditionStrategy();

    private final String username;
    private String password;
    private String firstName;
    private String lastName;
    private String telephone;
    private String email;
    private String city;
    private String address;
    private Money wallet;

    private final Collection<Rating> ratings;
    /**
     * A collection of jobs the user is interested in fulfilling.
     */
    private final Collection<WorkOffer> activeWorkOffers;

    /**
     * Create a new User instance out of a builder instance.
     * @param builder the builder
     * @throws IllegalArgumentException if any of the fields are falsely formatted or if the
     * username and password violate their respective rules
     */
    public User(Builder builder) throws IllegalArgumentException {
        this.username = builder.username;

        setPassword(builder.password); // don't encrypt the password
        setFirstName(builder.firstName);
        setLastName(builder.lastName);
        setEmail(builder.email);
        setTelephone(builder.telephone);
        setCity(builder.city);
        setAddress(builder.address);

        this.wallet = builder.wallet;
        this.activeWorkOffers = builder.activeWorkOffers;
        this.ratings = builder.ratings;
    }

    /**
     * Get all the ratings for this user.
     * @return a copy of the user's ratings
     */
    public Collection<Rating> getRatings() {
        return new HashSet<>(ratings);
    }

    /**
     * Add a new rating written by another user for this user.
     * @param rating the received rating for this user
     */
    public void addRating(Rating rating){
        ratings.add(rating);
    }

    /**
     * Get all the jobs the user is interested in fulfilling.
     * @return a copy of the list holding the interesting jobs for this user
     * @implNote the method also clears non-pending jobs from the interesting jobs list
     */
    public Collection<Job> getInterestingJobs() {
        // clear expired work offers

        activeWorkOffers.removeIf(n -> n.getStatus() != WorkOffer.Status.PENDING);
        HashSet<Job> jobs = new HashSet<>();

        for(WorkOffer offer : activeWorkOffers) {
            jobs.add(offer.getJob());
        }

        return jobs;
    }

    /**
     * Create a new work offer for this user, add it to the user's interesting jobs
     * and return the work offer entity associated with it.
     * @param job the job the user is interested in fulfilling
     * @return the work offer associated with the action
     */
    public WorkOffer addJobInterest(Job job) {
        WorkOffer newWorkOffer =new WorkOffer(job, this);
        activeWorkOffers.add(newWorkOffer);
        return newWorkOffer;
    }

    /**
     * Marks an interesting job as no longer interesting for the user.
     * @see #addJobInterest(Job)
     *
     * @param job a job the user was interested in
     * @throws NoSuchElementException if the job hasn't been marked as interesting
     */
    public void removeJobInterest(Job job) throws NoSuchElementException {
        WorkOffer workOffer = new WorkOffer(job, this);

        if(!activeWorkOffers.contains(workOffer)){
            throw new NoSuchElementException(String.format(
                    "The job %s had not been marked as interesting", job));
        }

        activeWorkOffers.remove(workOffer);
    }

    /**
     * Return the user name of the User.
     * @return the user name
     */
    @NonNull
    public String getUsername() {
        return username;
    }

    /**
     * Get the ENCRYPTED password of the user to be compared with.
     * @return a string representing the encrypted user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Registers a new password for the user.
     *
     * @param password the new password, unencrypted
     * @throws IllegalArgumentException if the password is not between 8 and 16 characters long
     */
    public void setPassword(String password) {
        if(!User.verifyPassword(password))
            throw new PasswordInvalidException("Password must be between 8 and 16 characters");

        this.password = password;
    }

    /**
     * Get the first name of the user.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Update the first name of the user.
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the user.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Update the last name of the user.
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the telephone number of the user.
     * @return the telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Update the telephone number of the user.
     * @param telephone the new telephone number
     */
    public void setTelephone(String telephone) { this.telephone = telephone; }

    /**
     * Returns the total amount of money this user has.
     * @return the amount of money of the user
     */
    public Money getWallet() {
        // Money is inherently immutable so no need to make a copy
        return wallet;
    }
    /**
     * Add the compensation of a completed job to the wallet of the worker.
     * @param compensation the amount of money the worker gained from the job
     */
    public void addMoney(Money compensation){
        wallet = moneyAdditionStrategy.add(wallet, compensation);
    }

    /**
     * Get the user's email.
     * @return the user's email
     */
    public String getEmail() { return email;}

    /**
     * Update the user's email.
     * @param email the new user's email
     * @throws IllegalArgumentException if the email is wrongly formatted
     */
    public void setEmail(String email) throws IllegalArgumentException {
        if(!verifyEmail(email)){
            throw new EmailInvalidException("Wrong email format " + email);
        }

        this.email = email;
    }

    /**
     * Get the current city of residence of the user.
     * @return the user's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Update the user's city of residence.
     * @param city the new user's city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get the address of the user's residence.
     * @return the user's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Update the address of the user's residence.
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    private static boolean verifyPassword(String proposedPassword){
        return proposedPassword.length() >= MIN_PASSWORD_LENGTH &&
                proposedPassword.length() <= MAX_PASSWORD_LENGTH;
    }

    private static boolean verifyEmail(@NonNull String proposedEmail) {
        final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
            "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*" +
            "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-" +
            "\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
            "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c" +
            "\\x0e-\\x7f])+)])";


        return proposedEmail.matches(EMAIL_REGEX);
    }

    /**
     * Encrypt the user's password.
     * @param encryptStrategy the encryption algorithm
     */
    private void setEncrypted(IEncryptStrategy encryptStrategy){
        this.password = encryptStrategy.encrypt(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @NonNull
    @Override
    public String toString(){
        return this.username;
    }


    /**
     * A builder class for constructing a User instance.
     *
     * @author Tsirmpas Dimitris
     */
    public static final class Builder {

        private final String username;
        private String password = "";
        private String firstName = "";
        private String lastName = "";
        private String telephone = "";
        private String email = "";
        private String city = "";
        private String address = "";
        private Money wallet = Money.parse("USD 0");
        private Collection<Rating> ratings = new HashSet<>();
        private Collection<WorkOffer> activeWorkOffers = new HashSet<>();

        public Builder(String username) {
            this.username = username;
        }

        /**
         * Build a new user based on the included fields. 
         * The password is automatically encrypted.
         * @return a new user
         */
        public User buildNew() {
            User user = new User(this);
            user.setEncrypted(new PBEEncryptStrategy());
            return user;
        }

        /**
         * Build a new user instance of an existing user.
         * Use this method to avoid encrypting the encrypted password.
         * @return a new user instance
         */
        public User buildExisting() {
            return new User(this);
        }

        /**
         * Include a password.
         * @param password the user's password, must be between 8 and 16 characters
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Include a first name.
         * @param firstName the first name of the user
         */
        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        /**
         * Include a last name.
         * @param lastName the last name of the user
         */
        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        /**
         * Include an email.
         * @param email the email of the user
         */
        public Builder email(String email){
            this.email = email;
            return this;
        }

        /**
         * Include a telephone number.
         * @param telephone the phone number of the user
         */
        public Builder telephone(String telephone){
            this.telephone = telephone;
            return this;
        }

        /**
         * Include the user's city.
         * @param city the current city of residence of the user
         */
        public Builder city(String city){
            this.city = city;
            return this;
        }

        /**
         * Include an address.
         * @param address the address of the user's residence
         */
        public Builder address(String address){
            this.address = address;
            return this;
        }

        /**
         * Include the user's stashed money.
         * @param money the current wallet of the user
         */
        public Builder money(Money money){
            this.wallet = money;
            return this;
        }

        /**
         * Include the user's jobs.
         * @param interestingJobs the jobs the user is interested in fulfilling
         */
        public Builder jobs(Collection<WorkOffer> interestingJobs){
            this.activeWorkOffers = interestingJobs;
            return this;
        }

        /**
         * Include the user's ratings.
         * @param receivedRatings the ratings that have been given by other users to this user
         */
        public Builder ratings(Collection<Rating> receivedRatings) {
            this.ratings = receivedRatings;
            return this;
        }
    }
    // custom setter exceptions to figure out which fields are wrong

    /**
     * An exception thrown when the provided password doesn't match the security requirements of the
     * System.
     */
    public static class PasswordInvalidException extends  IllegalArgumentException {
        public PasswordInvalidException(String message) {
            super(message);
        }
    }

    /**
     * An exception thrown when an email with an invalid format is provided.
     */
    public static class EmailInvalidException extends  IllegalArgumentException {
        public EmailInvalidException(String message) {
            super(message);
        }
    }

}
