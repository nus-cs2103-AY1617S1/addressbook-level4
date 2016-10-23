//@@author A0142184L
package seedu.address.model.task;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskDateTimeFormatter {
	private static DateTimeFormatter showDateAndTime = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
	private static DateTimeFormatter showTimeOnly = DateTimeFormatter.ofPattern("hh:mm a");
	
	public static String formatToShowDateAndTime(LocalDateTime dateTime) {
        return dateTime.format(showDateAndTime);
	}
	
	public static String formatToShowTimeOnly(LocalTime date) {
        return date.format(showTimeOnly);
	}
}
