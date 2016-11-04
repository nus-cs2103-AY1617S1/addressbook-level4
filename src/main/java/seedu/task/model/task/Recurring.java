package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

// @@author A0147944U
public class Recurring {
    
    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Recurring parameter should be hourly, daily, weekly, fortnightly monthly, yearly or false";
    public static final String RECURRING_VALIDATION_REGEX = "[\\p{Alnum} ]+";
    
    public final String recurringState;

    /**
     * Validates given Recurring parameter.
     *
     * @throws IllegalValueException
     *             if given recurring string is invalid.
     */
    public Recurring(String recurring) throws IllegalValueException {
        assert recurring != null;
        if (!isValidRecurring(recurring)) {
            throw new IllegalValueException(MESSAGE_RECURRING_CONSTRAINTS);
        }
        this.recurringState = recurring;
    }
    
    /**
     * Returns true if a given string is valid as a Recurring parameter.
     */
    public static boolean isValidRecurring(String test) {
        return test.matches(RECURRING_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return recurringState;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                && this.recurringState.equals(((Recurring) other).recurringState)); // state check
    }

    @Override
    public int hashCode() {
        return recurringState.hashCode();
    }

}
