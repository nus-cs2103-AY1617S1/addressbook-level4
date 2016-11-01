package seedu.malitio.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    
    //@@author a0126633j
    /**
     * Checks whether any of the query is part of the source string.
     */
    public static boolean containsIgnoreCase(String source, String query) {
        String[] splitSource = source.toLowerCase().split("\\s+");
        String[] splitQuery = query.toLowerCase().split("\\s+");

        for(int i = 0; i < splitQuery.length; i++) {
            for(int j = 0; j < splitSource.length; j++) {
               if (splitSource[j].contains(splitQuery[i])) {
                   return true;
               }
            }
        }
        return false;
    }
    //@@author

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
     * Removes the tag arguments in a string
     * @return
     */
    public static String removeTagsFromString(String arg) {
        // string has no tags to begin with
        if(arg.indexOf("t/") == -1) { 
        return arg;
        }
        
        return arg.substring(0,arg.indexOf("t/"));    
    }
    
    //@@author a0126633j
    public static String removeSlashesAtBeginningOfString(String arg) {
        while(arg.charAt(0) == '/') {
            arg = arg.substring(1);
        }
        return arg;
    }
    /**
     * Reformats a tag string into a string separated by white space.
     * e.g. "[cs2103], [cs1010e]"  into "cs2103 cs1010e".
     */
    public static String reformatTagString(String arg) {
        return arg.replaceAll(",", "").replaceAll("\\[|\\]", " ");
    }
}
