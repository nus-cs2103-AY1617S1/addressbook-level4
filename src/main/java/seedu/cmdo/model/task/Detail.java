package seedu.cmdo.model.task;

import seedu.cmdo.commons.exceptions.IllegalValueException;

//@@author A0139661Y
/**
 * Represents a Task's detail in the To Do List.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {

    public final String details;

    /**
     * Validates given detail.
     *
     * @throws IllegalValueException if given detail string is invalid.
     */
    public Detail(String detail) throws IllegalValueException {
        assert detail != null;
        detail = detail.trim();
        this.details = detail;
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
