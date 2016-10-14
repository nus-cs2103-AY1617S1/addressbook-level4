package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class RecurrenceRate {
    
    private static final String STRING_CONSTANT_ONE = "1";

    public static final String MESSAGE_VALUE_CONSTRAINTS = "Format for recurrence rate should be: repeat every [RATE] TIME_PERIOD\n"
            + "RATE must be a positive integer and TIME_PERIOD must be in one of the formats:\n"
            + "\"day(s)\", \"week(s)\", \"month(s)\", \"year(s)\", \"Monday\", \"Wed\"\n"
            + "For example: \"repeat every 3 days\", \"repeat every week\", \"repeat every Wed\"";
    
    public static final String[] arr = {"hour", "hours", "day", "days", "week", "weeks", ""};
    
    public Integer rate;
    public TimePeriod timePeriod;

    /**
     * Validates given rate and timePeriod.
     *
     * @throws IllegalValueException if either values are invalid.
     */
    public RecurrenceRate(String rate, String timePeriod) throws IllegalValueException {
        assert timePeriod != null;
        assert rate != null;
        
        isValidTimePeriod(timePeriod.trim());
        
        if (Integer.valueOf(rate) <= 0) {   
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        
        try {
            this.rate = Integer.valueOf(rate);
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    
    public RecurrenceRate(String timePeriod) throws IllegalValueException {
        this(STRING_CONSTANT_ONE, timePeriod);
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
        return Integer.toString(rate) + " " + timePeriod.toString().toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecurrenceRate // instanceof handles nulls
                && this.rate.equals(((RecurrenceRate) other).rate) // state check
                && this.timePeriod.equals(((RecurrenceRate) other).timePeriod));
    }

    @Override
    public int hashCode() {
        return rate.hashCode();
    }
}