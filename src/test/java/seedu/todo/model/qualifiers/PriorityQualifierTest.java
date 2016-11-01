//@@author A0121643R
package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

public class PriorityQualifierTest {
    
    @Test
    public void PriorityQualifer_test() throws Exception {
    	    	
    	TestDataHelper helper = new TestDataHelper();
        
    	Task taskLow = helper.generateFullTask(2);
    	
    	Task taskMid = helper.generateFullTaskPriorityMid(0);
    	
    	Task taskHigh = helper.generateFullTaskPriorityHigh(1);
    	
    	Priority priorityLow = taskLow.getPriority();
    	
    	Priority priorityMid = taskMid.getPriority();
    	
    	Priority priorityHigh = taskHigh.getPriority();
    	
    	assertEquals(priorityLow, new Priority("low"));
    	assertNotEquals(priorityLow.toString(), "LOW");
    	
    	assertEquals(priorityMid, new Priority("mid"));
    	assertNotEquals(priorityMid.toString(), "mID");
    	
    	assertEquals(priorityHigh, new Priority("high"));
    	assertNotEquals(priorityHigh.toString(), "HIGH");
    	
    }


    
    
    
    
    
}
