package seedu.address.commons.util;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

/**
 * Helper functions for anything with regards to date and time.
 */
public class DateTimeUtil {

	private static final String[] dateFormats = {
			"yyyy-M-d",
			"yyyy/M/d",
			"yyyy M d",
			"yyyy MMM d",
			"yyyy MMMM d",
			"d/M/yyyy", 
			"d/MMM/yyyy",
			"d/MMMM/yyyy",
			"d-M-yyyy",
			"d-MMM-yyyy",
			"d-MMMM-yyyy",
			"d M yyyy",
			"d MMM yyyy",
			"d MMMM yyyy",
			"d-MMM",
			"d/MMM",
			"d MMM",
			"MMMM d",
			"MMM d",
			"d/M", 
			"d-M" };
	
	private static final String[] timeFormats = {
			"hh:mm:ss a",
			"hh:mm a",
			"hh:m:ss a",
			"hh:m a",
			"h:mm:ss a",
			"h:mm a",
			"h:m:ss a",
			"h:m a",
			"HH:mm:ss",
			"HH:mm",
			"HH:m:ss",
			"HH:m",
			"H:mm:ss",
			"H:mm",
			"H:m:ss",
			"H:m"};
	
	public static boolean isValidDateString(String dateString) {
		dateString = dateString.trim();
		for(String formatString : dateFormats) {
			try {
				System.out.println(dateString);
				LocalDate.parse(dateString.trim(), DateTimeFormatter.ofPattern(formatString));
				return true;
			} catch (DateTimeParseException e) {}
		}
		return false;
	}
	
	
	public static LocalDate parseDateString(String dateString) {
		dateString = dateString.trim();
		for(String formatString : dateFormats) {
			try {
				return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(formatString));
			} catch (DateTimeParseException e) {}
		}
		return null; 
	}
	
	public static boolean isValidTimeString(String timeString) {
		timeString = timeString.trim().toUpperCase();
		for(String formatString : timeFormats) {
			try {
				LocalTime.parse(timeString.trim(), DateTimeFormatter.ofPattern(formatString));
				return true;
			} catch (DateTimeParseException e) {}
		}
		return false;
	}
	
	
	public static LocalTime parseTimeString(String timeString) {
		timeString = timeString.trim().toUpperCase();
		for(String formatString : timeFormats) {
			try {
				return LocalTime.parse(timeString, DateTimeFormatter.ofPattern(formatString));
			} catch (DateTimeParseException e) {}
		}
		
		return null; 
	}
	
	public static String prettyPrintDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("dd MMM''yy"));
	}
	
	public static String prettyPrintTime(LocalTime time) {
		return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
	}
	
}

/*
2001-01-05      - yyyy-MM-dd 
2001/01/05      - yyyy/MM/dd
01/05/2001      - dd/MM/yyyy 
01-05-2001      - dd-MM-yyyy 
2001 january 5  - yyyy MMMMM d
2001 5 january  - yyyy d MMMMM
january 5 2001  - MMMMM d yyyy 
5 january 2001  - d MMMMM yyyy
january 5       - MMMMM d
5 january       - d MMMMM
*/
