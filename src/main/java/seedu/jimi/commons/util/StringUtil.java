package seedu.jimi.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static boolean containsIgnoreCase(String source, String query) {
        String[] split = source.toLowerCase().split("\\s+");
        List<String> strings = Arrays.asList(split);
        return strings.stream().filter(s -> s.equals(query.toLowerCase())).count() > 0;
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t){
        assert t != null;
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if s represents an unsigned integer e.g. 1, 2, 3, ... <br>
     *   Will return false for null, empty string, "-1", "0", "+1", and " 2 " (untrimmed) "3 0" (contains whitespace).
     * @param s Should be trimmed.
     */
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^0*[1-9]\\d*$");
    }

    /** 
     * Returns the first word separated by spaces in {@code text}. 
     * Adapted from StackOverflow because I'm too lazy to create my own method.
     */
    public static String getFirstWord(String text) {
        String trimmed = new String(text.trim());
        if (trimmed.indexOf(' ') > -1) { // Check if there is more than one word.
            return trimmed.substring(0, trimmed.indexOf(' ')); // Extract first word.
        } else {
            return trimmed; // Text is the first word itself.
        }
    }
}
