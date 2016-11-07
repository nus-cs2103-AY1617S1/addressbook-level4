package tars.ui.formatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tars.commons.util.StringUtil;
import tars.model.task.DateTime;

// @@author A0139924W
/**
 * Container for formatting dates
 */
public class DateFormatter {
    private static final String DATE_FORMAT_DASH = " - ";
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("E, MMM dd yyyy");
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter NORMAL_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("E, MMM dd yyyy hh:mm a");
    private static final DateTimeFormatter SAME_DAY_DATE_FORMAT =
            DateTimeFormatter.ofPattern("ddMMyyyy");

    private static final String TODAY_PREFIX_TEXT = "Today at ";
    private static final String TOMORROW_PREFIX_TEXT = "Tomorrow at ";

    public static String formatDate(DateTime dateTime) {
        LocalDateTime startDateTime = dateTime.getStartDate();
        LocalDateTime endDateTime = dateTime.getEndDate();

        if (startDateTime != null && endDateTime == null) {
            return DateFormatter.generateSingleDateFormat(startDateTime);
        } else if (startDateTime == null && endDateTime != null) {
            return DateFormatter.generateSingleDateFormat(endDateTime);
        } else if (startDateTime != null && endDateTime != null) {
            return DateFormatter.generateDateRangeFormat(startDateTime,
                    endDateTime);
        } else {
            return StringUtil.EMPTY_STRING;
        }
    }

    public static String generateSingleDateFormat(LocalDateTime firstDate) {
        if (isToday(firstDate)) {
            return TODAY_PREFIX_TEXT + TIME_FORMAT.format(firstDate);
        } else if (isTomorrow(firstDate)) {
            return TOMORROW_PREFIX_TEXT + TIME_FORMAT.format(firstDate);
        } else {
            return NORMAL_DATETIME_FORMAT.format(firstDate);
        }
    }

    public static String generateDateRangeFormat(LocalDateTime firstDate,
            LocalDateTime secondDate) {
        if (isSameDay(firstDate, secondDate)) {
            return DATE_FORMAT.format(firstDate) + StringUtil.STRING_WHITESPACE
                    + TIME_FORMAT.format(firstDate) + DATE_FORMAT_DASH
                    + TIME_FORMAT.format(secondDate);
        } else {
            return NORMAL_DATETIME_FORMAT.format(firstDate) + DATE_FORMAT_DASH
                    + NORMAL_DATETIME_FORMAT.format(secondDate);
        }
    }

    private static boolean isToday(LocalDateTime firstDate) {
        return isSameDay(firstDate, LocalDateTime.now());
    }

    private static boolean isTomorrow(LocalDateTime firstDate) {
        return isSameDay(firstDate, LocalDateTime.now().plusDays(1));
    }

    private static boolean isSameDay(LocalDateTime firstDate,
            LocalDateTime secondDate) {
        return SAME_DAY_DATE_FORMAT.format(firstDate)
                .equals(SAME_DAY_DATE_FORMAT.format(secondDate));
    }

}
