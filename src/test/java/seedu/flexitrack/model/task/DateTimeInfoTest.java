package seedu.flexitrack.model.task;

import org.junit.Before;
import org.junit.Test;
import seedu.flexitrack.model.task.DateTimeInfo;
import seedu.flexitrack.commons.exceptions.IllegalValueException;

import static org.junit.Assert.*;
import static seedu.flexitrack.model.task.DateTimeInfo.MESSAGE_FROM_IS_AFTER_TO;

// @@ author A0127686R 
public class DateTimeInfoTest {

    private DateTimeInfo testTime1;
    private DateTimeInfo testTime2;

    @Before
    public void setup() {
        testTime1 = null; 
        testTime2 = null; 
    }

//    @Test 
//    public void emptyInput_returnsIncorrect() { 
//        final String[] emptyInputs = { "", "  ", "\n  \n" };
//        final String resultMessage = String.format(MESSAGE_DATETIMEINFO_CONSTRAINTS);
//        DateTimeInfoAndAssertIncorrectWithMessage(resultMessage, emptyInputs);
//    }
  
    @Test 
    public void ValidInputWithNoSpecificHoursAndMinutes_returnsDate() { 
        final String validInput = "3 July 2018";
        final String expectedSetTime = "Jul 03 2018 07:59";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test 
    public void ValidInputWithSpecificHours_returnsDate() { 
        final String validInput = "22 january 2017 4pm";
        final String expectedSetTime = "Jan 22 2017 16:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }

    @Test 
    public void RelaxValidInputWithNoSpecificHoursAndMinutes_returnsDate() { 
        final String validInput = "mon";
        final String expectedSetTime = "Oct 31 2016 07:59";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }
    
    @Test 
    public void RelaxValidInputWithSpecificHours_returnsDate() { 
        final String validInput = "tuesday 10am";
        final String expectedSetTime = "Nov 01 2016 10:00";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }
    
    @Test 
    public void RelativeValidInputWithNoSpecificHoursAndMinutes_returnsDate() { 
        final String validInput = "next month";
        final String expectedSetTime = "Nov 27 2016 07:59";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }
    
    @Test 
    public void RelativeValidInputWithSpecificHours_returnsDate() { 
        final String validInput = "last month";
        final String expectedSetTime = "Sep 27 2016 07:59";
        DateTimeInfoAndAssertCorrect(validInput, expectedSetTime);
    }
    
    @Test 
    public void IsDateNull_DateIsNull_True() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Feb 29 2000 00:00"); 
        assertTrue(testTime1.isDateNull());
    }
    
    @Test 
    public void IsDateNull_DateIsNotNull_False() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Feb 29 2001 00:00"); 
        assertTrue(!testTime1.isDateNull());
    }
    
    @Test 
    public void durationOfTheEvent_DurationIsPositive_7Days3hours() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("May 12 2017 07:00"); 
        testTime2 = new DateTimeInfo ("May 19 2017 10:00"); 
        String expected = "Duration of the event is: 7 days 3 hours.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));
    }
    
    @Test 
    public void durationOfTheEvent_DurationIsNegative_Message() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Aug 12 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Mar 19 2017 10:00"); 
        String expected = MESSAGE_FROM_IS_AFTER_TO;
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));
    }

    @Test 
    public void durationOfTheEvent_ExactSameTiming_Message() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        String expected = "Event starts and end at the same time.";
        assertTrue(DateTimeInfo.durationOfTheEvent(testTime1.toString(), testTime2.toString()).equals(expected));
    }
    
    @Test 
    public void isInTheFuture_validInput_True() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Jun 26 2018 07:00"); 
        assertTrue(DateTimeInfo.isInTheFuture(testTime1, testTime2));
    }
    
    @Test 
    public void isInTheFuture_invalidInput_False() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Jun 26 2018 07:00"); 
        assertTrue(!DateTimeInfo.isInTheFuture(testTime2, testTime1));
    }
    
    @Test 
    public void isInTheFuture_sameInput_True() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        assertTrue(DateTimeInfo.isInTheFuture(testTime1, testTime1));
    }
    
    @Test 
    public void isInThePast_validInput_True() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Jun 26 2018 07:00"); 
        assertTrue(DateTimeInfo.isInThePast(testTime2, testTime1));
    }
    
    @Test 
    public void isInThePast_invalidInput_False() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        testTime2 = new DateTimeInfo ("Jun 26 2018 07:00"); 
        assertTrue(!DateTimeInfo.isInThePast(testTime1, testTime2));
    }
    
    @Test 
    public void isInThePast_sameInput_True() throws IllegalValueException { 
        testTime1 = new DateTimeInfo ("Jun 26 2017 07:00"); 
        assertTrue(DateTimeInfo.isInThePast(testTime1, testTime1));
    }
    
    private void DateTimeInfoAndAssertCorrect(String validInput, String expectedSetTime) {
        try {
            testTime1 = new DateTimeInfo (validInput);
        } catch (IllegalValueException e) {
        }
        assertTrue(expectedSetTime.equals(testTime1.toString()));
    }

}
