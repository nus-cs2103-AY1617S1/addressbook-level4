package seedu.task.model.task;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.task.commons.exceptions.IllegalValueException;

/**
 * Represents a Date and Time in the task list
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date entered should be in a relatively standard form (e.g. in X hours, tomorrow, 20 Jan 2016, 5pm)";

    public final Optional<Instant> value;

    /**
     * Validates given Date and Time entered by the user.
     *
     * @throws IllegalValueException if given date/time string is invalid.
     */
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        if (dateTime.equals("")) {
            this.value = Optional.empty();
            return;
        }
        List<Date> possibleDates = new PrettyTimeParser().parse(dateTime);
        if (!isValidDateTime(possibleDates)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        
        this.value = Optional.of(possibleDates.get(0).toInstant());
    }

    /**
     * Returns true if a given string is a valid date/time that can be parsed
     * 
     * @param test output from date/time parser
     */
    public static boolean isValidDateTime(List<Date> test) {
        return !test.isEmpty() && (test.size() == 1);
    }

    @Override
    public String toString() {
        return (value.isPresent()) ? value.get().toString() : "";
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

}
