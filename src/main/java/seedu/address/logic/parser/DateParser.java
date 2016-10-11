package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

import com.google.common.base.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A parser for dates in day/month/year format.
 */
public class DateParser {

    private final LocalDate referenceDate;

    private final DateTimeFormatter dateFormatter;

    public DateParser(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
        dateFormatter = new DateTimeFormatterBuilder()
                .appendPattern("d[/M[/uuuu]]")
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, this.referenceDate.getMonthValue())
                .parseDefaulting(ChronoField.YEAR, this.referenceDate.getYear())
                .toFormatter();
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public LocalDate parse(String str) throws IllegalValueException {
        final Optional<LocalDate> nameDate = parseAsName(str.trim());
        if (nameDate.isPresent()) {
            return nameDate.get();
        }
        try {
            return LocalDate.parse(str.trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(e.toString());
        }
    }

    private Optional<LocalDate> parseAsName(String name) {
        switch (name) {
        case "tdy": // today
            return Optional.of(referenceDate);
        case "tmr": // tomorrow
            return Optional.of(referenceDate.plusDays(1));
        case "yst": // yesterday
            return Optional.of(referenceDate.minusDays(1));
        default:
            return Optional.absent();
        }
    }

}
