package seedu.todo.model;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.model.task.Completion;

public class CompletionTest {

    
    @Test
    public void completion_test() {
        
        Completion c = new Completion();
        assertFalse(c.isCompleted());
        c.toggle();
        assertTrue(c.isCompleted());
        
    }
    
}
