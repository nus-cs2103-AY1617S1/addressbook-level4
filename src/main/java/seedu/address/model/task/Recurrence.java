package seedu.address.model.task;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a task's recurrence (deadline, period, etc)
 * in the task list.
 * 
 * Guarantees: immutable;
 */
public class Recurrence {
    public enum Pattern {
        NONE, DAILY, WEEKLY, MONTHLY, YEARLY
    }

    public static final String MESSAGE_FREQUENCY_CONSTRAINT = "Frequency must be greater than 0.";
    
    public static final String TO_STRING_FORMAT = "%s [%d time(s)]";
    public static final String TO_STRING_NO_RECURRENCE = "No recurrence.";
    
    public final boolean hasRecurrence;
    public final Pattern pattern;
    public final int frequency;
    
    /**
     * Constructor for no recurrence.
     */
    public Recurrence() {
        this.hasRecurrence = false;
        this.pattern = Pattern.NONE;
        this.frequency = 0;
    }
    
    /**
     * Constructor for a given recurring pattern.
     * 
     * Pattern should not be null and frequency
     * must be valid (see {@link #isValidFrequency(int)}).
     * Pattern should not be NONE, call the default constructor
     * for such patterns.
     */
    public Recurrence(Pattern pattern, int frequency) throws IllegalValueException {
        assert pattern != null;
        assert pattern != Pattern.NONE;
        
        if (!isValidFrequency(frequency)) {
            throw new IllegalValueException(MESSAGE_FREQUENCY_CONSTRAINT);
        }
        
        this.hasRecurrence = true;
        this.pattern = pattern;
        this.frequency = frequency;
    }

    /**
     * Only positive frequency is valid.
     */
    private boolean isValidFrequency(int frequency) {
        return frequency > 0;
    }

    
    @Override
    public String toString() {
        if (hasRecurrence) {
            return String.format(TO_STRING_FORMAT, pattern.toString(), frequency);
        }
        
        return TO_STRING_NO_RECURRENCE;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Recurrence // instanceof handles nulls
                && (this.pattern.equals(((Recurrence) other).pattern)
                        && (this.frequency == ((Recurrence)other).frequency))); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, frequency);
    }
}
