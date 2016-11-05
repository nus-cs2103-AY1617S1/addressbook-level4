package seedu.taskitty.commons.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import seedu.taskitty.model.task.Task;
import seedu.taskitty.model.task.TaskDate;
import seedu.taskitty.model.task.TaskTime;

/**
 * Converts a String to Date and vice versa.
 */
public class DateTimeUtil {

    public static String formatDateForUI(TaskDate date) {
        return TaskDate.DATE_FORMATTER_UI.format(date.getDate());
    }

    public static String formatTimeForUI(TaskTime time) {
        return TaskTime.TIME_FORMATTER_UI.format(time.getTime());
    }

    // @@author A0130853L
    public static LocalDate createCurrentDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        return LocalDate.parse(date, TaskDate.DATE_FORMATTER_STORAGE);
    }

    /**
     * For use by the status bar footer, it appends a "(today)" string behind if
     * date == today.
     *
     */
    public static String createUISpecifiedDateString(LocalDate date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String dateString = date.format(df);
        if (isToday(date)) {
            dateString += " (Today)";
        }
        return dateString;

    }

    /**
     * creates a current Time object for comparison with current time.
     * 
     * @return
     */
    public static LocalDateTime createCurrentTime() {
        return LocalDateTime.now();
    }

    public static boolean isToday(LocalDate date) {
        return date.equals(createCurrentDate());
    }

    /**
     * This method specifically checks if a deadline task is overdue.
     */
    public static boolean isOverdue(Task t) {
        LocalDateTime currentTime = createCurrentTime();
        LocalDateTime taskTime = t.getPeriod().getEndDate().getDate().atTime(t.getPeriod().getEndTime().getTime());
        return currentTime.isAfter(taskTime);
    }
}
