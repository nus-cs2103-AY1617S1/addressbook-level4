package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    // @@author A0093960X
    /**
     * Returns a string that is the result of adding the specified stringToAdd
     * String at the specified position in the originalString String. <br>
     * If the position is less than 0, the stringToAdd will be added to the
     * front of originalString. <br>
     * If the position is greater than the length of the originalString, the stringToAdd
     * will be added to the back of the originalString. <br>
     * Asserts that the originalString and stringToAdd are not null.
     * 
     * @param originalString The String to add to
     * @param stringToAdd The String to add to the originalString
     * @param position The position in originalString to add the stringToAdd
     * @return The String with the stringToAdd added to in the given position of
     *         the originalString
     */
    public static String applyStringAtPosition(String originalString, String stringToAdd, int position) {
        assert originalString != null && stringToAdd != null;

        int originalStringLen = originalString.length();
        boolean isPositionBelowMinimum = (position < 0);
        boolean isPositionAboveMaximum = (position > originalStringLen);

        
        if (isPositionBelowMinimum) {
            position = 0;
        }
        else if (isPositionAboveMaximum) {
            position = originalString.length();
        }

        String stringBeforeCaret = originalString.substring(0, position);
        String stringAfterCaret = originalString.substring(position);
        return String.join(stringToAdd, stringBeforeCaret, stringAfterCaret);
    }

    // @@author

    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     * Will return false for null, empty string, "-1", "0", "+1", and " 2 "
     * (untrimmed) "3 0" (contains whitespace).
     * 
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s) {
        return s != null && s.matches("^0*[1-9]\\d*$");
    }
}
