package seedu.address.model;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.EventDate;

//@@author A0142325R

/*
 * Tests for updating out-dated recurring tasks
 */
public class RecurringTest {
    
    //--------------------------Tests for updating deadline------------------------------------
    
    /*
     * Equivalence partitions for DeadlineFormat:
     *  - deadline of dateFormat "dd.MM.yyyy"
     *  - deadline of dateTimeFormat "dd.MM.yyyy-XX"
     *  
     *  Equivalence partitions for numDaysToUpdate:
     *  - existing date + numDaysToUpdate < numDaysInCurrentMonth
     *  - existing date + numDaysToUpdate > numDaysInCurrentMonth
     *  
     */
    
    //test for deadlineDateFormat, numDaysToUpdate + existing date < numDaysInCurrentMonth
    @Test
    public void refresh_updateDeadlineDateFormat_success() throws IllegalValueException{
        Deadline d1=new Deadline("01.01.2016");
        d1.updateRecurringDate(2);
        assertEquals("03.01.2016",d1.getValue());
    }
    
    //test for deadlineDateTimeFormat, numDaysToUpdate + existing date < numDaysInCurrentMonth
    @Test
    public void refresh_updateDeadlineDateTimeFormat_succcess() throws IllegalValueException{
        Deadline d2=new Deadline("01.01.2016-14");
        d2.updateRecurringDate(14);
        assertEquals("15.01.2016-14",d2.getValue());
    }
    
    //test for numDaysToUpdate + existing date > numDaysInCurrentMonth
    @Test
    public void refresh_updateNumDaysExceedCurrentMonth_success() throws IllegalValueException{
        Deadline d3=new Deadline("01.02.2016-14");
        d3.updateRecurringDate(60);
        assertEquals("01.04.2016-14",d3.getValue());
    }
    
    //-----------------------------Tests for updating eventDate-------------------------------
    
    /*
     * Equivalence partitions for event date format:
     *  - eventDateFormat ( "dd.MM.yyyy", "dd.MM.yyyy" )
     *  - eventDateTimeFormat ( "dd.MM.yyyy-XX", "dd.MM.yyyy-XX")
     *  - eventDateTimeMixedFormat ( "dd.MM.yyyy-XX", "dd.MM.yyyy" )
     */
    
    //test eventDateFormat with numDaysToUpdate + existingDate > numDaysInMonth
    @Test
    public void refresh_updateEventDateFormat_success() throws IllegalValueException{
        EventDate d4=new EventDate("01.02.2016","02.02.2016");
        d4.updateRecurringDate(60);
        assertEquals(d4.getValue(),"01.04.2016 to 02.04.2016");
    }
    
    //test eventDateTimeFormat with numDaysToUpdate + existingDate < numDaysInMonth
    @Test
    public void refresh_updateEventDateTimeFormat_success() throws IllegalValueException{
        EventDate d5=new EventDate("01.01.2016-14","02.01.2016-16");
        d5.updateRecurringDate(3);
        assertEquals(d5.getValue(),"04.01.2016-14 to 05.01.2016-16");
    }
    
    //test eventDateTimeMixedFormat
    @Test
    public void refresh_updateEventDateTimeMixedFormat_success() throws IllegalValueException{
        EventDate d6=new EventDate("01.01.2016-16","02.01.2016");
        d6.updateRecurringDate(3);
        assertEquals(d6.getValue(),"04.01.2016-16 to 05.01.2016");
    }
    
    
}
