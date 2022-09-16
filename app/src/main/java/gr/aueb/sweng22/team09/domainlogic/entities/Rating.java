package gr.aueb.sweng22.team09.domainlogic.entities;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An object holding information about a Review associated with
 * a completed Job.
 *
 * @author Dimitris Tsirmpas
 */
public class Rating {
    private final long id;
    private final int score;
    private final String description;
    private final WorkOffer workOffer;

    /**
     * Construct a new rating with a randomly generated id, associated with a given Job.
     * @param score a value between 1 and 5 inclusive representing the
     * @param description a text describing the rating
     * @param workOffer the work offer with which this rating is associated
     * @throws IllegalArgumentException if the score's value isn't in the above range
     */
    public Rating(int score, String description, WorkOffer workOffer) throws IllegalArgumentException {
        this(ThreadLocalRandom.current().nextLong(), score, description, workOffer);
    }

    /**
     * Construct a rating object out of an already created review associated with a given Job.
     * @param id the rating's id
     * @param score a value between 1 and 5 inclusive representing the
     * @param description a text describing the rating
     * @param workOffer the work offer with which this rating is associated
     * @throws IllegalArgumentException if the score's value isn't in the above range
     */
    public Rating(long id, int score, String description, WorkOffer workOffer) throws IllegalArgumentException {
        if(score < 1 || score > 5)
            throw new IllegalArgumentException("The score must be between 1 and 5 inclusive.");

        this.id = id;
        this.score = score;
        this.description = description;
        this.workOffer = workOffer;
    }

    /**
     * Get the score of the Rating.
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the User that posted the Rating
     * @return the rating's poster
     */
    public User getReviewer(){
        return workOffer.getJob().getPoster();
    }

    /**
     * Get the User that got reviewed by the rating.
     * @return the review's target
     */
    public User getReviewed(){
        return workOffer.getInterestedWorker();
    }

    /**
     * Get the text component of the rating.
     * @return the rating's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the job's title.
     * @return the job's title
     */
    public String getJobTitle() {
        return workOffer.getJob().getTitle();
    }

    /**
     * Get the object's unique identifier.
     * @return the id
     */
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return getId() == rating.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
