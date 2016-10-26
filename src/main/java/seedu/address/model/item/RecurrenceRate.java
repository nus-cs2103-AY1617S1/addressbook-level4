package seedu.address.model.item;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
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
     * Validates given rate and timePeriod.
     *
     * @throws IllegalValueException if either values are invalid.
     */
    public RecurrenceRate(String rate, String timePeriodString) throws IllegalValueException {
        assert rate != null && timePeriodString != null;
        
        Optional<TimePeriod> timePeriod = TimePeriod.validateTimePeriodInput(timePeriodString.trim());
        this.timePeriod = timePeriod.orElseThrow(() -> new IllegalValueException(MESSAGE_VALUE_CONSTRAINTS));
        
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