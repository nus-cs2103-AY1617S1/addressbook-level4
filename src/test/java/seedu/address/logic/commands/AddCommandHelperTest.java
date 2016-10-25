package seedu.address.logic.commands;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.RecurrenceRate;
import seedu.address.testutil.TestOptionalHashMap;

//@@author A0139655U
public class AddCommandHelperTest {

    private static final int NUMBER_OF_DAYS_IN_A_WEEK = 7;
    TestOptionalHashMap testOptionalHashMap;
    AddCommand command;
    
    @Test
    public void addCommand_invalidRate() {
        testOptionalHashMap = new TestOptionalHashMap("eat bingsu from the beach", "11pm", "1am", "0", "days", "medium");
        try {
            AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    @Test
    public void addCommand_invalidTimePeriod() {
        testOptionalHashMap = new TestOptionalHashMap("eat bingsu from the beach", "11pm", "1am", "5", "bobs", "medium");
        try {
            AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    @Test
    public void addCommand_invalidRecurrenceAndDate() {
        testOptionalHashMap = new TestOptionalHashMap("eat bingsu from the beach", null, null, "5", "days", "medium");
        try {
            AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), AddCommandHelper.MESSAGE_RECUR_DATE_TIME_CONSTRAINTS);
        }
    }
    
    @Test
    public void addCommand_invalidRecurrenceRate() {
        testOptionalHashMap = new TestOptionalHashMap("eat bingsu from the beach", "12th Sep", "13th Sep", "2", null, "low");
        try {
            AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            assert false;
        } catch (IllegalValueException ive) {
            assertEquals(ive.getMessage(), RecurrenceRate.MESSAGE_VALUE_CONSTRAINTS);
        }
    }
    
    @Test
    public void addCommand_recurWeekdaysDatesNotGiven() {
        testOptionalHashMap = new TestOptionalHashMap("lower word count from 1000 to 500", null, null, "1", "Wednesday", "high");
        try {
            HashMap<String, Object> mapOfObjects = AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            Name taskName = (Name) mapOfObjects.get("taskName");
            Date startDate = (Date) mapOfObjects.get("startDate");
            Date endDate = (Date) mapOfObjects.get("endDate");
            RecurrenceRate recurrenceRate = (RecurrenceRate) mapOfObjects.get("recurrenceRate");
            Priority priority = (Priority) mapOfObjects.get("priority");
            Calendar calendar = Calendar.getInstance();
            
            addNumberOfDaysTillNextWed(calendar);
            
            assertEquals(taskName, new Name("lower word count from 1000 to 500"));
            generateAndAssertEqualsDates(startDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
                    calendar.get(Calendar.DATE), 0, 0);
            assertEquals(endDate, null);
            assertEquals(recurrenceRate, new RecurrenceRate("1", "Wednesday"));
            assertEquals(priority, Priority.HIGH);
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    private void addNumberOfDaysTillNextWed(Calendar calendar) {
        int date = calendar.get(Calendar.DAY_OF_WEEK);
        int numberOfDaysToAdd = 0;
        
        while (date % NUMBER_OF_DAYS_IN_A_WEEK != Calendar.WEDNESDAY) {
            date++;
            numberOfDaysToAdd++;
        }
        calendar.add(Calendar.DATE, numberOfDaysToAdd);
    }
    
    @Test
    public void addCommand_validInput_timePeriodPresentRateNotPresent() {
        testOptionalHashMap = new TestOptionalHashMap("eat food", "24th Oct", "25th Oct", null, "week", "l");
        try {
            HashMap<String, Object> mapOfObjects = AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            Name taskName = (Name) mapOfObjects.get("taskName");
            Date startDate = (Date) mapOfObjects.get("startDate");
            Date endDate = (Date) mapOfObjects.get("endDate");
            RecurrenceRate recurrenceRate = (RecurrenceRate) mapOfObjects.get("recurrenceRate");
            Priority priority = (Priority) mapOfObjects.get("priority");
            Calendar calendar = Calendar.getInstance();
            
            assertEquals(taskName, new Name("eat food"));
            generateAndAssertEqualsDates(startDate, calendar.get(Calendar.YEAR), Calendar.OCTOBER, 
                    24, 0, 0);
            generateAndAssertEqualsDates(endDate, calendar.get(Calendar.YEAR), Calendar.OCTOBER, 
                    25, 23, 59);
            assertEquals(recurrenceRate, new RecurrenceRate("1", "week"));
            assertEquals(priority, Priority.LOW);
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    @Test
    public void addCommand_validInput_endTimeEarlierThanStartTime() {
        testOptionalHashMap = new TestOptionalHashMap("lower word count from 1000 to 500", "11pm", "1am", "1", "Monday", "k");
        try {
            HashMap<String, Object> mapOfObjects = AddCommandHelper.convertStringToObjects(testOptionalHashMap.map);
            Name taskName = (Name) mapOfObjects.get("taskName");
            Date startDate = (Date) mapOfObjects.get("startDate");
            Date endDate = (Date) mapOfObjects.get("endDate");
            RecurrenceRate recurrenceRate = (RecurrenceRate) mapOfObjects.get("recurrenceRate");
            Priority priority = (Priority) mapOfObjects.get("priority");
            Calendar calendar = Calendar.getInstance();
            
            assertEquals(taskName, new Name("lower word count from 1000 to 500"));
            generateAndAssertEqualsDates(startDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
                    calendar.get(Calendar.DATE), 23, 0);
            calendar.add(Calendar.DATE, 1);
            generateAndAssertEqualsDates(endDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 
                    calendar.get(Calendar.DATE), 1, 0);
            assertEquals(recurrenceRate, new RecurrenceRate("1", "Monday"));
            assertEquals(priority, Priority.MEDIUM);
        } catch (IllegalValueException ive) {
            assert false;
        }
    }

    private void generateAndAssertEqualsDates(Date date, Integer... args) {
        Calendar calendarActual = generateActualCalendar(date);
        Calendar calendarExpected = generateExpectedCalendar(args);
        assertEqualsDate(calendarActual, calendarExpected);
    }

    private Calendar generateExpectedCalendar(Integer... args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, args[0]);
        calendar.set(Calendar.MONTH, args[1]);
        calendar.set(Calendar.DATE, args[2]);
        calendar.set(Calendar.HOUR_OF_DAY, args[3]);
        calendar.set(Calendar.MINUTE, args[4]);
        return calendar;
    }

    private Calendar generateActualCalendar(Date date) {
        assert date != null;
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private void assertEqualsDate(Calendar calendarActual, Calendar calendarExpected) {
        assertEquals(calendarActual.get(Calendar.YEAR), calendarExpected.get(Calendar.YEAR));
        assertEquals(calendarActual.get(Calendar.MONTH), calendarExpected.get(Calendar.MONTH));
        assertEquals(calendarActual.get(Calendar.DATE), calendarExpected.get(Calendar.DATE));
        assertEquals(calendarActual.get(Calendar.HOUR_OF_DAY), calendarExpected.get(Calendar.HOUR_OF_DAY));
        assertEquals(calendarActual.get(Calendar.MINUTE), calendarExpected.get(Calendar.MINUTE));
    }
}
