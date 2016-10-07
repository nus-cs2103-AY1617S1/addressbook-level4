package seedu.todo.commons.util;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Test;

public class TimeUtilTest {
    
    @Test (expected = AssertionError.class)
    public void getTaskDeadlineString_nullEndTime() {
        TimeUtil.getTaskDeadlineString(null);
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
        String expectedOutput = "in 1 hour";
        
        LocalDateTime inputTime1 = LocalDateTime.now().plusHours(1);
        String generatedOutput1 = TimeUtil.getTaskDeadlineString(inputTime1);
        assertEquals(generatedOutput1, expectedOutput);
        
        LocalDateTime inputTime2 = LocalDateTime.now().plusHours(1).plusMinutes(10);
        String generatedOutput2 = TimeUtil.getTaskDeadlineString(inputTime2);
        assertEquals(generatedOutput2, expectedOutput);
    }
    
    @Test
    public void getTaskDeadlineString_minutesBeforeDeadlines() {
        for (long minutesLeft = 2; minutesLeft < 60; minutesLeft++) {
            LocalDateTime inputTime = LocalDateTime.now().plusHours(minutesLeft).plusMinutes(1);
            String generatedOutput = TimeUtil.getTaskDeadlineString(inputTime);
            String expectedOutput = "in " + String.valueOf(minutesLeft) + " hours";
            assertEquals(generatedOutput, expectedOutput);
        }
    }
    
    @Test
    public void getTaskDeadlineString_aMinuteBeforeDeadlines() {
        String expectedOutput = "in 1 minute";
        
        LocalDateTime inputTime1 = LocalDateTime.now().plusMinutes(1);
        String generatedOutput1 = TimeUtil.getTaskDeadlineString(inputTime1);
        assertEquals(generatedOutput1, expectedOutput);
        
        LocalDateTime inputTime2 = LocalDateTime.now().plusMinutes(1).plusSeconds(10);
        String generatedOutput2 = TimeUtil.getTaskDeadlineString(inputTime2);
        assertEquals(generatedOutput2, expectedOutput);
    }
    
    

}
