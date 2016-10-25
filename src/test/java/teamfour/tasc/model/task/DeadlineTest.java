//@@author A0140011L
package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import teamfour.tasc.model.task.Deadline;

public class DeadlineTest {

    @Test
    public void defaultConstructor_noInput_returnsEmptyDeadline() {
        Deadline noDeadline = new Deadline();
        
        assertFalse(noDeadline.hasDeadline());
        assertNull(noDeadline.getDeadline());
    }
    
    @Test
    public void constructor_validDateGiven_returnsDeadlineWithDate() {
        Date date = new Date();
        Deadline deadline = new Deadline(date);
        
        assertTrue(deadline.hasDeadline());
        assertEquals(date, deadline.getDeadline());
    }
    
    @Test
    public void toString_noDeadline_returnsNoDeadline() {
        Deadline noDeadline = new Deadline();
        
        assertEquals(Deadline.TO_STRING_NO_DEADLINE, noDeadline.toString());
    }
    
    @Test
    public void toString_validDeadline_returnsSameDate() {
        Date date = new Date();
        Deadline deadline = new Deadline(date);
        
        assertEquals(date.toString(), deadline.toString());   
    }
    
    @Test
    public void equals_nonDeadlineObject_returnsFalse() {
        Deadline noDeadline = new Deadline();
        
        assertFalse(noDeadline.equals("String"));
    }
}
