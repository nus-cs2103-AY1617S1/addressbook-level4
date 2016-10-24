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
    public void setEndDateToStartDateOne() {
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarStartDate.set(Calendar.MINUTE, 30);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarEndDate.set(Calendar.MINUTE, 00);
        Date endDate = DateTime.setEndDateToStartDate(calendarStartDate.getTime(), calendarEndDate.getTime());
        
        Calendar expectedCalendarEndDate = Calendar.getInstance();
        expectedCalendarEndDate.setTime(endDate);
        assertEquals(expectedCalendarEndDate.get(Calendar.DATE), calendarStartDate.get(Calendar.DATE) + 1);
    }
    
    @Test
    public void setEndDateToStartDateTwo() {
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarStartDate.set(Calendar.MINUTE, 30);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarEndDate.set(Calendar.MINUTE, 31);
        Date endDate = DateTime.setEndDateToStartDate(calendarStartDate.getTime(), calendarEndDate.getTime());
        
        Calendar expectedCalendarEndDate = Calendar.getInstance();
        expectedCalendarEndDate.setTime(endDate);
        assertEquals(expectedCalendarEndDate.get(Calendar.DATE), calendarStartDate.get(Calendar.DATE));
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
        Date date = DateTime.assignStartDateToSpecifiedWeekday("monday");
        assertTrue(date.toString().contains("Mon"));
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
    public void updateDateByRecurrenceRateOne() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 00);
        
        try {
            RecurrenceRate recurrenceRate = new RecurrenceRate("1", "month");
            DateTime.updateDateByRecurrenceRate(calendar, recurrenceRate);
            assertEquals(calendar.get(Calendar.YEAR), 2016);
            assertEquals(calendar.get(Calendar.MONTH), Calendar.NOVEMBER);
            assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 24);
            assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 10);
            assertEquals(calendar.get(Calendar.MINUTE), 0);
        } catch (IllegalValueException e) {
            assert false;
        }
    }
    
    @Test
    public void updateDateByRecurrenceRateTwo() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 24);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 00);
        
        try {
            RecurrenceRate recurrenceRate = new RecurrenceRate("2", "wednesday");
            DateTime.updateDateByRecurrenceRate(calendar, recurrenceRate);
            assertEquals(calendar.get(Calendar.YEAR), 2016);
            assertEquals(calendar.get(Calendar.MONTH), Calendar.NOVEMBER);
            assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);
            assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 10);
            assertEquals(calendar.get(Calendar.MINUTE), 0);
        } catch (IllegalValueException e) {
            assert false;
        }
    }
}
