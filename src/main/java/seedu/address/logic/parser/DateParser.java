package seedu.address.logic.parser;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author A0141019U
public class DateParser {

	private static final Pattern[] STANDARD_DATE_FORMATS = new Pattern[] {
			Pattern.compile("(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // dd-MM-yyyy HH:mm am
			Pattern.compile("(?<year>\\d{4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // yyyy-MM-dd HH:mmpm
			Pattern.compile("(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // dd-MM-yy HH:mm pm
			Pattern.compile("(?<day>\\d{1,2})-(?<month>[a-z]{3})-(?<year>\\d{4})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // dd-MMM-yyyy HH:mm am
			Pattern.compile("(?<day>\\d{1,2})-(?<month>[a-z]{3})-(?<year>\\d{2})\\s(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // dd-MMM-yy HH:mm am

			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{4})"), // HH:mm am dd-MM-yyyy 
			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s(?<year>\\d{4})-(?<month>\\d{1,2})-(?<day>\\d{1,2})"), // HH:mmpm yyyy-MM-dd 
			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s(?<day>\\d{1,2})-(?<month>\\d{1,2})-(?<year>\\d{2})"), // HH:mm pm dd-MM-yy 
			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s(?<day>\\d{1,2})-(?<month>[a-z]{3})-(?<year>\\d{4})"), // HH:mm am dd-MMM-yyyy 
			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s(?<day>\\d{1,2})-(?<month>[a-z]{3})-(?<year>\\d{2})") // HH:mm am dd-MMM-yy 
	};

	private static final Pattern[] NATURAL_LANGUAGE = new Pattern[] {
			Pattern.compile("(?<day>[a-zA-Z\\s]+)?\\s*(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?"), // tomorrow 2:30 pm
			Pattern.compile("(?<hour>\\d{1,2}):*(?<minute>\\d{2})?\\s*(?<meridiem>am|pm)?\\s*(?<day>[a-zA-Z\\s]+)?") // 2pm tomorrow
	};

	
	/**
	 * @return LocalDateTime if valid date format
	 * @throws ParseException if unable to parse
	 */
	public static LocalDateTime parse(String dateString) throws ParseException {
		dateString = dateString.trim();

		LocalDateTime dateTime = parseNaturalLanguage(dateString);
		if (dateTime == null) {
			dateTime = parseStandardFormat(dateString);
		}
		
		if (dateTime == null) {
			throw new ParseException("Failed to parse date and time.", -1);
		}
		else {
			System.out.println("date parser result: " + dateTime);
			return dateTime;
		}
	}

	private static LocalDateTime parseNaturalLanguage(String dateString) throws ParseException {
		ArrayList<Matcher> matchers = new ArrayList<>();
		for (int i=0; i<NATURAL_LANGUAGE.length; i++) {
			matchers.add(NATURAL_LANGUAGE[i].matcher(dateString));
		}
		
		for (Matcher matcher : matchers) {
			if (matcher.matches()) {
				Optional<String> dayOpt = Optional.ofNullable(matcher.group("day"));
				System.out.println(dayOpt);
				String dayWord = dayOpt.orElse("today").trim();
				LocalDateTime dayMonthYear = parseDayWord(dayWord);
				
				Optional<String> meridiemOpt = Optional.ofNullable(matcher.group("meridiem"));
				int hour = parseHour(matcher.group("hour"), meridiemOpt.orElse(""));
				
				Optional<String> minuteOpt = Optional.ofNullable(matcher.group("minute"));
				int minute = parseMinute(minuteOpt.orElse("0"));

				return LocalDateTime.of(dayMonthYear.getYear(), dayMonthYear.getMonth(), dayMonthYear.getDayOfMonth(), hour, minute);
			}
		}

		// if matcher did not match
		return null;
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
				int day = parseDayNumber(matcher.group("day"));
				
				Optional<String> meridiemOpt = Optional.ofNullable(matcher.group("meridiem"));
				int hour = parseHour(matcher.group("hour"), meridiemOpt.orElse(""));

				Optional<String> minuteOpt = Optional.ofNullable(matcher.group("minute"));
				int minute = parseMinute(minuteOpt.orElse("0"));

				return LocalDateTime.of(year, month, day, hour, minute);
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
	
	private static LocalDateTime parseDayWord(String dayString) throws ParseException {
		LocalDateTime now = LocalDateTime.now();
		
		switch (dayString.toLowerCase()) {
		case "today":
			return now;
		case "tmr":
		case "tomorrow":
			return now.plusDays(1);
		case "next week":
			return now.plusDays(7);
		
		case "mon":
		case "monday":
			return now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		case "tue":
		case "tuesday":
			return now.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
		case "wed":
		case "wednesday":
			return now.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
		case "thu":
		case "thursday":
			return now.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
		case "fri":
		case "friday":
			return now.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		case "sat":
		case "saturday":
			return now.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
		case "sun":
		case "sunday":
			return now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		default:
			throw new ParseException("Day is not today, tomorrow, next week or day of week.", -1);
		}
	}

	private static int parseDayNumber(String dayString) throws ParseException {
		int day = Integer.parseInt(dayString);

		if (day < 1 || day > 31) {
			throw new ParseException("Day is not within valid bounds 1 - 31 inclusive", -1);
		}
		else {
			return day;
		}
	}

	private static int parseHour(String hour12, String meridiem) throws ParseException {
		meridiem = meridiem.toLowerCase();
		int hour;
		
		if (meridiem.equals("am") && hour12.equals("12")) {
			hour = 0;
		}
		else if (meridiem.equals("pm") && !hour12.equals("12")) {
			hour = 12 + Integer.parseInt(hour12);
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
	
	public static void main(String[] args) {
		try {
			LocalDateTime date1 = DateParser.parse("12-12-12 05:00");
			System.out.println(date1.toString());
			LocalDateTime date2 = DateParser.parse("12-12-12 05:00");
			System.out.println(date2.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
