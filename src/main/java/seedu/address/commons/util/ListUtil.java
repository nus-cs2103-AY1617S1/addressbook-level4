package seedu.address.commons.util;

import java.util.List;

/**
 * Utility methods related to List
 */
public class ListUtil {
    
    /**
     * Forms display string from list.
     * 
     * @return String with each item on a new line.
     */
    public static String generateDisplayString(List<?> items) {
        String toDisplay = "";
        for (Object item : items) {
            toDisplay += "\n" + item.toString();
        }
        return toDisplay;
    }
    
}
