package seedu.taskman.model.task;

import seedu.taskman.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's title in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS = "Task titles should be spaces or alphanumeric characters";
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String title;

    /**
     * Validates given title.
     *
     * @throws IllegalValueException if given title string is invalid.
     */
    public Title(String title) throws IllegalValueException {
        assert title != null;
        title = title.trim();
        if (!isValidTitle(title)) {
            throw new IllegalValueException(MESSAGE_TITLE_CONSTRAINTS);
        }
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid task title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
