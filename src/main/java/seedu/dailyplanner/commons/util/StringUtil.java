package seedu.dailyplanner.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import seedu.dailyplanner.logic.parser.nattyParser;
import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Time;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    
    public static final String EMPTY_STRING = "";
    
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
	 * @param s
	 *            Should be trimmed.
	 */
	public static boolean isUnsignedInteger(String s) {
		return s != null && s.matches("^0*[1-9]\\d*$");
	}
	
	 // @@author A0140124B
    public static boolean stringContainsAmOrPm(String startString) {
        return startString.contains("am") || startString.contains("pm");
    }
    
    /*
     * Loops through arguments, adds them to hashmap if valid
     */

    public static void argumentArrayToHashMap(HashMap<String, String> mapArgs, String[] splitArgs) {
   	for (int i = 0; i < splitArgs.length; i++) {
   	    if (splitArgs[i].substring(0, 2).equals("s/")) {
   		extractArgument(mapArgs, splitArgs, i, "start");
   	    }

   	    if (splitArgs[i].substring(0, 2).equals("e/")) {
   		extractArgument(mapArgs, splitArgs, i, "end");
   	    }

   	    if (splitArgs[i].substring(0, 2).equals("c/")) {
   		extractArgument(mapArgs, splitArgs, i, "cats");

   	    }
   	}
       }

       private static void extractArgument(HashMap<String, String> mapArgs, String[] splitArgs, int i, String type) {
   	int j = i + 1;
   	String arg = splitArgs[i].substring(2);
   	while (j < splitArgs.length && !splitArgs[j].contains("/")) {
   	    arg += " " + splitArgs[j];
   	    j++;
   	}
   	i = j;
   	mapArgs.put(type, arg);
        }
    }
	 
