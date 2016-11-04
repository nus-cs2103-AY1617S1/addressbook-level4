package guitests;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import seedu.cmdo.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
//@@author A0141006B
public class ClearCommandTest extends ToDoListGuiTest {

    @Test
    public void clear() {
    	
    	TestTask[] currentList = td.getTypicalTasks();
    	//sort unsorted list
    	currentList = sortList(currentList);
    	
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(currentList));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.grocery.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.grocery));
        
        runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }
    
    //run commands other than add
    private void runCommand(String input){
    	commandBox.runCommand(input);
    }
    
    //sort list
    private TestTask[] sortList(TestTask... currentList){
    	ArrayList<TestTask> list = new ArrayList<TestTask>(Arrays.asList(currentList));
    	Collections.sort(list);
    	return list.toArray(new TestTask[currentList.length]);
    }

    private void assertClearCommandSuccess() {
        runCommand("clear");
        assertListSize(0);
    }
}
