package tars.commons.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tars.model.task.DateTime;

/**
 * Date Time Utility package
 */
public class DateTimeUtil {

    public static final int DATETIME_FIRST_HOUR_OF_DAY = 0;
    public static final int DATETIME_FIRST_MINUTE_OF_DAY = 0;
    public static final int DATETIME_FIRST_SECOND_OF_DAY = 0;
    public static final int DATETIME_LAST_HOUR_OF_DAY = 23;
    public static final int DATETIME_LAST_MINUTE_OF_DAY = 59;
    public static final int DATETIME_LAST_SECOND_OF_DAY = 59;

    private static final String DATETIME_DAY = "day";
    private static final String DATETIME_WEEK = "week";
    private static final String DATETIME_MONTH = "month";
    private static final String DATETIME_YEAR = "year";
    private static final int DATETIME_INCREMENT = 1;
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    private static final DateTimeFormatter stringFormatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
    private static final DateTimeFormatter stringFormatterWithoutTime =
            DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter stringFormatterWithoutDate =
            DateTimeFormatter.ofPattern("HHmm");

    public static String MESSAGE_FREE_TIME_SLOT =
            StringUtil.STRING_NEWLINE + "%1$s. %2$shrs to %3$shrs (%4$s)";
    private static String MESSAGE_DURATION = "%1$s hr %2$s min";

    // @@author A0139924W
    /**
     * Extracts the new task's dateTime from the string arguments.
     * 
     * @return String[] with first index being the startDate time and second index being the end
     *         date time
     */
    public static String[] parseStringToDateTime(String dateTimeArg) {
        return NattyDateTimeUtil.parseStringToDateTime(dateTimeArg);
    }
    // @@author

