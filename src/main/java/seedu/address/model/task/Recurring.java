package seedu.address.model.task;
//@@author A0142325R
import java.util.HashSet;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a recurring task in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Recurring {

    public static final String MESSAGE_RECURRING_CONSTRAINTS = "Recurring frequency should be 'daily/weekly/monthly/yearly' ";
    public static HashSet<String> frequencySet=new HashSet<String>();

    public String recurringFrequency;

    /**
     * Validates given frequency.
     *
     * @throws IllegalValueException if given frequency string is invalid.
     */
    public Recurring(String freq) throws IllegalValueException {
        frequencySet.add("daily");
        frequencySet.add("weekly");
        frequencySet.add("monthly");
        frequencySet.add("yearly");
        assert freq != null;
        recurringFrequency = freq.trim();
        if (!isValidFrequency(recurringFrequency)) {
            throw new IllegalValueException(MESSAGE_RECURRING_CONSTRAINTS);
        }
        this.recurringFrequency = freq;
    }

    /**
     * Returns true if a given string is a valid task recurring frequency.
     */
    public static boolean isValidFrequency(String test) {
        return frequencySet.contains(test);
    }


    @Override
    public String toString() {
        return recurringFrequency;
    }
    
    public String getFrequency(){
        return recurringFrequency;
    }
    

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurring // instanceof handles nulls
                && this.recurringFrequency.equals(((Recurring) other).recurringFrequency)); // state check
    }

    @Override
    public int hashCode() {
        return recurringFrequency.hashCode();
    }

}
