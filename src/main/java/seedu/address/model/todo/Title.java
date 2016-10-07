package seedu.address.model.todo;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the title of a to-do
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Title {

    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public final String title;

    /**
     * Constructor for a title
     * @throws IllegalValueException if given title is invalid
     */
    public Title(String title) throws IllegalValueException {
        if (!isValid(title)) {
            throw new IllegalValueException(Messages.MESSAGE_TODO_TITLE_CONSTRAINTS);
        }

        title = title.trim();

        this.title = title;
    }

    /**
     * Returns true if a given string is a valid title
     */
    public static boolean isValid(String title) {
        return title != null && title.matches(TITLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
