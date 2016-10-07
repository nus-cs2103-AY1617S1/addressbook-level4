package taskle.model.person;

import taskle.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's DateTime data in the address book. Guarantees: immutable;
 * is valid as declared in {@link #isValidTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_NAME_CONSTRAINTS = "DateTime Strings should be spaces or alphanumeric characters";
    public static final String DATE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    // DateTime member variable
    private int day;
    private int month;
    private int year;
    private int timeInt;

    /**
     * Validates given timeString.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public DateTime(String timeString) throws IllegalValueException {
        assert timeString != null;
        timeString = timeString.trim();
        if (!isValidTime(timeString)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid time String.
     */
    public static boolean isValidTime(String time) {
        return time.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(timeInt);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateTime
                        && this.day == (((DateTime) other).day)
                        && this.month == (((DateTime) other).month)
                        && this.year == (((DateTime) other).year)); 

    }

    @Override
    public int hashCode() {
        return timeInt;
    }

}
