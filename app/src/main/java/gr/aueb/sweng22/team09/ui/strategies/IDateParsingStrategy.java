package gr.aueb.sweng22.team09.ui.strategies;

import java.time.LocalDate;

/**
 * A strategy that parses a user provided date into a {@link LocalDate} object.
 *
 * @author Dimitris Tsirmpas
 */
public interface IDateParsingStrategy {

    // All of this is because the LocalDate.parse() method is eternally retarded
    /**
     * Parses the string to produce a new {@link LocalDate} object,
     * @param localDateString the date to be parsed
     * @return the LocalDate object or null if an error occurs
     */
    LocalDate parseDate(String localDateString);
}
