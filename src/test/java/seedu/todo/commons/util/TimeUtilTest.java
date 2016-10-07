package seedu.todo.commons.util;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Test;

public class TimeUtilTest {
    
    @Test (expected = AssertionError.class)
    public void getTaskDeadlineString_nullEndTime() {
        TimeUtil.getTaskDeadlineString(null);
    }
    
    @Test (expected = AssertionError.class)
    public void getTaskDeadlineString_startTimeAfterEndTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        TimeUtil.getTaskDeadlineString(currentTime.minusHours(1).minusDays(1));
    }
    
    @Test
    public void getTaskDeadlineString_hoursBeforeDeadlines() {
        for (long hoursLeft = 2; hoursLeft < 24; hoursLeft++) {
            LocalDateTime inputTime = LocalDateTime.now().plusHours(hoursLeft).plusMinutes(1);
            String generatedOutput = TimeUtil.getTaskDeadlineString(inputTime);
            String expectedOutput = "in " + String.valueOf(hoursLeft) + " hours";
            assertEquals(generatedOutput, expectedOutput);
        }
    }
    
    @Test
    public void getTaskDeadlineString_anHourBeforeDeadlines() {
        LocalDateTime inputTime = LocalDateTime.now().plusHours(1).plusMinutes(10);
        String generatedOutput = TimeUtil.getTaskDeadlineString(inputTime);
        String expectedOutput = "in an hour";
        assertEquals(generatedOutput, expectedOutput);
    }

}
