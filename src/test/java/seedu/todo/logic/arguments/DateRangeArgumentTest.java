package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

public class DateRangeArgumentTest {
    private final Argument<DateRange> arg = new DateRangeArgument("Test");
    private final LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));

    @Test
    public void testInternationalDate() throws Exception {
        arg.setValue("6/12/16");
        assertEquals(LocalDateTime.of(2016, 12, 6, 0, 0), arg.getValue().getEndTime());
    }
    
    @Test
    public void testInternationalDateTime() throws Exception {
        arg.setValue("6/12/16 12:45pm");
        assertEquals(LocalDateTime.of(2016, 12, 6, 12, 45), arg.getValue().getEndTime());
    }
    
    @Test
    public void testNaturalLanguageDate() throws Exception {
        arg.setValue("12 Oct 14 2pm");
        assertEquals(LocalDateTime.of(2014, 10, 12, 2, 0), arg.getValue().getEndTime());
    }
    
    @Test
    public void testRelativeDate() throws Exception {
        arg.setValue("tomorrow");
        assertEquals(LocalDateTime.now().plusDays(1), arg.getValue().getEndTime());
    }

    @Test
    public void testRelativeDateTime() throws Exception {
        arg.setValue("tomorrow 6pm");
        assertEquals(tomorrow.withHour(18), arg.getValue().getEndTime());
    }
    
    @Test
    public void testNaturalDateRange() throws Exception {
        arg.setValue("tomorrow 6 to 8pm");
        assertEquals(tomorrow.withHour(20), arg.getValue().getEndTime());
        assertEquals(tomorrow.withHour(18), arg.getValue().getStartTime());
    }
    
    @Test
    public void testFormalDateRange() throws Exception {
        arg.setValue("18-12-16 1800-2000");
        LocalDateTime date = LocalDateTime.of(2016, 12, 18, 0, 0);
        assertEquals(date.withHour(20), arg.getValue().getEndTime());
        assertEquals(date.withHour(18), arg.getValue().getStartTime());
    }
    
    @Test(expected=IllegalValueException.class)
    public void testNoDate() throws Exception {
        arg.setValue("no date here");
    }
    
    @Test(expected=IllegalValueException.class)
    public void testTooManyDates() throws Exception {
        arg.setValue("yesterday, today, tomorrow");
    }

}
