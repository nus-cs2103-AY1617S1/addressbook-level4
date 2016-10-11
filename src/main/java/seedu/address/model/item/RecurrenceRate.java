package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class RecurrenceRate {
    
    public static final String MESSAGE_VALUE_CONSTRAINTS = "Recurrence rate should be more than or equals to 0 days";
    
    public final Integer recurrenceRate;

    /**
     * Validates given recurrence rate.
     *
     * @throws IllegalValueException if given recurrence rate is invalid.
     */
    public RecurrenceRate(Integer recurrenceRate) throws IllegalValueException {
        if (!isValidValue(recurrenceRate)) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        this.recurrenceRate = recurrenceRate;
    }

    /**
     * Returns true if a given value is a valid RecurrenceRate value.
     */
    public static boolean isValidValue(Integer recurrenceRate) {
        return recurrenceRate >= 0;
    }
    

    @Override
    public String toString() {
        return Integer.toString(recurrenceRate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurrenceRate // instanceof handles nulls
                && this.recurrenceRate.equals(((RecurrenceRate) other).recurrenceRate)); // state check
    }

    @Override
    public int hashCode() {
        return recurrenceRate.hashCode();
    }
}