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
            this(Clock.fixed(pseudoCurrentTime.toInstant(
                    ZoneId.systemDefault().getRules().getOffset(pseudoCurrentTime)), ZoneId.systemDefault()));
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
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 59), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 30), dueTime);
    }
    
    @Test
    public void getTaskDeadlineText_dueLessThanAMinute() {
        String expectedOutput = "in less than a minute";
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 01), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 59), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 30), dueTime);
    }
    
    @Test
    public void getTaskDeadlineText_aMinuteBeforeDeadline() {
        String expectedOutput = "in 1 minute";
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 59, 00), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 58, 30), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 11, 58, 01), dueTime);
    }
    
    @Test
    public void getTaskDeadlineText_aMinuteAfterDeadline() {
        String expectedOutput = "1 minute ago";
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 00), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 30), dueTime);
        testTaskDeadlineTextHelper(expectedOutput, LocalDateTime.of(2016, Month.MARCH, 20, 12, 01, 59), dueTime);
    }
    
    @Test
    public void getTaskDeadlineText_minutesBeforeDeadline() {
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        for (int minutesLeft = 2; minutesLeft <= 59; minutesLeft++) {
            String expectedOutput = "in " + minutesLeft + " minutes";
            
            testTaskDeadlineTextHelper(expectedOutput, dueTime.minusMinutes(minutesLeft), dueTime);
            testTaskDeadlineTextHelper(expectedOutput, dueTime.minusMinutes(minutesLeft).minusSeconds(30), dueTime);
            testTaskDeadlineTextHelper(expectedOutput, dueTime.minusMinutes(minutesLeft).minusSeconds(59), dueTime);
        }
    }
    
    @Test
    public void getTaskDeadlineText_minutesAfterDeadline() {
        LocalDateTime dueTime = LocalDateTime.of(2016, Month.MARCH, 20, 12, 00, 00);
        
        for (int minutesLater = 2; minutesLater <= 59; minutesLater++) {
            String expectedOutput = minutesLater + " minutes ago";
            
            testTaskDeadlineTextHelper(expectedOutput, dueTime.plusMinutes(minutesLater), dueTime);
            testTaskDeadlineTextHelper(expectedOutput, dueTime.plusMinutes(minutesLater).plusSeconds(30), dueTime);
            testTaskDeadlineTextHelper(expectedOutput, dueTime.plusMinutes(minutesLater).plusSeconds(59), dueTime);
        }
    }
    
    @Test
    public void getTaskDeadlineText_todayBeforeDeadline() {
        testTaskDeadlineTextHelper("by today, 5:59 PM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 10, 45), LocalDateTime.of(2016, Month.MARCH, 20, 17, 59));
        testTaskDeadlineTextHelper("by tonight, 6:00 PM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 11, 58), LocalDateTime.of(2016, Month.MARCH, 20, 18, 00));
        testTaskDeadlineTextHelper("in 30 minutes", 
                LocalDateTime.of(2016, Month.MARCH, 20, 00, 00), LocalDateTime.of(2016, Month.MARCH, 20, 00, 30));
    }
    
    @Test
    public void getTaskDeadlineText_todayAfterDeadline() {
        testTaskDeadlineTextHelper("since 12:00 PM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 18, 45), LocalDateTime.of(2016, Month.MARCH, 20, 12, 00));
        testTaskDeadlineTextHelper("since 4:50 PM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 23, 58), LocalDateTime.of(2016, Month.MARCH, 20, 16, 50));
        testTaskDeadlineTextHelper("30 minutes ago", 
                LocalDateTime.of(2016, Month.MARCH, 20, 00, 30), LocalDateTime.of(2016, Month.MARCH, 20, 00, 00));
    }
    
    @Test
    public void getTaskDeadlineText_tomorrowBeforeDeadline() {
        testTaskDeadlineTextHelper("by tomorrow, 12:00 PM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 12, 00), LocalDateTime.of(2016, Month.MARCH, 21, 12, 00));
        testTaskDeadlineTextHelper("by tomorrow, 12:51 AM", 
                LocalDateTime.of(2016, Month.MARCH, 20, 23, 50), LocalDateTime.of(2016, Month.MARCH, 21, 00, 51));
        testTaskDeadlineTextHelper("in 20 minutes", 
                LocalDateTime.of(2016, Month.MARCH, 20, 23, 50), LocalDateTime.of(2016, Month.MARCH, 21, 00, 10));
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
    
    @Test
    public void getTaskDeadlineText_thisYearBeforeDeadline() {
        testTaskDeadlineTextHelper("by 12 August, 12:55 PM", 
                LocalDateTime.of(2016, Month.JANUARY, 21, 12, 00), LocalDateTime.of(2016, Month.AUGUST, 12, 12, 55));
        testTaskDeadlineTextHelper("by 15 September, 12:00 AM",
                LocalDateTime.of(2016, Month.SEPTEMBER, 13, 23, 59), LocalDateTime.of(2016, Month.SEPTEMBER, 15, 00, 00));
    }
    
    @Test
    public void getTaskDeadlineText_thisYearAfterDeadline() {
        testTaskDeadlineTextHelper("since 21 January, 8:47 PM", 
                LocalDateTime.of(2016, Month.AUGUST, 12, 12, 55), LocalDateTime.of(2016, Month.JANUARY, 21, 20, 47));
        testTaskDeadlineTextHelper("since 13 September, 11:59 PM",
                LocalDateTime.of(2016, Month.SEPTEMBER, 15, 00, 00), LocalDateTime.of(2016, Month.SEPTEMBER, 13, 23, 59));
    }
    
    @Test
    public void getTaskDeadlineText_differentYearBeforeDeadline() {
        testTaskDeadlineTextHelper("in 1 minute", 
                LocalDateTime.of(2016, Month.DECEMBER, 31, 23, 59), LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        testTaskDeadlineTextHelper("by tomorrow, 1:15 AM",
                LocalDateTime.of(2016, Month.DECEMBER, 31, 23, 00), LocalDateTime.of(2017, Month.JANUARY, 1, 1, 15));
        testTaskDeadlineTextHelper("by 31 January 2017, 1:05 AM",
                LocalDateTime.of(2016, Month.JUNE, 30, 22, 00), LocalDateTime.of(2017, Month.JANUARY, 31, 1, 5));
        testTaskDeadlineTextHelper("by 31 August 2020, 12:35 PM",
                LocalDateTime.of(2016, Month.FEBRUARY, 13, 13, 00), LocalDateTime.of(2020, Month.AUGUST, 31, 12, 35));
    }
    
    @Test
    public void getTaskDeadlineText_differentYearAfterDeadline() {
        testTaskDeadlineTextHelper("1 minute ago", 
                LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0), LocalDateTime.of(2016, Month.DECEMBER, 31, 23, 59));
        testTaskDeadlineTextHelper("since yesterday, 12:00 AM",
                LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0), LocalDateTime.of(2016, Month.DECEMBER, 31, 00, 00));
        testTaskDeadlineTextHelper("since 30 June 2016, 10:00 PM",
                LocalDateTime.of(2017, Month.JANUARY, 31, 1, 5), LocalDateTime.of(2016, Month.JUNE, 30, 22, 00));
        testTaskDeadlineTextHelper("since 13 February 2016, 1:00 PM",
                LocalDateTime.of(2020, Month.AUGUST, 31, 12, 35), LocalDateTime.of(2016, Month.FEBRUARY, 13, 13, 00));
    }
    
    @Test (expected = AssertionError.class)
    public void getEventTimeText_nullStartTime() {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.getEventTimeText(null, LocalDateTime.now());
    }
    
    @Test (expected = AssertionError.class)
    public void getEventTimeText_nullEndTime() {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.getEventTimeText(LocalDateTime.now(), null);
    }
    
    @Test (expected = AssertionError.class)
    public void isOverdue_nullEndTime() {
        TimeUtil timeUtil = new TimeUtil();
        timeUtil.isOverdue(null);
    }
    
    @Test
    public void isOverdue_endTimeAfterNow() {
        TimeUtil timeUtil = new ModifiedTimeUtil(LocalDateTime.of(2016, Month.DECEMBER, 12, 12, 34));
        LocalDateTime laterEndTime = LocalDateTime.of(2016, Month.DECEMBER, 12, 12, 35);
        assertFalse(timeUtil.isOverdue(laterEndTime));
    }
    
    @Test
    public void isOverdue_endTimeBeforeNow() {
        TimeUtil timeUtil = new ModifiedTimeUtil(LocalDateTime.of(2016, Month.DECEMBER, 12, 12, 36));
        LocalDateTime laterEndTime = LocalDateTime.of(2016, Month.DECEMBER, 12, 12, 35);
        assertTrue(timeUtil.isOverdue(laterEndTime));
    }
    
    @Test
    public void toAmericanDateFormat_matches() {
        assertEquals("12/6", TimeUtil.toAmericanDateFormat("6/12"));
        assertEquals("12/6/2016", TimeUtil.toAmericanDateFormat("6/12/2016"));
        assertEquals("12-6-2016", TimeUtil.toAmericanDateFormat("6-12-2016"));
        
        assertEquals("from 7/6/2016 to 8/6/2016 tomorrow", TimeUtil.toAmericanDateFormat("from 6/7/2016 to 6/8/2016 tomorrow"));
        assertEquals("5/6/15", TimeUtil.toAmericanDateFormat("6/5/15"));
        assertEquals("5/6 5pm", TimeUtil.toAmericanDateFormat("6/5 5pm"));
    }
    
    @Test
    public void toAmericanDateFormat_nomatches() {
        assertEquals("Should not do anything", TimeUtil.toAmericanDateFormat("Should not do anything"));
        assertEquals("Hello/world", TimeUtil.toAmericanDateFormat("Hello/world"));
        assertEquals("10-1002 train", TimeUtil.toAmericanDateFormat("10-1002 train"));
        assertEquals("6-8pm", TimeUtil.toAmericanDateFormat("6-8pm"));
        assertEquals("Breakfast at 6-8am in the morning", TimeUtil.toAmericanDateFormat("Breakfast at 6-8am in the morning"));
    }
}
