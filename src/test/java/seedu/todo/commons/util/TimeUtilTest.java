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
        timeUtil.getTaskDeadlineString(null);
    }
    
    @Test
    public void getTaskDeadlineString_dueNow() {
        String expectedOutput = "due now";
        
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineString(dueTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 59);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineString(dueTime2);
        assertEquals(expectedOutput, generatedOutput2);
    }
    
    @Test
    public void getTaskDeadlineString_dueLessThanAMinute() {
        TimeUtil timeUtil = new ModifiedTimeUtil(LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00));
        String expectedOutput = "in less than a minute";
        
        LocalDateTime inputTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 01);
        String generatedOutput1 = timeUtil.getTaskDeadlineString(inputTime1);
        assertEquals(expectedOutput, generatedOutput1);
        
        LocalDateTime inputTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 59);
        String generatedOutput2 = timeUtil.getTaskDeadlineString(inputTime2);
        assertEquals(expectedOutput, generatedOutput2);
    }
}
