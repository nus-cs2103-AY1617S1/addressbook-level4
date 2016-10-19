package tars.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import tars.commons.exceptions.IllegalValueException;
import tars.commons.exceptions.InvalidRangeException;


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
     * Handles three different cases of strings and return them in the appropriate format
     * @@author A0121533W
     */
    public static String indexString(String s) throws InvalidRangeException, IllegalValueException {
        if (s.isEmpty()) {
            return s;
        }
        // String is single number
        if (s.indexOf(" ") == -1 && !s.contains("..")) {
            if (!isUnsignedInteger(s)) {
                throw new IllegalValueException("Invalid index entered"); 
            }
            return s;
        }
        // String is list of indexes separated by whitespace
        if (s.indexOf(" ") != -1 && !s.contains("..")) {
            String indexString = "";
            String[] indexArray = s.split(" ");
            for (int i = 0; i < indexArray.length; i++) {
                if (!isUnsignedInteger(indexArray[i])) {
                    throw new IllegalValueException("Invalid index entered"); 
                }
                indexString += indexArray[i] + " ";
            }
            return indexString.trim();
        }
        // String is a range of indexes
        if (s.contains("..")) {
            String rangeToReturn = "";

            int toIndex = s.indexOf("..");
            String start = s.substring(0, toIndex);
            String end = s.substring(toIndex + 2);
            
            if (!isUnsignedInteger(start) || !isUnsignedInteger(end)) {
                throw new IllegalValueException("Invalid index entered");
            }

            int startInt = Integer.parseInt(start);
            int endInt = Integer.parseInt(end);

            if (startInt > endInt) {
                throw new InvalidRangeException();
            }

            for (int i = startInt; i <= endInt; i++) {
                rangeToReturn += String.valueOf(i) + " ";
            }

            return rangeToReturn.trim();
        } else {
            throw new IllegalValueException("Unexpected error!");
        }
    }
}

