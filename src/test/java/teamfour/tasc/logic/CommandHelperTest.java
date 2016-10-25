package teamfour.tasc.logic;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.commands.CommandHelper;
import teamfour.tasc.model.task.Recurrence;

import org.junit.Test;

public class CommandHelperTest {

    /*
     * Equivalence partitions for date strings:
     *  - null
     *  - empty string
     *  - invalid date string
     *  - valid date string
     */

    @Test
    public void convertStringToDateIfPossible_stringIsNull_returnsNull() {
        String string = null;
        Date date = CommandHelper.convertStringToDateIfPossible(string);
        assertTrue(date == null);
    }

    @Test
    public void convertStringToDateIfPossible_stringIsEmpty_returnsNull() {
        String string = "";
        Date date = CommandHelper.convertStringToDateIfPossible(string);
        assertTrue(date == null);
    }

    @Test
    public void convertStringToDateIfPossible_stringIsInvalidDate_returnsNull() {
        String string = "invalidstringdate";
        Date date = CommandHelper.convertStringToDateIfPossible(string);
        assertTrue(date == null);
    }

    @Test
    public void convertStringToDateIfPossible_stringIsValidDate_returnsDate() {
        String string = "13 sep 2013";
        Date date = CommandHelper.convertStringToDateIfPossible(string);
        assertTrue(date.getDate() == 13);
        assertTrue(date.getMonth() == 8);
        assertTrue(date.getYear() == 2013 - 1900);
    }
    //@@author A0127014W
    @Test
    public void convertStringToMultipleDates_shortName_correct_date_month_year() {
        String date = "13 sep 2013";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
        assertTrue(dates.get(0).getYear() == 2013 - 1900);

        String date2 = "13th sept 2000";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 13);
        assertTrue(dates2.get(0).getMonth() == 8);
        assertTrue(dates2.get(0).getYear() == 2000 - 1900);

        String date3 = "sept 13 1900";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 13);
        assertTrue(dates3.get(0).getMonth() == 8);
        assertTrue(dates3.get(0).getYear() == 1900 - 1900);
    }

    @Test
    public void convertStringToMultipleDates_fullName_correct_date_month() {
        String date = "thirteenth september";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
    }

    @Test
    public void convertStringToMultipleDates_correct_dayOfWeek_timeOfDayInNumbers() {
        String date = "friday 7pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "next wed 3 afternoon";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDay() == 3);
        assertTrue(dates2.get(0).getHours() == 15);

        String date3 = "this monday 1600 hours";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDay() == 1);
        assertTrue(dates3.get(0).getHours() == 16);
    }

    @Test
    public void convertStringToMultipleDates_timeOfDayInWords() {
        String date = "next friday seven evening";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "3rd april five in the morning";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 3);
        assertTrue(dates2.get(0).getHours() == 5);

        String date3 = "6 april three pm";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 6);
        assertTrue(dates3.get(0).getHours() == 15);
    }

    @Test
    public void convertStringToMultipleDates_yearWithTime() {
        String date = "6 april 2004 3 pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 6);
        assertTrue(dates.get(0).getHours() == 15);
        assertTrue(dates.get(0).getYear() == 2004 - 1900);
    }

    @Test
    public void convertStringToMultipleDates_getsMultipleDates() {
        String date = "20 aug and 17 april";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.size() == 2);
    }

    @Test
    public void convertStringToDate_shortName_correct_date_month() {
        String dateString = "13 sep";
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            assertTrue(date.getDate() == 13);
            assertTrue(date.getMonth() == 8);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToDate_multipleDates_exceptionThrown(){
        String dateString = "13 sep and 14 oct";
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            fail("Exception expected");
        } catch (Exception e) {

        }
    }

    @Test
    public void getRecurrence_validInput(){
        String repeatParameter = "weekly 3";
        Recurrence recurrence;
        try {
            recurrence = CommandHelper.getRecurrence(repeatParameter);
            assertEquals("WEEKLY [3 time(s)]", recurrence.toString());
        } catch (IllegalValueException e) {
            fail();
        }

        String repeatParameter2 = "none 0";
        Recurrence recurrence2;
        try {
            recurrence2 = CommandHelper.getRecurrence(repeatParameter2);
            assertEquals("No recurrence.", recurrence2.toString());
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test
    public void getRecurrence_invalidInput(){
        String repeatParameter = "weekly 0";
        Recurrence recurrence;
        try {
            recurrence = CommandHelper.getRecurrence(repeatParameter);
            fail("Exception expected");
        } catch (IllegalValueException e) {
        }
    }
    //@@author

    @Test
    public void convertDateToPrettyTimeParserFriendlyString_validInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 0, 1, 0, 0, 0);
        Date input = calendar.getTime();
        String expectedOutput = "Jan 01 2016 00:00:00";

        assertEquals(expectedOutput,
                CommandHelper.convertDateToPrettyTimeParserFriendlyString(input));
    }
}
