package seedu.agendum.logic.parser;

import com.joestelmach.natty.DateGroup;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DateTimeParser {

    public static Optional<LocalDateTime> parseString(String input) {
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

    public static boolean containsTime(String input) {
        return parseString(input).isPresent();
    }
}
