package seedu.address.model.item;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.CommandParserHelper;

public class DateTimeTest {

    @Test
    public void convertStringToDate() {
        Date date = DateTime.convertStringToDate("11th Sep 2016 7:15am");
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(date);
        Calendar calendarExpected = Calendar.getInstance();
        calendarExpected.set(2016, Calendar.SEPTEMBER, 11, 7, 15);
        assertEquals(calendarActual.get(Calendar.YEAR), calendarExpected.get(Calendar.YEAR));
        assertEquals(calendarActual.get(Calendar.MONTH), calendarExpected.get(Calendar.MONTH));
        assertEquals(calendarActual.get(Calendar.DAY_OF_YEAR), calendarExpected.get(Calendar.DAY_OF_YEAR));
        assertEquals(calendarActual.get(Calendar.HOUR_OF_DAY), calendarExpected.get(Calendar.HOUR_OF_DAY));
        assertEquals(calendarActual.get(Calendar.MINUTE), calendarExpected.get(Calendar.MINUTE));
    }
    
    @Test
    public void hasDateValue_True() {
        assertTrue(DateTime.hasDateValue("11th Sep 2016"));
    }
    
    @Test
    public void hasDateValue_False() {
        assertFalse(DateTime.hasDateValue("11:30pm"));
    }
    
    @Test
    public void hasTimeValue_True() {
        assertTrue(DateTime.hasTimeValue("11:30pm"));
    }
    
    @Test
    public void hasTimeValue_False() {
        assertFalse(DateTime.hasTimeValue("11th Sep 2016"));
    }

    @Test
    public void setEndDateToStartDate_() {
        
    }
    
    @Test
    public void isValidDate_invalidDateOne() {
        assertFalse(DateTime.isValidDate("the beach"));
    }
    
    @Test
    public void isValidDate_invalidDateTwo() {
        assertFalse(DateTime.isValidDate("10am meh"));
    }
    
    @Test
    public void isValidDate_invalidDateThree() {
        assertFalse(DateTime.isValidDate("meh 10am"));
    }
    
    @Test
    public void isValidDate_validDate() {
        assertTrue(DateTime.isValidDate("11th Sep 10am"));
    }
    
    @Test
    public void assignStartDateToSpecifiedWeekday() {
    }
    
    @Test
    public void setTimeToStartOfDay() {
        Date dateActual = DateTime.setTimeToStartOfDay(Calendar.getInstance().getTime());
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(dateActual);

        assertEquals(calendarActual.get(Calendar.HOUR_OF_DAY), 0);
        assertEquals(calendarActual.get(Calendar.MINUTE), 0);
        assertEquals(calendarActual.get(Calendar.SECOND), 0);
    }
    
    @Test
    public void setTimeToEndOfDay() {
        Date dateActual = DateTime.setTimeToEndOfDay(Calendar.getInstance().getTime());
        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(dateActual);

        assertEquals(calendarActual.get(Calendar.HOUR_OF_DAY), 23);
        assertEquals(calendarActual.get(Calendar.MINUTE), 59);
        assertEquals(calendarActual.get(Calendar.SECOND), 59);
    }
    
    @Test
    public void updateDateByRecurrenceRate() {
    }
}
