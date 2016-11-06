package seedu.dailyplanner.commons.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import seedu.dailyplanner.model.task.Date;
import seedu.dailyplanner.model.task.DateTime;
import seedu.dailyplanner.model.task.ReadOnlyTask;
import seedu.dailyplanner.model.task.Task;

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

	public static boolean withinDateRange(ReadOnlyTask task, String keyword) {
		int keyDate = Integer.parseInt(keyword.substring(0, 2));
		int keyMonth = Integer.parseInt(keyword.substring(3, 5));
		int keyYear = Integer.parseInt(keyword.substring(6));
		Date taskStart = task.getStart().m_date;
		Date taskEnd = task.getEnd().m_date;
		if (taskStart.m_value.equals("") && taskEnd.m_value.equals("")) {
			return false;
		} else if (taskStart.m_value.equals("")) {
			taskStart = taskEnd;
		} else if (taskEnd.m_value.equals("")) {
			taskEnd = taskStart;
		}
		Calendar start = Calendar.getInstance();
		start.set(taskStart.m_year + 1900, taskStart.m_month, taskStart.m_day);
		Calendar searchKey = Calendar.getInstance();
		searchKey.set(keyYear + 1900, keyMonth, keyDate);
		Calendar end = Calendar.getInstance();
		end.set(taskEnd.m_year + 1900, taskEnd.m_month, taskEnd.m_day);
		return (start.compareTo(searchKey) <= 0 && end.compareTo(searchKey) >= 0);
	}
}
