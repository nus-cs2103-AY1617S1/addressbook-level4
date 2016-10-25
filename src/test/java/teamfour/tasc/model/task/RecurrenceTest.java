package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.model.task.Recurrence;

public class RecurrenceTest {

    private Date firstDayOfDecember;
    private Date secondDayOfDecember;
    private Date eighthDayOfDecember;
    private Date firstDayOfJanuaryNextYear;
    private Date firstDayOfDecemberNextYear;
    
    @Before
    public void setUp() throws Exception {
        Calendar calendar = Calendar.getInstance();
        
        calendar.set(2000, 11, 1, 0, 0, 0);        
        firstDayOfDecember = calendar.getTime();
        
        calendar.set(2000, 11, 2, 0, 0, 0);        
        secondDayOfDecember = calendar.getTime();
                
        calendar.set(2000, 11, 8, 0, 0, 0);        
        eighthDayOfDecember = calendar.getTime();
        
        calendar.set(2001, 0, 1, 0, 0, 0);        
        firstDayOfJanuaryNextYear = calendar.getTime();
        
        calendar.set(2001, 11, 1, 0, 0, 0);        
        firstDayOfDecemberNextYear = calendar.getTime();
    }

    @Test
    public void defaultConstructor_noInput_returnsNoRecurrence() {
        Recurrence noRecurrence = new Recurrence();
        
        assertFalse(noRecurrence.hasRecurrence());
        assertEquals(Recurrence.Pattern.NONE, noRecurrence.getPattern());
        assertEquals(0, noRecurrence.getFrequency());
    }
    
