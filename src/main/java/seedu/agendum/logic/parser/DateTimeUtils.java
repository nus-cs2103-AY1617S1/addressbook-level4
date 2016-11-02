package seedu.agendum.logic.parser;

import com.joestelmach.natty.DateGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//@@author A0003878Y

/**
 * Utilities for DateTime parsing
 */
public class DateTimeUtils {

    /**
     * Parses input string into LocalDateTime objects using Natural Language Parsing
     * @param input natural language date time string
     * @return Optional is null if input coult not be parsed
     */
    public static Optional<LocalDateTime> parseNaturalLanguageDateTimeString(String input) {
        if(input == null || input.isEmpty()) {
            return Optional.empty();
        }
        // Referring to natty's Parser Class using its full path because of the namespace collision with our Parser class.
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List groups = parser.parse(input);

        if (groups.size() <= 0) {
            return Optional.empty();
        }

        DateGroup dateGroup = (DateGroup) groups.get(0);

        if (dateGroup.getDates().size() < 0) {
            return Optional.empty();
        }

        Date date = dateGroup.getDates().get(0);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return Optional.ofNullable(localDateTime);
    }

    /**
     * Takes two LocalDateTime and balances by ensuring that the latter DateTime is gaurenteed to be later
     * than the former DateTime
     * @param startDateTime
     * @param endDateTime
     * @return endDateTime that is now balanced
     */
    public static LocalDateTime balanceStartAndEndDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime newEndDateTime = endDateTime;
        while (startDateTime.compareTo(newEndDateTime) >= 1) {
            newEndDateTime = newEndDateTime.plusDays(1);
        }
        return newEndDateTime;
    }

    public static boolean containsTime(String input) {
        return parseNaturalLanguageDateTimeString(input).isPresent();
    }
}
