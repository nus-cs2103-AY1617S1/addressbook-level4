package seedu.address.logic.parser;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author A0141019U
public class DateParser {

	private static final Pattern[] STANDARD_DATE_FORMATS = new Pattern[] {
			Pattern.compile("^(?<day>\\d{1,2})-|\\s(?<month>\\d{1,2})-|\\s|/(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$"), // dd-MM-yyyy HH:mm
			Pattern.compile("^(?<year>\\d{4})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<day>\\d{1,2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$"), // yyyy-MM-dd HH:mm
			Pattern.compile("^(?<day>\\d{1,2})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$"), // dd-MM-yy HH:mm
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$"), // dd MMM yyyy HH:mm
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$") // dd MMM yy HH:mm
	};

	private static final Pattern[] AMPM_DATE_FORMATS = new Pattern[] {
			Pattern.compile("^(?<day>\\d{1,2})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$"), // dd-MM-yyyy HH:mm am
			Pattern.compile("^(?<year>\\d{4})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<day>\\d{1,2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$"), // yyyy-MM-dd HH:mmpm
			Pattern.compile("^(?<day>\\d{1,2})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$"), // dd-MM-yy HH:mm pm
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$"), // dd MMM yyyy HH:mm am
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$") // dd MMM yy HH:mm am
	};
	
