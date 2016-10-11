package seedu.todo.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static LocalDateTime today() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
    }
    
    public static LocalDateTime tomorrow() {
        return LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
    }
}
