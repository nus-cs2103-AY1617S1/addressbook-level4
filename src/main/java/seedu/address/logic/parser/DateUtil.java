package seedu.address.logic.parser;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DateUtil {

	private static final Pattern[] STANDARD_DATE_FORMAT_REGEXPS = new Pattern[] {
		Pattern.compile("^(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})$"), // dd-MM-yyyy
		Pattern.compile("^(?<year>\\d{4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})$"), // yyyy-MM-dd
		Pattern.compile("^(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})\\s(?<hour>\\d{1,2}):(?<minute>)\\d{2})$"), // dd-MM-yyyy HH:mm
		Pattern.compile("^(?<year>\\d{4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})\\s(?<hour>\\d{1,2}):(?<minute>)\\d{2})$"), // yyyy-MM-dd HH:mm
	};

	private static final Pattern[] SHORT_YEARS_DATE_FORMAT_REGEXPS = new Pattern[] {
		Pattern.compile("^(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<shortYear>\\d{2})$"), // dd-MM-yy
		Pattern.compile("^(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<shortYear>\\d{2})\\s(?<hour>\\d{1,2}):(?<minute>)\\d{2})$"), // dd-MM-yy HH:mm
	};

	private static final Pattern[] WORD_MONTHS_DATE_FORMAT_REGEXPS = new Pattern[] {
		Pattern.compile("(?i)^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{4})$"), // dd MMM yyyy
		Pattern.compile("(?i)^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{4})\\s(?<hour>\\d{1,2}):(?<minute>)\\d{2})$"), // dd MMM yyyy HH:mm
	};

	private static final Pattern[] AMPM_DATE_FORMAT_REGEXPS = new Pattern[] {
		Pattern.compile("^(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})\\s(?<hour12>\\d{1,2}):(?<minute>)\\d{2})\\s*(?<meridiem>am|pm)$"), // dd-MM-yyyy hh:mm am
		Pattern.compile("^(?<year>\\d{4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})\\s(?<hour12>\\d{1,2}):(?<minute>)\\d{2})\\s*(?<meridiem>am|pm)$"), // yyyy-MM-dd hh:mm pm
		Pattern.compile("^(?<day>\\d{1,2})\\s(?<month>[a-z]{3})\\s(?<year>\\d{4})\\s(?<hour12>\\d{1,2}):(?<minute>)\\d{2})\\s*(?<meridiem>am|pm)$"), // dd MMM yyyy hh:mm am
	};

	/**
	 * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
	 * format is unknown. You can simply extend DateUtil with more formats if needed.
	 * @param dateString The date string to determine the SimpleDateFormat pattern for.
	 * @return The matching SimpleDateFormat pattern, or null if format is unknown.
	 * @see SimpleDateFormat
	 */
	public static LocalDateTime determineDateFormat(String dateString) {
		for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
			if (dateString.toLowerCase().matches(regexp)) {
				//	            LocalDateTime.of(arg0, arg1)
			}
		}
		return null; // Unknown format.
	}
	
	private static int processShortYear(String shortYear) {
		return 2000 + Integer.parseInt(shortYear);
	}
	
	private static int processWordMonth(String wordMonth) throws IllegalArgumentException {
		switch (wordMonth.toLowerCase()) {
		case "jan":
			return 1;
		case "feb":
			return 2;
		case "mar":
			return 3;
		case "apr":
			return 4;
		case "may":
			return 5;
		case "jun":
			return 6;
		case "jul":
			return 7;
		case "aug":
			return 8;
		case "sep":
			return 9;
		case "oct":
			return 10;
		case "nov":
			return 11;
		case "dec":
			return 12;
		default:
			throw new IllegalArgumentException("Unable to parse month");
		}
		
	}
}
