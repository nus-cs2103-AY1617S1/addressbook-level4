package seedu.tasklist.model.task;

//@@author A0146107M
import org.apache.commons.lang.StringUtils;

import seedu.tasklist.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class UniqueID {

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public UniqueID(String address) throws IllegalValueException {
        assert StringUtils.isNumeric(address);
        this.value = address;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueID // instanceof handles nulls
                && this.value.equals(((UniqueID) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}