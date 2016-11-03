//@author A0144939R
package seedu.task.model.task;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Date and Time in the task list
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "You have entered an invalid Date/Time format. For a complete list of all acceptable formats, please view our user guide.";

    public final Optional<Instant> value;
    private static PrettyTime p = new PrettyTime();
    
    //@@author A0141052Y
    /**
     * Constructs a DateTime from an Instant
     * @param dateTime the Instant of the time and date to be represented
     */
    public DateTime(Instant dateTime) {
        if (dateTime == null) {
            this.value = Optional.empty();
            return;
        }

        this.value = Optional.of(dateTime.truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Validates given Date and Time entered by the user.
     * @param dateTime the String representation of the input from the user
     * @throws IllegalValueException if given date/time string is invalid.
     */
    public static DateTime fromUserInput(String dateTime) throws IllegalValueException {
        if (dateTime == null || dateTime.equals("")) {
            return new DateTime(null);
        }
        
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        
        List<Date> possibleDates = new PrettyTimeParser().parse(dateTime);
        return new DateTime(possibleDates.get(0).toInstant());
    }
    
    /**
     * Create a new DateTime object using the number of milliseconds from 01-01-1970
     * @param epochMilli the number of milliseconds elapsed from the epoch
     * @return a new DateTime object for the specified epoch offset
     */
    public static DateTime fromEpoch(Long epochMilli) {
        if (epochMilli == null) {
            return new DateTime(null);
        }
        
        return new DateTime(Instant.ofEpochMilli(epochMilli));
    }
    
    /**
     * Creates a new DateTime based off an offset from another DateTime
     * @param offsetFrom the DateTime to offset from
     * @param amountToAdd the amount of the specified unit to add to the DateTime
     * @param unit the time unit of the amount
     * @return a new DateTime object with a value that is offset from another DateTime
     */
    public static DateTime fromDateTimeOffset(DateTime offsetFrom, long amountToAdd, TemporalUnit unit) {
        if (offsetFrom == null || offsetFrom.isEmpty()) {
            return new DateTime(null);
        }
        
        Instant offsetInstant = offsetFrom.value.get();
        return new DateTime(offsetInstant.plus(amountToAdd, unit));
    }

    //@@author A0144939R
    
    /**
     * Returns true if a given string is a valid date/time that can be parsed
     * 
     * @param test output from date/time parser
     */
    public static boolean isValidDateTime(String dateTime) {
        List<Date> possibleDates = new PrettyTimeParser().parse(dateTime);
        if(!possibleDates.isEmpty() && (possibleDates.size() == 1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        if(value.isPresent()) {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.FULL )
                                     .withLocale( Locale.UK )
                                     .withZone( ZoneId.systemDefault() );
            return formatter.format( value.get() );
        } else {
            return "";
        }
    }
    
    public String toPrettyString() {
        if(value.isPresent()) {
            return p.format(Date.from(this.value.get()));
        } else {
            return "";
        }
    }
    
    public Long getSaveableValue() {
        if(value.isPresent()) {
            return this.value.get().toEpochMilli();
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime// instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
    /**
     * Returns an optional corresponding to the value of the DateTime object
     * @return value of DateTime object
     */
    public Optional<Instant> getDateTimeValue() {
        return this.value;
    }

    /**
     * Checks if there is a DateTime specified
     * 
     * @return true if a DateTime is specified, else false
     */
    public boolean isEmpty() {
        return !this.value.isPresent();
    }
}
