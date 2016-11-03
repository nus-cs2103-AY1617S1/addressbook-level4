package seedu.flexitrack.model.task;

import static org.junit.Assert.assertTrue;
import static seedu.flexitrack.model.task.DateTimeInfo.MESSAGE_FROM_IS_AFTER_TO;

import org.junit.Before;
import org.junit.Test;

import seedu.flexitrack.commons.exceptions.IllegalValueException;

// @@ author A0127686R 
public class DateTimeInfoTest {

    private DateTimeInfo testTime1;
    private DateTimeInfo testTime2;

    @Before
    public void setup() {
        testTime1 = null;
        testTime2 = null;
    }

    @Test
    public void DateTimeInfo_ValidInputWithNoSpecificHoursAndMinutes_returnsDate() {
        final String validInput = "3 July 2018";
        final String expectedSetTime = "Jul 03 2018 08:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_ValidInputWithSpecificHours_returnsDate() {
        final String validInput = "22 january 2017 4pm";
        final String expectedSetTime = "Jan 22 2017 16:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_RelaxValidInputWithNoSpecificHoursAndMinutes_returnsDate() {
        final String validInput = "mon";
        final String expectedSetTime = "Nov 07 2016 08:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_RelaxValidInputWithSpecificHours_returnsDate() {
        final String validInput = "tuesday 10am";
        final String expectedSetTime = "Nov 08 2016 10:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_RelativeValidInputWithNoSpecificHoursAndMinutes_returnsDate() {
        final String validInput = "next month";
        final String expectedSetTime = "Dec 03 2016 08:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_RelativeValidInputSpecificHours_returnsDate() {
        final String validInput = "last month";
        final String expectedSetTime = "Oct 03 2016 08:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test
    public void DateTimeInfo_EndTimeFormat_returnsDate() throws IllegalValueException {
        testTime1 = new DateTimeInfo("June 12 2017");
        testTime1.formatEndTime(testTime1);
        testTime2 = new DateTimeInfo("3pm");
        testTime2.formatEndTime(testTime1);
        final String expectedSetTime1 = "Jun 12 2017 17:00";
        final String expectedSetTime2 = "Jun 12 2017 15:00";
        assertTrue(testTime1.toString().equals(expectedSetTime1));
        assertTrue(testTime2.toString().equals(expectedSetTime2));
    }

    @Test
    public void IsDateNull_DateIsNull_True() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Feb 29 2000 00:00");
        assertTrue(testTime1.isDateNull());
    }

    @Test
    public void IsDateNull_DateIsNotNull_False() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Feb 29 2001 00:00");
        assertTrue(!testTime1.isDateNull());
    }

    @Test
    public void durationOfTheEvent_DurationIsPositive_true() throws IllegalValueException {
        testTime1 = new DateTimeInfo("May 12 2017 07:00");
        testTime2 = new DateTimeInfo("July 19 2017 10:00");
        String expected = "Duration of the event is: 2 months 7 days 3 hours.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));

        testTime2 = new DateTimeInfo("Aug 02 2019 10:30");
        expected = "Duration of the event is: 2 years 2 months 21 days 3 hours 30 minutes.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));

        testTime2 = new DateTimeInfo("Jun 13 2018 08:01");
        expected = "Duration of the event is: 1 year 1 month 1 day 1 hour 1 minute.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));

    }

    @Test
    public void durationOfTheEvent_DurationIsNegative_Message() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Oct 12 2017 07:00");
        testTime2 = new DateTimeInfo("Feb 19 2017 10:00");
        String expected = MESSAGE_FROM_IS_AFTER_TO;
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));

        testTime1 = new DateTimeInfo("Dec 12 2017 07:30");
        testTime2 = new DateTimeInfo("Nov 19 2017 02:10");
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));

        testTime1 = new DateTimeInfo("Sep 12 2017 07:30");
        testTime2 = new DateTimeInfo("March 19 2017 02:10");
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));
    }

    @Test
    public void durationOfTheEvent_ExactSameTiming_Message() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Dec 26 2017 07:00");
        testTime2 = new DateTimeInfo("Dec 26 2017 07:00");
        String expected = "Event starts and end at the same time.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));
    }

    @Test
    public void isInTheFuture_validInput_True() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        testTime2 = new DateTimeInfo("Jun 26 2018 07:00");
        assertTrue(testTime1.isInTheFuture(testTime2));
    }

    @Test
    public void isInTheFuture_invalidInput_False() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        testTime2 = new DateTimeInfo("Jun 26 2018 07:00");
        assertTrue(!testTime2.isInTheFuture(testTime1));
    }

    @Test
    public void isInTheFuture_sameInput_True() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        assertTrue(testTime1.isInTheFuture(testTime1));
    }

    @Test
    public void isInThePast_validInput_True() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        testTime2 = new DateTimeInfo("Jun 26 2018 07:00");
        assertTrue(testTime2.isInThePast(testTime1));
    }

    @Test
    public void isInThePast_invalidInput_False() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        testTime2 = new DateTimeInfo("Jun 26 2018 07:00");
        assertTrue(!testTime1.isInThePast(testTime2));
    }

    @Test
    public void isInThePast_sameInput_True() throws IllegalValueException {
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        assertTrue(!testTime1.isInThePast(testTime1));
    }

    @Test
    public void isTaskAnEventPassingThisDate_DateIsWithinEvent_True() throws IllegalValueException {
        Task event = new Task(new Name("event"), new DateTimeInfo("Feb 29 2000 00:00"),
                new DateTimeInfo("April 26 2017 07:00"), new DateTimeInfo("Jan 26 2018 07:00"));
        testTime1 = new DateTimeInfo("Jun 26 2017 07:00");
        assertTrue(DateTimeInfo.isTaskAnEventPassingThisDate(event, testTime1.toString()));
    }

    @Test
    public void isTaskAnEventPassingThisDate_DateIsNotWithinEvent_True() throws IllegalValueException {
        Task event = new Task(new Name("event"), new DateTimeInfo("Feb 29 2000 00:00"),
                new DateTimeInfo("April 26 2017 07:00"), new DateTimeInfo("Jan 26 2018 07:00"));
        testTime1 = new DateTimeInfo("Jan 26 2017 07:00");
        assertTrue(!DateTimeInfo.isTaskAnEventPassingThisDate(event, testTime1.toString()));
    }

    @Test
    public void isOnTheDate_DateIsNotWithinEvent_True() throws IllegalValueException {
        Task event = new Task(new Name("event"), new DateTimeInfo("Feb 29 2000 00:00"),
                new DateTimeInfo("April 26 2017 07:00"), new DateTimeInfo("Jan 26 2018 07:00"));
        testTime1 = new DateTimeInfo("April 26 2017 07:00");
        assertTrue(testTime1.isOnTheDate(event));
    }

    private void DateTimeInfoAndAssertCorrect(String validInput, String expectedSetTime) {
        try {
            testTime1 = new DateTimeInfo(validInput);
            testTime1.formatStartOrDueDateTime();
        } catch (IllegalValueException e) {
        }
        assertTrue(expectedSetTime.equals(testTime1.toString()));
    }

}
