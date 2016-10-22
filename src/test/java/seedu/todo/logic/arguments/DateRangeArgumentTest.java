package seedu.todo.logic.arguments;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;

//@@author A0135817B
public class DateRangeArgumentTest {
    private final Argument<DateRange> arg = new DateRangeArgument("Test");
    private final LocalDateTime tomorrow = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(0, 0));
    
    @Test
    public void testDefaultValue() throws Exception {
        assertNull(arg.getValue().getStartTime());
        assertNull(arg.getValue().getEndTime());
        assertFalse(arg.hasBoundValue());
    }
    
    @Test
    public void testEmptyInput() throws Exception {
        arg.setValue("");
        assertNull(arg.getValue().getStartTime());
        assertNull(arg.getValue().getEndTime());
        assertTrue(arg.hasBoundValue());
    }

    @Test
    public void testInternationalDate() throws Exception {
        arg.setValue("6/12/16");
        assertEquals(LocalDate.of(2016, 12, 6), arg.getValue().getEndTime().toLocalDate());
        assertFalse(arg.getValue().isRange());
    }
    
    @Test
    public void testIsoDate() throws Exception {
        arg.setValue("06-12-2016");
        assertEquals(LocalDate.of(2016, 12, 6), arg.getValue().getEndTime().toLocalDate());
        assertFalse(arg.getValue().isRange());
    }
    
    @Test
    public void testInternationalDateTime() throws Exception {
        arg.setValue("6/12/16 12:45pm");
        assertEquals(LocalDateTime.of(2016, 12, 6, 12, 45), arg.getValue().getEndTime());
        assertFalse(arg.getValue().isRange());
    }
    
    @Test
    public void testNaturalLanguageDateTime() throws Exception {
        arg.setValue("12 Oct 2014 6pm");
        assertEquals(LocalDateTime.of(2014, 10, 12, 18, 0), arg.getValue().getEndTime());
        assertFalse(arg.getValue().isRange());
    }
    
    @Test
    public void testRelativeDate() throws Exception {
        arg.setValue("tomorrow");
        assertEquals(tomorrow.toLocalDate(), arg.getValue().getEndTime().toLocalDate());
        assertFalse(arg.getValue().isRange());
    }

    @Test
    public void testRelativeDateTime() throws Exception {
        arg.setValue("tomorrow 6pm");
        assertEquals(tomorrow.withHour(18), arg.getValue().getEndTime());
        assertFalse(arg.getValue().isRange());
    }
    
    @Test
    public void testRelativeDateRange() throws Exception {
        arg.setValue("tomorrow 6 to 8pm");
        assertEquals(tomorrow.withHour(20), arg.getValue().getEndTime());
        assertEquals(tomorrow.withHour(18), arg.getValue().getStartTime());
    }
    
    @Test
    public void testFormalDateTimeRange() throws Exception {
        arg.setValue("18-12-16 1800hrs to 2000hrs");
        LocalDateTime date = LocalDateTime.of(2016, 12, 18, 0, 0);
        assertEquals(date.withHour(20), arg.getValue().getEndTime());
        assertEquals(date.withHour(18), arg.getValue().getStartTime());

        arg.setValue("18-12-16 1800hrs to 19-12-16 2000hrs");
        assertEquals(LocalDateTime.of(2016, 12, 18, 18, 0), arg.getValue().getStartTime());
        assertEquals(LocalDateTime.of(2016, 12, 19, 20, 0), arg.getValue().getEndTime());
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
