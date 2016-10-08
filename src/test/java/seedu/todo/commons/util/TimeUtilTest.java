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
            this.clock = Clock.fixed(pseudoCurrentTime.toInstant(
                    ZoneId.systemDefault().getRules().getOffset(pseudoCurrentTime)), ZoneId.systemDefault());
        }
    }
    
    /**
     * Aids to test taskDeadlineText with a current time and due time, against an expected output.
     */
    private void testTaskDeadlineTextHelper (String expectedOutput, LocalDateTime currentTime, LocalDateTime dueTime) {
        TimeUtil timeUtil = new ModifiedTimeUtil(currentTime);
        String generatedOutput = timeUtil.getTaskDeadlineText(dueTime);
        assertEquals(expectedOutput, generatedOutput);
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
        for (int minutesLeft = 2; minutesLeft <= 59; minutesLeft++) {
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
    
    @Test
    public void getTaskDeadlineText_minutesAfterDeadline() {
        for (int minutesLater = 2; minutesLater <= 59; minutesLater++) {
            String expectedOutput = minutesLater + " minutes ago";
            
            LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime1 = dueTime1.plusMinutes(minutesLater);
            TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
            String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
            assertEquals(expectedOutput, generatedOutput1);
            
            LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime2 = dueTime2.plusMinutes(minutesLater).plusSeconds(30);
            TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
            String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
            assertEquals(expectedOutput, generatedOutput2);
            
            LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
            LocalDateTime currentTime3 = dueTime3.plusMinutes(minutesLater).plusSeconds(59);
            TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
            String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
            assertEquals(expectedOutput, generatedOutput3);
        }
    }
    
    @Test
    public void getTaskDeadlineText_todayBeforeDeadline() {
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 10, 45, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 17, 59, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        String expectedOutput1 = "by today, 5:59 PM";
        assertEquals(expectedOutput1, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 11, 58, 30);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 18, 00, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        String expectedOutput2 = "by tonight, 6:00 PM";
        assertEquals(expectedOutput2, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 00, 00, 00);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 00, 30, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        String expectedOutput3 = "in 30 minutes";
        assertEquals(expectedOutput3, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_todayAfterDeadline() {
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 18, 45, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        String expectedOutput1 = "since 12:00 PM";
        assertEquals(expectedOutput1, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 23, 58, 30);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 16, 50, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        String expectedOutput2 = "since 4:50 PM";
        assertEquals(expectedOutput2, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 00, 30, 00);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 00, 00, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        String expectedOutput3 = "30 minutes ago";
        assertEquals(expectedOutput3, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_tomorrowBeforeDeadline() {
        LocalDateTime currentTime1 = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        LocalDateTime dueTime1 = LocalDateTime.of(2016, Month.MARCH, 21, 12, 00, 00);
        TimeUtil timeUtil1 = new ModifiedTimeUtil(currentTime1);
        String generatedOutput1 = timeUtil1.getTaskDeadlineText(dueTime1);
        String expectedOutput1 = "by tomorrow, 12:00 PM";
        assertEquals(expectedOutput1, generatedOutput1);
        
        LocalDateTime currentTime2 = LocalDateTime.of(2016, Month.MARCH, 20, 23, 50, 00);
        LocalDateTime dueTime2 = LocalDateTime.of(2016, Month.MARCH, 21, 00, 51, 00);
        TimeUtil timeUtil2 = new ModifiedTimeUtil(currentTime2);
        String generatedOutput2 = timeUtil2.getTaskDeadlineText(dueTime2);
        String expectedOutput2 = "by tomorrow, 12:51 AM";
        assertEquals(expectedOutput2, generatedOutput2);
        
        LocalDateTime currentTime3 = LocalDateTime.of(2016, Month.MARCH, 20, 23, 50, 00);
        LocalDateTime dueTime3 = LocalDateTime.of(2016, Month.MARCH, 21, 00, 10, 00);
        TimeUtil timeUtil3 = new ModifiedTimeUtil(currentTime3);
        String generatedOutput3 = timeUtil3.getTaskDeadlineText(dueTime3);
        String expectedOutput3 = "in 20 minutes";
        assertEquals(expectedOutput3, generatedOutput3);
    }
    
    @Test
    public void getTaskDeadlineText_yesterdayAfterDeadline() {
        testTaskDeadlineTextHelper("since yesterday, 12:00 PM", 
                LocalDateTime.of(2016, Month.MARCH, 21, 12, 00), LocalDateTime.of(2016, Month.MARCH, 20, 12, 00));
        testTaskDeadlineTextHelper("since yesterday, 12:51 AM",
                LocalDateTime.of(2016, Month.MARCH, 21, 23, 50), LocalDateTime.of(2016, Month.MARCH, 20, 00, 51));
        testTaskDeadlineTextHelper("20 minutes ago", 
                LocalDateTime.of(2016, Month.MARCH, 21, 00, 10), LocalDateTime.of(2016, Month.MARCH, 20, 23, 50));
    }
    }
    
    
}
