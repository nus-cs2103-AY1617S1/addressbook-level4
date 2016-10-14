package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

public class RecurrenceRate {
    
    private static final String STRING_CONSTANT_ONE = "1";

    public static final String MESSAGE_VALUE_CONSTRAINTS = "Format for recurrence rate should be: repeat every [RATE] TIME_PERIOD\n"
            + "RATE must be a positive integer and TIME_PERIOD must be in one of the formats:\n"
            + "\"day(s)\", \"week(s)\", \"month(s)\", \"year(s)\", \"Monday\", \"Wed\"\n"
            + "For example: \"repeat every 3 days\", \"repeat every week\", \"repeat every Wed\"";
    
    //TODO: Design decision?
    public static final String[][] TIME_PERIOD_INPUT = {{"hour", "hours", "day", "days", "week", "weeks", "month", "months", 
        "year", "years", "mon", "monday", "tues", "tuesday", "wed", "wednesday", "thur", "thurs", "thursday", 
        "fri", "friday", "sat", "saturday", "sun", "sunday"}, {"hour", "hour", "day", "day", "week", "week", "month", "month", 
            "year", "year", "monday", "monday", "tuesday", "tuesday", "wednesday", "wednesday", "thursday", "thursday", "thursday", 
            "friday", "friday", "saturday", "saturday", "sunday", "sunday"}};
    
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
        String validTimePeriod = null;
        
        for (int j = 0; j < TIME_PERIOD_INPUT[0].length; j++) {
            if (TIME_PERIOD_INPUT[0][j].equals(timePeriod.toLowerCase())) {
                validTimePeriod = TIME_PERIOD_INPUT[1][j];
                break;
            }
        }
        
        switch (validTimePeriod) {
        case ("hour"):
            this.timePeriod = TimePeriod.HOUR;
            break;
        case ("day"):
            this.timePeriod = TimePeriod.DAY; 
            break;
        case ("week"):
            this.timePeriod = TimePeriod.WEEK;
            break;
        case ("month"):
            this.timePeriod = TimePeriod.MONTH;
            break;
        case ("year"):
            this.timePeriod = TimePeriod.YEAR;
            break;
        case ("monday"):
            this.timePeriod = TimePeriod.MONDAY;
            break;
        case ("tuesday"):
            this.timePeriod = TimePeriod.TUESDAY;
            break;
        case ("wednesday"):
            this.timePeriod = TimePeriod.WEDNESDAY;
            break;
        case ("thursday"):
            this.timePeriod = TimePeriod.THURSDAY;
            break;
        case ("friday"):
            this.timePeriod = TimePeriod.FRIDAY;
            break;
        case ("saturday"):
            this.timePeriod = TimePeriod.SATURDAY;
            break;
        case ("sunday"):
            this.timePeriod = TimePeriod.SUNDAY;
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