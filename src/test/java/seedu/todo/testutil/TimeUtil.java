package seedu.todo.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final LocalDateTime now = LocalDateTime.now();
    private static final String FORMAT_FULL_DATE = "d MMMM yyyy, h:mm a";
    
    public static LocalDateTime today() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    }
    
    public static LocalDateTime tomorrow() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
    }

    //@@author A0135805H
    /**
     * Checks against system time if the provided dueTime is before system time.
     *
     * @param dueTime The due time to check against with the current system time.
     * @return Returns true if the provided dueTime is before system time.
     */
    public static boolean isOverdue(LocalDateTime dueTime) {
        return dueTime.isBefore(LocalDateTime.now());
    }

    /**
     * Gets the complete date time text in the following format:
     *      12 August 2015, 12:34 PM
     */
    public static String getDateTimeText(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(FORMAT_FULL_DATE));
    }
}
