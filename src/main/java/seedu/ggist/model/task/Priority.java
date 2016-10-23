package seedu.ggist.model.task;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's priority in the GGist.
 * Guarantees: immutable; is valid as declared in {low|med|high}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Task priority must be prefixed with -\n "
            + "Valid priority level are -low , -med , -high";

    public static final String PRIORITY_VALIDATION_REGEX = "low|med|high|"+ Messages.MESSAGE_NO_PRIORITY_VALUE;

    public String value;

    /**
     * Validates given priority.
     *
     * @throws IllegalValueException if given priority string is invalid.
     */
    public Priority(String priority) throws IllegalValueException {
        if (priority == null) {
            value =  Messages.MESSAGE_NO_PRIORITY_VALUE;
        } else if (!isValidPriority(priority.trim())){
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        } else {
            priority = priority.trim();
            value = priority;
        }
    }
    
    /**
     * Changes the value attribute
     * @param String
     * @throws IllegalValueException
     */
    public void editPriority(String newPriority) throws IllegalValueException {
        assert newPriority!= null;
        newPriority = newPriority.trim();
        if (!isValidPriority(newPriority)) {
            throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
        }
        this.value = newPriority;
    }
    
    /**
     * Returns the matched string
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instance of handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
