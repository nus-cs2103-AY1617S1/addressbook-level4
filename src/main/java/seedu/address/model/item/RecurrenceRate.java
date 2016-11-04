package seedu.address.model.item;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
/**
 * Represents a Task's recurrence rate in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #RecurrenceRate(String, String)}
 */
public class RecurrenceRate {
    
    /** Name of key in map that maps to the recurrence rate of task */
    private static final String MAP_RECURRENCE_RATE_KEY = "recurrenceRate";
    /** Name of key in map that maps to the rate of recurrence rate */
    private static final String MAP_RATE_KEY = "rate";
    
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final String STRING_CONSTANT_ONE = "1";

    private static final String MESSAGE_VALUE_CONSTRAINTS = "RECURRING_INTERVAL Format : repeat every [RATE] TIME_PERIOD\n"
            + "RATE must be a positive integer and TIME_PERIOD must be in one of the formats: "
            + "\"hour(s)\", \"day(s)\", \"week(s)\", \"month(s)\", \"year(s)\", "
            + "or days of the week such as \"Monday\", \"Wed\"\n"
            + "For example: \"repeat every 3 days\", \"repeat every week\", \"repeat every Wed\"";

    private Integer rate;
    private TimePeriod timePeriod;

    /**
     * Constructor for RecurrenceRate. 
     * Checks for validity of rate and timePeriod.
     *
     * @throws IllegalValueException if either values are invalid.
     */
    public RecurrenceRate(String rateString, String timePeriodString) throws IllegalValueException {
        assert rateString != null && timePeriodString != null;
        
        Optional<TimePeriod> timePeriod = TimePeriod.convertStringToTimePeriod(timePeriodString.trim());
        this.timePeriod = timePeriod.orElseThrow(() -> new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS));
        this.rate = convertStringToRateInteger(rateString);
    }
    
    public RecurrenceRate(String timePeriod) throws IllegalValueException {
        this(STRING_CONSTANT_ONE, timePeriod);
    }

    /**
     * Converts rate from String to Integer.
     *
     * @param rateString  user input of rate of recurrence.
     * @return Integer value of rate.
     * @throws IllegalValueException if rate cannot be converted into an Integer
     * or rate <= 0.
     */
    private Integer convertStringToRateInteger(String rateString) throws IllegalValueException {
        assert rateString != null;
        int rateInteger;
        
        try {
            rateInteger = Integer.valueOf(rateString);
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        
        if (rateInteger <= 0) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        
        return rateInteger;
    }
    
    public static String getMessageValueConstraints() {
        return MESSAGE_VALUE_CONSTRAINTS;
    }
    
    public Integer getRate() {
        return rate;
    }
    
    public TimePeriod getTimePeriod() {
        return timePeriod;
    }
    
    @Override
    public String toString() {
        return "Every " + (rate == ONE ? "" : rate.toString() + " ")
                + timePeriod.toString().substring(ZERO, ONE).toUpperCase() + timePeriod.toString().substring(ONE).toLowerCase()
                + (rate.intValue() > ONE ? "s" : "");
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
    
    /** 
     * @return the key in map that maps to the recurrence rate of task
     */
    public static String getMapRecurrenceRateKey() {
        return MAP_RECURRENCE_RATE_KEY;
    }
    
    /** 
     * @return the key in map that maps to the rate of recurrence rate
     */
    public static String getMapRateKey() {
        return MAP_RATE_KEY;
    }
}