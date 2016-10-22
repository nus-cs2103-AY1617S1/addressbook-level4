package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

//@@author A0135817B
public class DateRangeTest {

    @Test
    public void testDateRangeLocalDateTime() {
        LocalDateTime start = LocalDateTime.now();
        DateRange range = new DateRange(start);
        assertNull(range.getStartTime());
        assertFalse(range.isRange());
    }

    @Test
    public void testDateRangeLocalDateTimeLocalDateTime() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);
        DateRange range = new DateRange(start, end);
        
        assertTrue(range.isRange());
    }

}
