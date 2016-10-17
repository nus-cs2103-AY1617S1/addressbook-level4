package teamfour.tasc.model.task;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.commons.util.ObjectUtil;

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
    
    private final boolean hasRecurrence;
    private final Pattern pattern;
    private final int frequency;
    
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
        if (hasRecurrence()) {
            return String.format(TO_STRING_FORMAT, getPattern().toString(), getFrequency());
        }
        
        return TO_STRING_NO_RECURRENCE;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }
        
        if (other instanceof Recurrence == false) { // instanceof handles nulls
            return false;
        }
        
        Recurrence otherRecurrence = (Recurrence)other;
        
        return ObjectUtil.isEquivalentOrBothNull(this.hasRecurrence(), otherRecurrence.hasRecurrence()) &&
                ObjectUtil.isEquivalentOrBothNull(this.getPattern(), otherRecurrence.getPattern()) &&
                ObjectUtil.isEquivalentOrBothNull(this.getFrequency(), otherRecurrence.getFrequency()); // state checkk
    
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPattern(), getFrequency());
    }

    public boolean hasRecurrence() {
        return hasRecurrence;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getFrequency() {
        return frequency;
    }
    
    /**
     * Get the next recurrence date according to the pattern that
     * we have
     * @param currentDate to base our next date on
     * @return
     */
    public Date getNextDateAfterRecurrence(Date currentDate) {
        if (pattern == Pattern.NONE) {
            return null;
        }
        
        LocalDateTime localDateTime = LocalDateTime.ofInstant(currentDate.toInstant(),
                ZoneId.systemDefault());
        LocalDateTime finalDateTime = localDateTime;

        switch (pattern) {
        case NONE:
            return null;
            
        case DAILY:
            finalDateTime = localDateTime.plusDays(1);
            break;
            
        case WEEKLY:
            finalDateTime = localDateTime.plusWeeks(1);
            break;
            
        case MONTHLY:
            finalDateTime = localDateTime.plusMonths(1);
            break;
            
        case YEARLY:
            finalDateTime = localDateTime.plusYears(1);
            break;
            
        default:
            assert false : "Not possible";
            break;
        }
        
        return Date.from(finalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Get the same recurrence, except that we have one less frequency.
     * @return
     * @throws IllegalValueException 
     */
    public Recurrence getRecurrenceWithOneFrequencyLess() throws IllegalValueException {
        if (pattern == Pattern.NONE) {
            return null;
        }
        
        if (frequency == 1) {
            return new Recurrence();
        }
        
        return new Recurrence(pattern, frequency - 1);
    }
}
