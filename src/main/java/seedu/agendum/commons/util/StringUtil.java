package seedu.agendum.commons.util;

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
    
    //@@author A0148095X
    /**
     * Checks whether the string matches an approved file path.
     * <p>
     * Examples of valid file paths: <br>
     * - C:/Program Files (x86)/some-folder/data.xml <br>
     * - data/todolist.xml <br>
     * - list.xml <br>
     * </p>
     * <p>
     * Examples of invalid file paths: <br>
     * - data/.xml <br>
     * - data/user <br>
     * - C:/Program /data.xml <br>
     * - C:/ Files/data.xml <br>
     * </p>
     * @param s should be trimmed
     * @return true if it is a valid file path
     */
    public static boolean isValidPathToFile(String s) {
        return s != null && !s.isEmpty() && s.matches("([A-z]\\:)?(\\/?[\\w-_()]+(\\s[\\w-_()])?)+(\\.[\\w]+)");
    }
}
