package seedu.address.model.item;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139655U
public class DateTimeTest {

    @Test
    public void convertStringToDate_validDate() {
        Date date;
        try {
            date = DateTime.convertStringToDate("11th Sep 2016 7:15am");
            Calendar calendarActual = Calendar.getInstance();
            calendarActual.setTime(date);
            Calendar calendarExpected = Calendar.getInstance();
            calendarExpected.set(2016, Calendar.SEPTEMBER, 11, 7, 15);
            assertEquals(calendarActual.get(Calendar.YEAR), calendarExpected.get(Calendar.YEAR));
            assertEquals(calendarActual.get(Calendar.MONTH), calendarExpected.get(Calendar.MONTH));
            assertEquals(calendarActual.get(Calendar.DAY_OF_MONTH), calendarExpected.get(Calendar.DAY_OF_MONTH));
            assertEquals(calendarActual.get(Calendar.HOUR_OF_DAY), calendarExpected.get(Calendar.HOUR_OF_DAY));
            assertEquals(calendarActual.get(Calendar.MINUTE), calendarExpected.get(Calendar.MINUTE));
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void hasDateValue() {
        // EP: Date has date value
        try {
            assertTrue(DateTime.hasDateValue("11th Sep 2016"));
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: Date does not have date value
        try {
            assertFalse(DateTime.hasDateValue("11:30pm"));
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void hasTimeValue() {
        // EP: Date has time value
        try {
            assertTrue(DateTime.hasTimeValue("11:30pm"));
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: Date does not have time value
        try {
            assertFalse(DateTime.hasTimeValue("11th Sep 2016"));
        } catch (IllegalValueException ive) {
            assert false;
        }
    }
    
    @Test
    public void setEndDateToStartDate_endDateEarlierThanStartDate_endDateOneDayAfterStartDay() {
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarStartDate.set(Calendar.MINUTE, 30);

        Calendar calendarEndDate = Calendar.getInstance();
        calendarEndDate.set(Calendar.HOUR_OF_DAY, 23);
        calendarEndDate.set(Calendar.MINUTE, 00);
        Date endDate = DateTime.setEndDateToStartDate(calendarStartDate.getTime(), calendarEndDate.getTime());
        
        Calendar expectedCalendarEndDate = Calendar.getInstance();
        expectedCalendarEndDate.setTime(endDate);
        
        assertEquals(expectedCalendarEndDate.get(Calendar.DATE), (calendarStartDate.get(Calendar.DATE) + 1) %
                calendarStartDate.getActualMaximum(Calendar.DATE));
    }
    
    @Test
    public void setEndDateToStartDate_endDateLaterThanStartDate_endDateSameDayAsStartDay() {
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
    public void isValidDate() {
        // EP: no date time values
        assertFalse(DateTime.isValidDate("the beach"));
        
        // EP: wrong values before date time values
        assertFalse(DateTime.isValidDate("meh 11th Sep 10am"));
        
        // EP: valid date time values
        assertTrue(DateTime.isValidDate("11th Sep 10am"));
        
        // EP: valid relative date
        assertTrue(DateTime.isValidDate("one week later"));
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
    public void assignStartDateToSpecifiedWeekday() {
        Date date;
        try {
            date = DateTime.assignStartDateToSpecifiedWeekday("monday");
            assertTrue(date.toString().contains("Mon"));
        } catch (IllegalValueException ive) {
            assert false;
        }
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
        // EP: For time periods e.g repeat every month
        Calendar calendar = generateCalendarWithTimeValues(2016, Calendar.OCTOBER, 24, 10, 00);
        
        try {
            RecurrenceRate recurrenceRate = new RecurrenceRate("month");
            Date date = calendar.getTime();
            date = DateTime.updateDateByRecurrenceRate(date, recurrenceRate);
            calendar.setTime(date);
            assertEquals(calendar.get(Calendar.YEAR), 2016);
            assertEquals(calendar.get(Calendar.MONTH), Calendar.NOVEMBER);
            assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 24);
            assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 10);
            assertEquals(calendar.get(Calendar.MINUTE), 0);
        } catch (IllegalValueException ive) {
            assert false;
        }
        
        // EP: for time values e.g repeat every 2 wednesday
        calendar = generateCalendarWithTimeValues(2016, Calendar.OCTOBER, 24, 10, 00);
        
        try {
            RecurrenceRate recurrenceRate = new RecurrenceRate("2", "wednesday");
            Date date = calendar.getTime();
            date = DateTime.updateDateByRecurrenceRate(date, recurrenceRate);
            calendar.setTime(date);
            assertEquals(calendar.get(Calendar.YEAR), 2016);
            assertEquals(calendar.get(Calendar.MONTH), Calendar.NOVEMBER);
            assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 2);
            assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 10);
            assertEquals(calendar.get(Calendar.MINUTE), 0);
        } catch (IllegalValueException e) {
            assert false;
        }
    }

    private Calendar generateCalendarWithTimeValues(Integer... timeValues) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, timeValues[0]);
        calendar.set(Calendar.MONTH, timeValues[1]);
        calendar.set(Calendar.DAY_OF_MONTH, timeValues[2]);
        calendar.set(Calendar.HOUR_OF_DAY, timeValues[3]);
        calendar.set(Calendar.MINUTE, timeValues[4]);
        return calendar;
    }
}
