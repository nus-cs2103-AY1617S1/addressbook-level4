package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's venue in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Venue {

    public static final String MESSAGE_VENUE_CONSTRAINTS = "The nvnue of a task can be in any format";
    public static final String VENUE_VALIDATION_REGEX = "^\\s*$|.+";

    public final String value;

    /**
     * Validates given venue.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Venue(String venue) throws IllegalValueException {
        assert venue != null;
        venue = venue.trim();
        if (!isValidVenue(venue)) {
            throw new IllegalValueException(MESSAGE_VENUE_CONSTRAINTS);
        }
        this.value = venue;
    }

    /**
     * Returns true if a given string is a valid person phone number.
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
