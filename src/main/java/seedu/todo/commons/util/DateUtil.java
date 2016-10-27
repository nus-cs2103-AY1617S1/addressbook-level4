package seedu.todo.commons.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A utility class for Dates and LocalDateTimes
 */
public class DateUtil {

    private static final String FROM_NOW = "later";
    private static final String TILL_NOW = "ago";
    private static final String TODAY = "Today";
    private static final String TOMORROW = "Tomorrow";
    private static final String DAY = "day";
    private static final String DAYS = "days";
    
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
        if (dateTime == null) {
            return null;
        }
        
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
        if (dateTime == null) {
            return null;
        }
        
        LocalDate date = dateTime.toLocalDate();
        long daysDifference = LocalDate.now().until(date, ChronoUnit.DAYS);

        // Consider today's date.
        if (date.isEqual(LocalDate.now())) {
            return TODAY;
        }
        
        if (daysDifference == 1) {
            return TOMORROW;
        }

        // Consider dates up to 6 days from today.
        if (daysDifference > 1 && daysDifference <= 6) {
            return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US);
        }

        // Otherwise, dates should be a relative days ago/from now format.
        return String.format("%d %s %s", Math.abs(daysDifference), 
                StringUtil.pluralizer((int) Math.abs(daysDifference), DAY, DAYS), 
                daysDifference > 0 ? FROM_NOW : TILL_NOW);
    }
    
    /**
     * Formats a LocalDateTime to a short date. Excludes the day of week only if
     * the date is within 2-6 days from now.
     * 
     * @param dateTime   LocalDateTime to format.
     * @return           Formatted shorten day.
     */
    public static String formatShortDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        
        LocalDate date = dateTime.toLocalDate();
        long daysDifference = LocalDate.now().until(date, ChronoUnit.DAYS);
        String dateFormat;
        
        // Don't show dayOfWeek for days d, such that d = {n+2,...,n+6}, where n = date now
        if (daysDifference >= 2 && daysDifference <= 6) {
            dateFormat = "dd MMM";
        } else {
            dateFormat = "E dd MMM";
        }
        
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }
    
    /**
     * Parses a short date (as defined in {@link formatShortDate}) back to a LocalDateTime.
     * We ignore the day of week portion for simplicity, since the shortDate can optionally omit it.
     * 
     * @param shortDateToParse   Date string to format.
     * @return                  Parsed LocalDateTime.
     */
    public static LocalDate parseShortDate(String shortDateToParse) {
        String[] dateParts = shortDateToParse.split(" ");
        String dateString;
        
        // Get the current year to add to the parsing since we cannot parse without a year...
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        
        if (dateParts.length < 2 || dateParts.length > 3) {
            return null;
        }
        
        if (dateParts.length == 3) {
            dateString = String.format("%s %s %d", dateParts[1], dateParts[2], currentYear);
        } else {
            dateString = String.format("%s %s %d", dateParts[0], dateParts[1], currentYear);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return LocalDate.parse(dateString, formatter);
    }
    
    /**
     * Formats a LocalDateTime to a 24-hour time.
     * 
     * @param dateTime   LocalDateTime to format.
     * @return           24-hour time formatted string.
     */
    public static String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        
        return dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    
    /**
     * Formats a LocalDateTime into short date + time format.
     * @param dateTime   LocalDateTime to format.
     * @return           Short date + time formatted string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return String.format("%s %s", formatShortDate(dateTime), formatTime(dateTime));
    }
    
    /**
     * Formats a start date and end date to a date range, which will display only as much info as necessary.
     * @param dateFrom   LocalDateTime from.
     * @param dateTo     LocalDateTime to.
     * @return           Formatted string.
     */
    public static String formatDateFromTo(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom == null && dateTo == null) {
            return "";
        } else if (dateTo == null) {
            // No endDate
            return formatTime(dateFrom);
        } else if (dateFrom.isAfter(dateTo)) {
            // Unhandled error, just ignore endDate and assume it has no endDate
            return formatTime(dateFrom);
        } else if (dateFrom.toLocalDate().equals(dateTo.toLocalDate())) {
            return String.format("%s - %s", formatTime(dateFrom), formatTime(dateTo));
        } else {
            return String.format("%s - %s", formatDateTime(dateFrom), formatDateTime(dateTo));
        }
    }
    
    /**
     * Parses a dateTime string with the standard ISO format {@code yyyy-MM-dd HH:mm:ss}.
     * 
     * @param dateTimeString
     * @return
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

}
