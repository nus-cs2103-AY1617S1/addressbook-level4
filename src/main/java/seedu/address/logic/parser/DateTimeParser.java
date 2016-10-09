package seedu.address.logic.parser;

import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Parses datetimes with help of {@link com.joestelmach.natty.Parser}
 */
public class DateTimeParser {
    public static final LocalTime DefaultLocalTime = LocalTime.NOON;

    private Parser parser = new Parser();

    /**
     * Gets the first datetime encountered in text
     * Works based on {@link com.joestelmach.natty.Parser}, but:
     * - Converted to `LocalDateTime`
     * - If time is deemed as "inferred" (in natty), time = {@link #DefaultLocalTime}
     * - Seconds field is always set to 0 (ignored)
     */
    public Optional<LocalDateTime> parseDateTime(String text) {
        List<DateGroup> dateGroups = parser.parse(text);

        // Return first date parsed
        if (!dateGroups.isEmpty()) {
            DateGroup dateGroup = dateGroups.get(0);
            List<Date> dates = dateGroup.getDates();
            if (!dates.isEmpty()) {
                return Optional.of(toLocalDateTime(dateGroup, dates.get(0)));
            }
        }

        return Optional.empty();
    }

    private LocalDateTime toLocalDateTime(DateGroup dateGroup, Date date) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        ZoneId zoneId = ZoneOffset.systemDefault();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

        // Check if time is inferred
        if (dateGroup.isTimeInferred()) {
            localDateTime = LocalDateTime.of(
                localDateTime.toLocalDate(),
                DefaultLocalTime
            );
        }

        // Reset seconds
        localDateTime = localDateTime.withSecond(0);

        return localDateTime;
    }
}