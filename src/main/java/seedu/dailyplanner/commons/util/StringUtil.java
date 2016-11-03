package seedu.dailyplanner.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import seedu.dailyplanner.model.task.Date;

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

    public static boolean withinDateRange(Date phone, String keyword) {
	int keyDate = Integer.parseInt(keyword.substring(0, 2));
	int keyMonth = Integer.parseInt(keyword.substring(3, 5));
	int keyYear = Integer.parseInt(keyword.substring(6, 8));
	return (phone.startDay <= keyDate && phone.startMonth <= keyMonth && phone.startYear <= keyYear
		&& phone.endDay >= keyDate && phone.endMonth >= keyMonth && phone.endYear >= keyYear);
    }
}
