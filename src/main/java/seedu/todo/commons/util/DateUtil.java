package seedu.todo.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * A utility class for Dates and LocalDateTimes
 */
public class DateUtil {
	
	/**
	 * Converts a LocalDateTime object to a legacy java.util.Date object.
	 * 
	 * @param dateTime   LocalDateTime object.
	 * @return           Date object.
	 */
	public static Date toDate(LocalDateTime dateTime) {
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Performs a "floor" operation on a LocalDateTime, and returns a new LocalDateTime
	 * with time set to 00:00.
	 * 
	 * @param dateTime   LocalDateTime for operation to be performed on.
	 * @return           "Floored" LocalDateTime.
	 */
	public static LocalDateTime floorDate(LocalDateTime dateTime) {
		return dateTime.toLocalDate().atTime(0, 0);
	}
	
	/**
	 * Formats a LocalDateTime to a relative date. 
	 * Prefers DayOfWeek format, for dates up to 6 days from today.
	 * Otherwise, returns a relative time (e.g. 13 days from now).
	 * 
	 * @param dateTime   LocalDateTime to format.
	 * @return           Formatted relative day.
	 */
	public static String formatDay(LocalDateTime dateTime) {
		LocalDate date = dateTime.toLocalDate();
		long daysDifference = LocalDate.now().until(date, ChronoUnit.DAYS);
		
		String fromNow = "later";
		String tillNow = "ago";
		
		// Consider today's date.
		if (date.isEqual(LocalDate.now()))
			return "Today";
		
		// Consider dates up to 6 days from today.
		if (daysDifference > 0 && daysDifference <= 6)
			return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
		
		// Otherwise, dates should be a relative days ago/from now format.
		return String.format("%d %s %s", Math.abs(daysDifference), 
							 StringUtil.pluralizer((int) Math.abs(daysDifference), "day", "days"), 
							 daysDifference > 0 ? fromNow : tillNow);
	}
	
}