    @Test (expected = AssertionError.class)
    public void constructor_noPatternValidFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidPattern = new Recurrence(Recurrence.Pattern.NONE, 1);
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_negativeFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidFrequency = new Recurrence(Recurrence.Pattern.DAILY, -1);
    }
    
    @Test (expected = IllegalValueException.class)
    public void constructor_zeroFrequency_throwsException() throws IllegalValueException {
        Recurrence invalidFrequency = new Recurrence(Recurrence.Pattern.DAILY, 0);
    }
    
    @Test
    public void constructor_validPatternFrequency_returnsCorrectRecurrence() throws IllegalValueException {
        Recurrence.Pattern pattern = Recurrence.Pattern.WEEKLY;
        int frequency = 1;
        
        Recurrence recurrence = new Recurrence(pattern, frequency);
        assertTrue(recurrence.hasRecurrence());
        assertEquals(pattern, recurrence.getPattern());
        assertEquals(frequency, recurrence.getFrequency());
    }
    
    @Test
    public void toString_noRecurrence_returnsNoRecurrence() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();
        
        assertEquals(Recurrence.TO_STRING_NO_RECURRENCE, noRecurrence.toString());
    }
    
    @Test
    public void toString_recurrence_returnsCorrectValues() throws IllegalValueException {
        Recurrence recurrence = new Recurrence(Recurrence.Pattern.WEEKLY, 5);
        
        assertEquals("WEEKLY [5 time(s)]", recurrence.toString());
    }
    
    @Test
    public void equals_nonRecurrenceObject_returnsFalse() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();
        
        assertFalse(noRecurrence.equals("String"));
    }
    

    /* --- Test for Recurrence.getNextDateAfterRecurrence()
     * 
     * Equivalence partitions are:
     * 1. no recurrence
     * 2. daily
     * 3. weekly
     * 4. monthly
     * 5. yearly
     * 
     * (EP: Frequency does not matter and will not matter)
     * (EP: Boundary for date values (e.g. 31 -> 1) should not be tested because
     * that is testing the Java API -> not our problem if Java API is bugged.
     * Java API used = LocalDateTime)
     */
    
    // 1
    @Test
    public void getNextDateAfterRecurrence_noRecurrence_returnsNull() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();

        assertEquals(null, noRecurrence.getNextDateAfterRecurrence(new Date(0)));
    }

    // 2
    @Test
    public void getNextDateAfterRecurrence_daily_returnsTomorrow() throws IllegalValueException {
        Recurrence daily = new Recurrence(Recurrence.Pattern.DAILY, 1);

        assertEquals(secondDayOfDecember, daily.getNextDateAfterRecurrence(firstDayOfDecember));
    }

    // 3
    @Test
    public void getNextDateAfterRecurrence_weekly_returnsNextWeek() throws IllegalValueException {
        Recurrence weekly = new Recurrence(Recurrence.Pattern.WEEKLY, 1);

        assertEquals(eighthDayOfDecember, weekly.getNextDateAfterRecurrence(firstDayOfDecember));
    }

    // 4
    @Test
    public void getNextDateAfterRecurrence_monthly_returnsNextMonth() throws IllegalValueException {
        Recurrence monthly = new Recurrence(Recurrence.Pattern.MONTHLY, 1);

        assertEquals(firstDayOfJanuaryNextYear, monthly.getNextDateAfterRecurrence(firstDayOfDecember));
    }
    
    // 5
    @Test
    public void getNextDateAfterRecurrence_yearly_returnsNextYear() throws IllegalValueException {
        Recurrence yearly = new Recurrence(Recurrence.Pattern.YEARLY, 1);

        assertEquals(firstDayOfDecemberNextYear, yearly.getNextDateAfterRecurrence(firstDayOfDecember));
    }
    
    /* --- Test for Recurrence.getRecurrenceWithOneFrequencyLess()
     * 
     * Equivalence partitions are:
     * 1. no recurrence
     * 2. frequency = 1
     * 3. frequency = 2, daily
     * 4. frequency = 2, weekly
     * 5. frequency = 2, monthly
     * 6. frequency = 2, yearly
     * 
     * (Heuristics: Pattern should not really matter when frequency = 1)
     * (Boundary: 0, 1, >1)
     * (EP: 2, 3, 4, 5, etc are all the same, testing for those is pointless)
     */
    
    // 1
    @Test
    public void getRecurrenceWithOneFrequencyLess_noRecurrence_returnsNull() throws IllegalValueException {
        Recurrence noRecurrence = new Recurrence();
        
        assertEquals(null, noRecurrence.getRecurrenceWithOneFrequencyLess());
    }
    
    // 2
    @Test
    public void getRecurrenceWithOneFrequencyLess_oneRecurrence_returnsNoRecurrence() throws IllegalValueException {
        Recurrence oneRecurrence = new Recurrence(Recurrence.Pattern.DAILY, 1);
        Recurrence expected = new Recurrence();
        
        assertEquals(expected, oneRecurrence.getRecurrenceWithOneFrequencyLess());
    }

    // 3
    @Test
    public void getRecurrenceWithOneFrequencyLess_dailyMoreThanOneRecurrence_returnsOneLeft() throws IllegalValueException {
        Recurrence twoRecurrence = new Recurrence(Recurrence.Pattern.DAILY, 2);
        Recurrence expected = new Recurrence(Recurrence.Pattern.DAILY, 1);
        
        assertEquals(expected, twoRecurrence.getRecurrenceWithOneFrequencyLess());
    }


    // 4
    @Test
    public void getRecurrenceWithOneFrequencyLess_weeklyMoreThanOneRecurrence_returnsOneLeft() throws IllegalValueException {
        Recurrence twoRecurrence = new Recurrence(Recurrence.Pattern.WEEKLY, 2);
        Recurrence expected = new Recurrence(Recurrence.Pattern.WEEKLY, 1);
        
        assertEquals(expected, twoRecurrence.getRecurrenceWithOneFrequencyLess());
    }


    // 5
    @Test
    public void getRecurrenceWithOneFrequencyLess_monthlyMoreThanOneRecurrence_returnsOneLeft() throws IllegalValueException {
        Recurrence twoRecurrence = new Recurrence(Recurrence.Pattern.MONTHLY, 2);
        Recurrence expected = new Recurrence(Recurrence.Pattern.MONTHLY, 1);
        
        assertEquals(expected, twoRecurrence.getRecurrenceWithOneFrequencyLess());
    }


    // 6
    @Test
    public void getRecurrenceWithOneFrequencyLess_yearlyMoreThanOneRecurrence_returnsOneLeft() throws IllegalValueException {
        Recurrence twoRecurrence = new Recurrence(Recurrence.Pattern.YEARLY, 2);
        Recurrence expected = new Recurrence(Recurrence.Pattern.YEARLY, 1);
        
        assertEquals(expected, twoRecurrence.getRecurrenceWithOneFrequencyLess());
    }
}
