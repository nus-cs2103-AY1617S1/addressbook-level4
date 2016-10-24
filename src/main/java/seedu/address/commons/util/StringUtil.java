package seedu.address.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    
    /**
     * Returns true if query is a valid substring of source (beginning at the first character of source)
     * Will return false if either source or query is null
     * Will also return false if the substring of query found in source is different capitalization
     * 
     * @param source the base/source string to check on
     * @param query the query string that you want to check if is substring and begins in source
     * @return boolean representing if the query is a valid substring of source
     */
    public static boolean isSubstringFromStart(String source, String query) {
        return source != null & query != null && source.indexOf(query) == 0;
    }
    
    /**
     * Combines the given list of strings into a single string, with newlines separating the strings.
     * The list given cannot be null.
     * If list of strings is empty, returns an empty string.
     * If a string in the list is null, it will be ignored.
     * 
     * @param strings the list of strings to combine
     * @return the single combined string
     */
    public static String combineStrings(List<String> strings) {
        assert strings != null;
        
        StringBuilder sb = new StringBuilder();
                
        for (String string : strings) {
            if (string != null) {
                sb.append(string);
                sb.append("\n");
            }
        }
        
        // remove the final newline char if present
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        
        return sb.toString();
    }
    
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
}
