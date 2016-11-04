package seedu.dailyplanner.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.dailyplanner.model.task.Date1;

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

    public static boolean withinDateRange(Date date, String keyword) {
	int keyDate = Integer.parseInt(keyword.substring(0, 2));
	int keyMonth = Integer.parseInt(keyword.substring(3, 5));
	int keyYear = Integer.parseInt(keyword.substring(6, 8));
	return (date.startDay <= keyDate && date.startMonth <= keyMonth && date.startYear <= keyYear
		&& date.endDay >= keyDate && date.endMonth >= keyMonth && date.endYear >= keyYear);
    }
}
