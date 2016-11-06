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
     * Returns a string that is the result of adding the String at the given
     * input string.If the position is less than 0, the extraString will be
     * added to the front of input String. If the position is greater than the
     * length of the String, extraString will be added to the back of the input
     * String. Asserts that the input String and extraString String are not
     * null, so the caller should ensure that none of the Strings passed in are
     * null.
     * 
     * @param input the String input
     * @param extraString the String to apply to the input String
     * @param position the position of the String to add the extraString
     * @return the String with the extraString added to in the given position of
     *         the original String
     */
    public static String applyStringAtPosition(String input, String extraString, int position) {
        assert input != null && extraString != null;
        
        if (position < 0) {
            position = 0;
        }
        
        if (position > input.length()) {
            position = input.length();
        }
        
        String stringBeforeCaret = input.substring(0, position);
        String stringAfterCaret = input.substring(position);
        return String.join(extraString, stringBeforeCaret, stringAfterCaret);
    }

    // @@author
    /**
     * Appends an additional string to the initial string after a new line.
     */
    public static String appendOnNewLine(String initial, String additional) {
        return initial += "\n" + additional;
    }

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
