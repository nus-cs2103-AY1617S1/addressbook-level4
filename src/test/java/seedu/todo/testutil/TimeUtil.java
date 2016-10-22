package seedu.todo.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static final LocalDateTime now = LocalDateTime.now();
    
    public static LocalDateTime today() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    }
    
    public static LocalDateTime tomorrow() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
    }

    //@@author A0315805H
    /**
     * Checks against system time if the provided dueTime is before system time.
     *
     * @param dueTime The due time to check against with the current system time.
     * @return Returns true if the provided dueTime is before system time.
     */
    public static boolean isOverdue(LocalDateTime dueTime) {
        return dueTime.isBefore(LocalDateTime.now());
    }
}
