package seedu.todoList.commons.util;

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
    
    //@@author A0139923X
    public static boolean equalsIgnoreCase(String source, String query) {
        List<String> strings = Arrays.asList(source.toLowerCase());
        return strings.stream().filter(s -> s.contains(query.toLowerCase())).count() > 0;
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
    //@@author A0139923X
    public static boolean isUnsignedInteger(String s){
        return s != null && s.matches("^\\d+$");
    }
    
    /**
     * Returns true if s represents an unsigned string e.g. A, AB, ABC, ... <br>
     *   Will return false for null, empty string, numbers, " ABC " (untrimmed) "A B" (contains whitespace).
     * @param s Should be trimmed.
     */
    //@@author A0139923X
    public static boolean isUnsignedString(String s){
        return s != null && s.matches("^[a-zA-Z]+$");
    }
    
    //@@author A0139923X
    public static boolean isUnsignedDate(String s){
        return s != null && s.matches("^(\\d{2}-\\d{2}-\\d{4})$");
    }
}
