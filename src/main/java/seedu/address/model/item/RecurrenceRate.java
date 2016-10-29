package seedu.address.model.item;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
/**
 * Represents a Task's recurrence rate in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #RecurrenceRate(String, String)}
 */
public class RecurrenceRate {
    
    private static final String STRING_CONSTANT_ONE = "1";

    public static final String MESSAGE_VALUE_CONSTRAINTS = "RECURRING_INTERVAL Format : repeat every [RATE] TIME_PERIOD\n"
            + "RATE must be a positive integer and TIME_PERIOD must be in one of the formats: "
            + "\"hour(s)\", \"day(s)\", \"week(s)\", \"month(s)\", \"year(s)\", "
            + "or days of the week such as \"Monday\", \"Wed\"\n"
            + "For example: \"repeat every 3 days\", \"repeat every week\", \"repeat every Wed\"";
    
    public Integer rate;
    public TimePeriod timePeriod;

    /**
     * Constructor for RecurrenceRate. 
     * Checks for validity of rate and timePeriod.
     *
     * @throws IllegalValueException if either values are invalid.
     */
    public RecurrenceRate(String rate, String timePeriodString) throws IllegalValueException {
        assert rate != null && timePeriodString != null;
        
        Optional<TimePeriod> timePeriod = TimePeriod.convertStringToTimePeriod(timePeriodString.trim());
        this.timePeriod = timePeriod.orElseThrow(() -> new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS));
        this.rate = convertStringToRateInteger(rate);
    }
    
    public RecurrenceRate(String timePeriod) throws IllegalValueException {
        this(STRING_CONSTANT_ONE, timePeriod);
    }

    /**
     * Converts rate from String to Integer.
     *
     * @param rate  user input of rate of recurrence.
     * @return Integer value of rate.
     * @throws IllegalValueException if rate cannot be converted into an Integer
     * or rate is <= 0.
     */
    private Integer convertStringToRateInteger(String rate) throws IllegalValueException {
        assert rate != null;
        int rateInteger;
        
        try {
            rateInteger = Integer.valueOf(rate);
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        
        if (rateInteger <= 0) {
            throw new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS);
        }
        
        return rateInteger;
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