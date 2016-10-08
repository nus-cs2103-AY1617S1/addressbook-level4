package seedu.todo.commons.util;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

import org.junit.Test;

/**
 * Tests TimeUtil class
 * In this test, today is 3 May 2016, 13:15:10
 */
public class TimeUtilTest {
    
    /**
     * A subclass of TimeUtil that provides the ability to override the current system time, 
     * so that time sensitive components can be conveniently tested. 
     */
    private class ModifiedTimeUtil extends TimeUtil {
        
        /**
         * Construct a ModifiedTimeUtil object overriding the current time with Clock object.
         * Is only used for dependency injection in testing time sensitive components.
         */
        private ModifiedTimeUtil (Clock clock) {
            this.clock = clock;
        }
        
        /**
         * Construct a ModifiedTimeUtil object overriding the current time with LocalDateTime object.
         * Is only used for dependency injection in testing time sensitive components.
         */
        public ModifiedTimeUtil (LocalDateTime pseudoCurrentTime) {
            this(Clock.fixed(pseudoCurrentTime.toInstant(ZoneId.systemDefault().getRules().getOffset(pseudoCurrentTime)), ZoneId.systemDefault()));
        }
    }
        
    @Test (expected = AssertionError.class)
    public void getTaskDeadlineString_nullEndTime() {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.getTaskDeadlineText(null);
    }
    
    @Test
    public void getTaskDeadlineText_dueNow() {
        String expectedOutput = "due now";
        
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 59);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        assertEquals(expectedOutput, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 30);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        assertEquals(expectedOutput, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_dueLessThanAMinute() {
        String expectedOutput = "in less than a minute";
        
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 01);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 59);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        assertEquals(expectedOutput, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 30);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        assertEquals(expectedOutput, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_aMinuteBeforeDeadline() {
        String expectedOutput = "in 1 minute";
        
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 58, 30);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        assertEquals(expectedOutput, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 58, 01);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        assertEquals(expectedOutput, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_aMinuteAfterDeadline() {
        String expectedOutput = "1 minute ago";
        
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 30);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        assertEquals(expectedOutput, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 59);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        assertEquals(expectedOutput, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_minutesBeforeDeadline() {
        for (int minutesLeft = 2; minutesLeft < 59; minutesLeft++) {
            String expectedOutput = "in " + minutesLeft + " minutes";
            
            LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime1 = dueTime1.minusMinutes(minutesLeft);            
            TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
            String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
            assertEquals(expectedOutput, generatedOutput1);
            
            LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime2 = dueTime2.minusMinutes(minutesLeft).minusSeconds(30);
            TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
            String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
            assertEquals(expectedOutput, generatedOutput2);
            
            LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime3 = dueTime3.minusMinutes(minutesLeft).minusSeconds(59);
            TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
            String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
            assertEquals(expectedOutput, generatedOutput3);
        }
    }
}
