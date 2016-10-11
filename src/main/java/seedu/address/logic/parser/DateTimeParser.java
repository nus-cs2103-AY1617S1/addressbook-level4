package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A date and time parser. See {@link DateParser} and {@link TimeParser}.
 */
public class DateTimeParser {

    private final DateParser dateParser;

    private final TimeParser timeParser;

    public DateTimeParser() {
        this(LocalDateTime.now());
    }

    public DateTimeParser(LocalDateTime referenceDateTime) {
        assert referenceDateTime != null;
        dateParser = new DateParser(referenceDateTime.toLocalDate());
        timeParser = new TimeParser(referenceDateTime.toLocalTime());
    }

    public LocalDateTime getReferenceDateTime() {
        return LocalDateTime.of(dateParser.getReferenceDate(), timeParser.getReferenceTime());
    }

    public LocalDate parseDate(String str) throws IllegalValueException {
        return dateParser.parse(str);
    }

    public LocalTime parseTime(String str) throws IllegalValueException {
        return timeParser.parse(str);
    }

}
