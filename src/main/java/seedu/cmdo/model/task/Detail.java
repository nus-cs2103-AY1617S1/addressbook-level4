package seedu.cmdo.model.task;

import seedu.cmdo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's detail in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public static final String MESSAGE_DETAIL_CONSTRAINTS = "You can type anything in details. It must not end with by, on, before or at.";
    public static final String DETAIL_VALIDATION_REGEX = ".*";

    public final String details;

    /**
     * Validates given detail.
     *
     * @throws IllegalValueException if given detail string is invalid.
     */
    public Detail(String detail) throws IllegalValueException {
        assert detail != null;
        detail = detail.trim();
        if (!isValidDetail(detail)) {
            throw new IllegalValueException(MESSAGE_DETAIL_CONSTRAINTS);
        }
        this.details = detail;
    }

    /**
     * Returns true if a given string is a valid detail.
     */
    public static boolean isValidDetail(String test) {
        return test.matches(DETAIL_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return details;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                && this.details.equals(((Detail) other).details)); // state check
    }

    @Override
    public int hashCode() {
        return details.hashCode();
    }

}