    // @@author A0121533W
    /**
     * Checks if given endDateTime is within today and the end of this week
     */
    public static boolean isWithinWeek(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime endThisWeek =
                    now.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                            .withHour(0).withMinute(0).withSecond(0);
            return endDateTime.isAfter(now)
                    && endDateTime.isBefore(endThisWeek);
        }
    }

    // @@author A0121533W
    /**
     * Checks if given endDateTime is before the end of today
     */
    public static boolean isOverDue(LocalDateTime endDateTime) {
        if (endDateTime == null) {
            return false;
        } else {
            LocalDateTime now = LocalDateTime.now();
            return endDateTime.isBefore(now);
        }
    }

    // @@author A0124333U
    /**
     * Checks whether the dateTimeQuery falls within the range of the dateTimeSource i.e.
     * dateTimeQuery startDateTime is equals to or before the dateTimeSource endDateTime &&
     * dateTimeQuery endDateTime is equals to or after the dateTimeSource startDateTime
     * 
     * @param dateTimeSource
     * @param dateTimeQuery
     */
    public static boolean isDateTimeWithinRange(DateTime dateTimeSource,
            DateTime dateTimeQuery) {

        // Return false if task is a floating task (i.e. no start or end
        // dateTime
        if (dateTimeSource.getEndDate() == null) {
            return false;
        }

        DateTime dateTime1 = fillDateTime(dateTimeSource);
        DateTime dateTime2 = fillDateTime(dateTimeQuery);

        return !dateTime1.getEndDate().isBefore(dateTime2.getStartDate())
                && !dateTime1.getStartDate().isAfter(dateTime2.getEndDate());
    }

    /**
     * Checks whether the dateTimeQuery conflicts with the dateTimeSource i.e. dateTimeQuery
     * endDateTime occurs after the dateTimeSource startDateTime && dateTimeQuery startDateTime
     * occurs before the dateTimeSource endDateTime
     */
    public static boolean isDateTimeConflicting(DateTime dateTimeSource,
            DateTime dateTimeQuery) {

        // Return false if task is a floating task (i.e. no start or end
        // dateTime
        if (dateTimeSource.getEndDate() == null) {
            return false;
        }

        DateTime dateTime1 = fillDateTime(dateTimeSource);
        DateTime dateTime2 = fillDateTime(dateTimeQuery);

        return dateTime1.getEndDate().isAfter(dateTime2.getStartDate())
                && dateTime1.getStartDate().isBefore(dateTime2.getEndDate());
    }

    private static DateTime fillDateTime(DateTime filledDateTime) {
        DateTime dateTimeToFill = new DateTime();

        dateTimeToFill.setEndDateTime(filledDateTime.getEndDate());

        if (filledDateTime.getStartDate() != null) {
            dateTimeToFill.setStartDateTime(filledDateTime.getStartDate());
        } else {
            dateTimeToFill.setStartDateTime(filledDateTime.getEndDate());
        }
        return dateTimeToFill;
    }


    /**
     * Returns an arraylist of free datetime slots in a specified date
     */
    public static ArrayList<DateTime> getListOfFreeTimeSlotsInDate(
            DateTime dateToCheck,
            ArrayList<DateTime> listOfFilledTimeSlotsInDate) {
        ArrayList<DateTime> listOfFreeTimeSlots = new ArrayList<DateTime>();
        LocalDateTime startDateTime = dateToCheck.getStartDate();
        LocalDateTime endDateTime;

        for (DateTime dt : listOfFilledTimeSlotsInDate) {
            if (dt.getStartDate() == null) {
                continue;
            } else {
                endDateTime = dt.getStartDate();
            }

            if (startDateTime.isBefore(endDateTime)) {
                listOfFreeTimeSlots
                        .add(new DateTime(startDateTime, endDateTime));
            }

            if (startDateTime.isBefore(dt.getEndDate())) {
                startDateTime = dt.getEndDate();
            }
        }

        if (startDateTime.isBefore(dateToCheck.getEndDate())) {
            listOfFreeTimeSlots
                    .add(new DateTime(startDateTime, dateToCheck.getEndDate()));
        }

        return listOfFreeTimeSlots;
    }


    public static String getDayAndDateString(DateTime dateTime) {
        StringBuilder sb = new StringBuilder();

        sb.append(dateTime.getEndDate().getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .append(StringUtil.STRING_COMMA).append(dateTime.getEndDate()
                        .format(stringFormatterWithoutTime));

        return sb.toString();
    }


    public static String getStringOfFreeDateTimeInDate(DateTime dateToCheck,
            ArrayList<DateTime> listOfFreeTimeSlotsInDate) {
        StringBuilder sb = new StringBuilder();

        sb.append(getDayAndDateString(dateToCheck))
                .append(StringUtil.STRING_COLON);

        int counter = 1;

        for (DateTime dt : listOfFreeTimeSlotsInDate) {
            sb.append(String.format(MESSAGE_FREE_TIME_SLOT, counter,
                    dt.getStartDate().format(stringFormatterWithoutDate),
                    dt.getEndDate().format(stringFormatterWithoutDate),
                    getDurationBetweenTwoLocalDateTime(dt.getStartDate(),
                            dt.getEndDate())));
            counter++;
        }

        return sb.toString();
    }

    public static String getDurationBetweenTwoLocalDateTime(
            LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return String.format(MESSAGE_DURATION, hours, minutes);
    }

    // @@author A0139924W
    /**
     * Modify the date based on the new hour, min and sec
     */
    public static Date setDateTime(Date toBeEdit, int hour, int min, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toBeEdit);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        toBeEdit = calendar.getTime();

        return toBeEdit;
    }

    // @@author A0140022H
    /**
     * Modifies the date based on the frequency for recurring tasks.
     */
    public static String modifyDate(String dateToModify, String frequency) {
        LocalDateTime date = LocalDateTime.parse(dateToModify, formatter);

        switch (frequency.toLowerCase()) {
            case DATETIME_DAY:
                date = date.plusDays(DATETIME_INCREMENT);
                break;
            case DATETIME_WEEK:
                date = date.plusWeeks(DATETIME_INCREMENT);
                break;
            case DATETIME_MONTH:
                date = date.plusMonths(DATETIME_INCREMENT);
                break;
            case DATETIME_YEAR:
                date = date.plusYears(DATETIME_INCREMENT);
                break;
        }

        dateToModify = date.format(stringFormatter);
        return dateToModify;
    }

    public static LocalDateTime setLocalTime(LocalDateTime dateTime, int hour,
            int min, int sec) {
        return LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(),
                dateTime.getDayOfMonth(), hour, min, sec);
    }
}
