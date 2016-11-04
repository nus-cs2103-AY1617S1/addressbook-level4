package seedu.todo.controllers.concerns;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.todo.commons.exceptions.InvalidNaturalDateException;

/**
 * @@author A0093907W
 * 
 * Class to store date parsing methods to be shared across controllers.
 *
 */
public class DateParser {
    
    /**
     * Extracts the natural dates from parsedResult.
     * 
     * @param parsedResult
     * @return { naturalFrom, naturalTo }
     */
    public static String[] extractDatePair(Map<String, String[]> parsedResult) {
        String naturalFrom = null;
        String naturalTo = null;
        setTime: {
            if (parsedResult.get("time") != null && parsedResult.get("time")[1] != null) {
                naturalFrom = parsedResult.get("time")[1];
                break setTime;
            }
            if (parsedResult.get("timeFrom") != null && parsedResult.get("timeFrom")[1] != null) {
                naturalFrom = parsedResult.get("timeFrom")[1];
            }
            if (parsedResult.get("timeTo") != null && parsedResult.get("timeTo")[1] != null) {
                naturalTo = parsedResult.get("timeTo")[1];
            }
        }
        return new String[] { naturalFrom, naturalTo };
    }
    
    /**
     * Parse a natural date into a LocalDateTime object.
     * 
     * @param natural
     * @return LocalDateTime object
     * @throws InvalidNaturalDateException 
     */
    public static LocalDateTime parseNatural(String natural) throws InvalidNaturalDateException {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(natural);
        Date date = null;
        try {
            date = groups.get(0).getDates().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidNaturalDateException(natural);
        }
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return ldt;
    }

}
