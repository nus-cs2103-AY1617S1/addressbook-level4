package seedu.todo.controllers.concerns;

import java.util.Map;

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

}
