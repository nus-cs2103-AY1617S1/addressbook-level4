package seedu.todo.commons.util;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests TimeUtil class
 * In this test, today is 3 May 2016, 13:15:10
 */
public class TimeUtilTest {
    
    private TimeUtil timeUtil;
    private LocalDateTime pseudoCurrentTime;
    
    @Before
    public void initialiseTimeUtil() {
        pseudoCurrentTime = LocalDateTime.of(2016, Month.MAY, 3, 13, 15, 10);
        Clock clockStub = Clock.fixed(pseudoCurrentTime.toInstant(ZoneId.systemDefault().getRules().getOffset(pseudoCurrentTime)), ZoneId.systemDefault());
        timeUtil = new TimeUtil(clockStub);
    }
        
    @Test (expected = AssertionError.class)
    public void getTaskDeadlineString_nullEndTime() {
        timeUtil.getTaskDeadlineString(null);
    }
    
    @Test 
    public void getTaskDeadlineString_differentYearsBeforeDeadlines() {
        LocalDateTime inputTime1 = LocalDateTime.of(2018, Month.JUNE, 6, 17, 20, 15);
        String expectedOutput1 = "by 6 June 2018, 5:20 PM";
        String generatedOutput1 = timeUtil.getTaskDeadlineString(inputTime1);
        assertEquals(generatedOutput1, expectedOutput1);
        
        LocalDateTime inputTime2 = LocalDateTime.of(2017, Month.DECEMBER, 11, 11, 49, 30);
        String expectedOutput2 = "by 11 December 2017, 11:49 AM";
        String generatedOutput2 = timeUtil.getTaskDeadlineString(inputTime2);
        assertEquals(generatedOutput2, expectedOutput2);
        
        LocalDateTime inputTime3 = LocalDateTime.of(2017, Month.JANUARY, 3, 13, 15, 10);
        String expectedOutput3 = "by 3 January 2017, 1:15 PM";
        String generatedOutput3 = timeUtil.getTaskDeadlineString(inputTime3);
        assertEquals(generatedOutput3, expectedOutput3);
    }
    
    @Test
    public void getTaskDeadlineString_hoursBeforeDeadlines() {
        for (long hoursLeft = 2; hoursLeft < 24; hoursLeft++) {
            LocalDateTime inputTime = pseudoCurrentTime.plusHours(hoursLeft).plusMinutes(1);
            String generatedOutput = timeUtil.getTaskDeadlineString(inputTime);
            String expectedOutput = "in " + String.valueOf(hoursLeft) + " hours";
            assertEquals(generatedOutput, expectedOutput);
        }
    }
    
    @Test
    public void getTaskDeadlineString_anHourBeforeDeadlines() {
        String expectedOutput = "in 1 hour";
        LocalDateTime inputTime = pseudoCurrentTime.plusHours(1);
        String generatedOutput = timeUtil.getTaskDeadlineString(inputTime);
        assertEquals(generatedOutput, expectedOutput);
    }
    
    @Test
    public void getTaskDeadlineString_minutesBeforeDeadlines() {
        for (long minutesLeft = 2; minutesLeft < 60; minutesLeft++) {
            LocalDateTime inputTime = pseudoCurrentTime.plusMinutes(minutesLeft).plusSeconds(30);
            String generatedOutput = timeUtil.getTaskDeadlineString(inputTime);
            String expectedOutput = "in " + String.valueOf(minutesLeft) + " minutes";
            assertEquals(generatedOutput, expectedOutput);
        }
    }
    
    @Test
    public void getTaskDeadlineString_aMinuteBeforeDeadlines() {
        String expectedOutput = "in 1 minute";
        LocalDateTime inputTime = pseudoCurrentTime.plusMinutes(1).plusSeconds(10);
        String generatedOutput = timeUtil.getTaskDeadlineString(inputTime);
        assertEquals(generatedOutput, expectedOutput);
    }
    
    @Test
    public void getTaskDeadlineString_dueNow() {
        String expectedOutput = "right now";
        
        LocalDateTime inputTime1 = pseudoCurrentTime;
        String generatedOutput1 = timeUtil.getTaskDeadlineString(inputTime1);
        assertEquals(generatedOutput1, expectedOutput);
        
        LocalDateTime inputTime2 = pseudoCurrentTime.plusSeconds(30);
        String generatedOutput2 = timeUtil.getTaskDeadlineString(inputTime2);
        assertEquals(generatedOutput2, expectedOutput);
    }
    
    

}
