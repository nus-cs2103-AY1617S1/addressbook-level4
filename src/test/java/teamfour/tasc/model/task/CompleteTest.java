//@@author A0140011L
package teamfour.tasc.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import teamfour.tasc.model.task.Complete;

public class CompleteTest {

    @Test
    public void defaultConstructor_notCompleted_returnsNotCompleted() {
        Complete notCompleted = new Complete(false);
        
        assertFalse(notCompleted.isCompleted());
        assertEquals(Complete.TO_STRING_NOT_COMPLETED, notCompleted.toString());
    }
    
    @Test
    public void defaultConstructor_completed_returnsCompleted() {
        Complete completed = new Complete(true);
        
        assertTrue(completed.isCompleted());
        assertEquals(Complete.TO_STRING_COMPLETED, completed.toString());
    }

}
