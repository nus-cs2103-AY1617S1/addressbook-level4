package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class RecurrenceRate {
    
    public static final String MESSAGE_VALUE_CONSTRAINTS = "Recurrence rate should be more than or equals to 0 days";
    
    public Integer recurrenceRate;
    public TimePeriod timePeriod;

    /**
     * Validates given recurrence rate.
     *
     * @throws IllegalValueException if given recurrence rate is invalid.
     */
    public RecurrenceRate(String recurrenceRateString, String timePeriod) throws IllegalValueException {
        if (timePeriod != null) {
            timePeriod = timePeriod.trim();
            isValidTimePeriod(timePeriod);
        }
        try {
            if (recurrenceRateString != null) {
                this.recurrenceRate = Integer.valueOf(recurrenceRateString);
            }
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    //TODO: Anything better than null?
    public RecurrenceRate(String timePeriod) throws IllegalValueException {
        this(null, timePeriod);
    }
        
    //TODO: Anything better than null?
    public RecurrenceRate() throws IllegalValueException {
        this(null, null);
    }

    private void isValidTimePeriod(String timePeriod) throws IllegalValueException {
        switch (timePeriod) {
        case ("day"):
        case ("days"):
            this.timePeriod = TimePeriod.DAY; 
            break;
        case ("week"):
        case ("weeks"):
            this.timePeriod = TimePeriod.WEEK;
            break;
        case ("month"):
        case ("months"):
            this.timePeriod = TimePeriod.MONTH;
            break;
        case ("year"):
        case ("years"):
            this.timePeriod = TimePeriod.YEAR;
            break;
        default:
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    @Override
    public String toString() {
        return Integer.toString(recurrenceRate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurrenceRate // instanceof handles nulls
                && this.recurrenceRate.equals(((RecurrenceRate) other).recurrenceRate) // state check
                && this.timePeriod.equals(((RecurrenceRate) other).timePeriod));
    }

    @Override
    public int hashCode() {
        return recurrenceRate.hashCode();
    }
}