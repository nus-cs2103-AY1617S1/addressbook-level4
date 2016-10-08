package seedu.task.model.task;


import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVenue(String)}
 */
public class Venue {
    
    public static final String MESSAGE_VENUE_CONSTRAINTS = "Task venue can be in any format";
    public static final String VENUE_VALIDATION_REGEX = "";

    public final String value;

    /**
     * Validates given venue.
     *
     * Venue can be null
     */
    public Venue(String venue) {
        this.value = venue;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidVenue(String test) {
        return test.matches(VENUE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Venue // instanceof handles nulls
                && this.value.equals(((Venue) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}