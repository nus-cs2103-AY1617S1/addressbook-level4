package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's title in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidTitle(String)}
 */
//@@author A0153411W
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS = "Task titles should be spaces or alphanumeric characters";
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String fullTitle;

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
        this.fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid task title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && this.fullTitle.equals(((Title) other).fullTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }

}