	private static final Pattern[] NO_MINUTES_DATE_FORMATS = new Pattern[] {
			Pattern.compile("^(?<day>\\d{1,2})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<year>\\d{4})\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$"), // dd-MM-yyyy hh am
			Pattern.compile("^(?<year>\\d{4})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<day>\\d{1,2})\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$"), // yyyy-MM-dd hhpm
			Pattern.compile("^(?<day>\\d{1,2})-|\\s|/(?<month>\\d{1,2})-|\\s|/(?<year>\\d{2})\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$"), // dd-MM-yy hh pm
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{4})\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$"), // dd MMM yyyy hh am
			Pattern.compile("^(?<day>\\d{1,2})\\s(?<wordMonth>[a-z]{3})\\s(?<year>\\d{2})\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$") // dd MMM yy hh am
	};
	
	private static final Pattern[] NATURAL_LANGUAGE = new Pattern[] {
			Pattern.compile("^(?<day>today|tomorrow|next week)\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})$"), // today 14:30
			Pattern.compile("^(?<day>today|tomorrow|next week)\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)$"), // tomorrow 2:30 pm
			Pattern.compile("^(?<day>today|tomorrow|next week)\\s(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)$"), // next week 2 pm
			Pattern.compile("^(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s(?<day>today|tomorrow|next week)$"), // 14:30 today
			Pattern.compile("^(?<hour>\\d{1,2}):*(?<minute>\\d{2})\\s*(?<meridiem>am|pm)\\s(?<day>today|tomorrow|next week)$"), // 2:30pm 
			Pattern.compile("^(?<hour>\\d{1,2})\\s*(?<meridiem>am|pm)\\s(?<day>today|tomorrow|next week)$"), // 2 pm next week
	};

	/**
	 * @return LocalDateTime if valid date format, null if unable to parse
	 */
	public static LocalDateTime parse(String dateString) throws ParseException {
		dateString = dateString.trim();

		LocalDateTime dateTime = parseStandardFormat(dateString);

		if (dateTime == null) {
			dateTime = parseAmPmFormat(dateString);
		}
		if (dateTime == null) {
			dateTime = parseNoMinutesAmPmFormat(dateString);
		}
		if (dateTime == null) {
			dateTime = parseNaturalLanguage(dateString);
		}

		return dateTime;
	}


	private static LocalDateTime parseStandardFormat(String dateString) throws ParseException {
		ArrayList<Matcher> matchers = new ArrayList<>();
		for (int i=0; i<STANDARD_DATE_FORMATS.length; i++) {
			matchers.add(STANDARD_DATE_FORMATS[i].matcher(dateString));
		}

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				int year = parseYear(matcher.group("year"));
				int month = parseMonth(matcher.group("month"));
				int day = parseDay(matcher.group("day"));
				int hour = parseHour(matcher.group("hour"), "");
				int minute = parseMinute(matcher.group("minute"));

				return LocalDateTime.of(year, month, day, hour, minute);
			}
		}

		// if matcher did not match
		return null;
	}


	private static LocalDateTime parseAmPmFormat(String dateString) throws ParseException {
		ArrayList<Matcher> matchers = new ArrayList<>();
		for (int i=0; i<AMPM_DATE_FORMATS.length; i++) {
			matchers.add(AMPM_DATE_FORMATS[i].matcher(dateString));
		}

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				int year = parseYear(matcher.group("year"));
				int month = parseMonth(matcher.group("month"));
				int day = parseDay(matcher.group("day"));
				int hour = parseHour(matcher.group("hour"), matcher.group("meridiem"));
				int minute = parseMinute(matcher.group("minute"));

				return LocalDateTime.of(year, month, day, hour, minute);
			}
		}

		// if matcher did not match
		return null;
	}
	
	private static LocalDateTime parseNoMinutesAmPmFormat(String dateString) throws ParseException {
		ArrayList<Matcher> matchers = new ArrayList<>();
		for (int i=0; i<NO_MINUTES_DATE_FORMATS.length; i++) {
			matchers.add(NO_MINUTES_DATE_FORMATS[i].matcher(dateString));
		}

		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				int year = parseYear(matcher.group("year"));
				int month = parseMonth(matcher.group("month"));
				int day = parseDay(matcher.group("day"));
				int hour = parseHour(matcher.group("hour"), matcher.group("meridiem"));

				return LocalDateTime.of(year, month, day, hour, 0);
			}
		}

		// if matcher did not match
		return null;
	}
	
	private static LocalDateTime parseNaturalLanguage(String dateString) throws ParseException {
		ArrayList<Matcher> matchers = new ArrayList<>();
		for (int i=0; i<NATURAL_LANGUAGE.length; i++) {
			matchers.add(NATURAL_LANGUAGE[i].matcher(dateString));
		}
		
		LocalDateTime now = LocalDateTime.now();
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				int day = parseDay(matcher.group("day"));
				
				String meridiem;
				try {
					meridiem = matcher.group("meridiem");
				} catch (IllegalArgumentException e) {
					meridiem = "";
				}
				
				int hour = parseHour(matcher.group("hour"), meridiem);
				
				String minuteString;
				try {
					minuteString = matcher.group("minute");
				} catch (IllegalArgumentException e) {
					minuteString = "0";
				}
				int minute = parseMinute(minuteString);

				return LocalDateTime.of(now.getYear(), now.getMonth(), day, hour, minute);
			}
		}

		// if matcher did not match
		return null;
	}

	private static int parseYear(String yearString) {
		int year;
		if (yearString.length() == 2) {
			year = 2000 + Integer.parseInt(yearString);
		}
		else {
			year = Integer.parseInt(yearString);
		}

		return year;
	}

	private static int parseMonth(String wordMonth) throws ParseException {
		try {
			int month = Integer.parseInt(wordMonth);

			if (month < 1 || month > 12) {
				throw new ParseException("Month is not within valid bounds 1 - 12 inclusive", -1);
			}
			else {
				return month;
			}
		}
		catch (NumberFormatException e) {

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
				throw new ParseException("Month is not an integer or one of the standard 3 letter abbreviations.", -1);
			}

		}
	}

	private static int parseDay(String dayString) throws ParseException {
		int day = Integer.parseInt(dayString);

		if (day < 1 || day > 31) {
			throw new ParseException("Day is not within valid bounds 1 - 31 inclusive", -1);
		}
		else {
			return day;
		}
	}

	private static int parseHour(String hour12, String meridien) throws ParseException {
		meridien = meridien.toLowerCase();
		int hour;

		if (meridien.equals("pm")) {
			hour = 12 +Integer.parseInt(hour12);
		}
		else {
			hour = Integer.parseInt(hour12);
		}

		if (hour > 23) {
			throw new ParseException("Hour is not within valid bounds 0 - 23 inclusive", -1);
		}
		else {
			return hour;
		}
	}

	private static int parseMinute(String minuteString) throws ParseException {
		int minute = Integer.parseInt(minuteString);

		if (minute < 0 || minute > 60) {
			throw new ParseException("Minute is not within valid bounds 0 - 60 inclusive", -1);
		}
		else {
			return minute;
		}
	}

}
