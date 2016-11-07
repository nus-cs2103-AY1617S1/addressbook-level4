package seedu.task.model.task;

import seedu.task.commons.exceptions.IllegalValueException;

// @@author A0147944U
public class Recurring {

    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Recurring parameter should be daily, weekly, fortnightly monthly, yearly, true or false";
    public static final String RECURRING_VALIDATION_REGEX = "[\\p{Alnum} ]+";

    public String recurringState;

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

    /**
     * Updates recurringState with specified interval.
     * 
     * @param interval
     *            the specified interval
     */
    public void setRecurring(String interval) {
        this.recurringState = interval;
    }

    /**
     * Generates a String format of the recurringState
     * 
     * @return recurringState
     */
    @Override
    public String toString() {
        return recurringState;
    }

    /**
     * Checks if the two are the same and returns true if the recurringStates
     * are equal to each other and false otherwise
     * 
     * @param other
     *            The recurringState to check against.
     * @return true if the recurringStates are equal to each other and false
     *         otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                        && this.recurringState.equals(((Recurring) other).recurringState)); // state
                                                                                            // check
    }

    /**
     * Generates a hash code for the Recurring parameter.
     * 
     * @return generated hash code
     */
    @Override
    public int hashCode() {
        return recurringState.hashCode();
    }

}
