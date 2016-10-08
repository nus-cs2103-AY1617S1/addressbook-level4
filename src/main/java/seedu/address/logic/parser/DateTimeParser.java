package seedu.address.logic.parser;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Parses datetimes with help of {@link com.joestelmach.natty.Parser}
 */
public class DateTimeParser {

    private Parser parser = new Parser();

    public Optional<Date> parseDateTime(String text) {
        List<DateGroup> dateGroups = parser.parse(text);

        // Return first date parsed
        if (!dateGroups.isEmpty()) {
            List<Date> dates = dateGroups.get(0).getDates();
            if (!dates.isEmpty()) {
                return Optional.of(dates.get(0));
            }
        }

        return Optional.empty();
    }
}