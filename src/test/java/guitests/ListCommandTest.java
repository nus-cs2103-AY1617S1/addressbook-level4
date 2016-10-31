package guitests;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
//@@author A0141128R
public class ListCommandTest extends ToDoListGuiTest {

    @Test
    public void list() {

        TestTask[] currentList = td.getTypicalTasks();
        TestTask[] doneList = td.getEmptyTasks();
        TestTask[] blockList = td.getEmptyTasks();
        
        
        //test for list block
        TestTask timeToBlock = td.meeting;
        commandBox.runCommand(timeToBlock.getBlockCommand());
        blockList = TestUtil.addTasksToList(doneList, timeToBlock);
        currentList = TestUtil.addTasksToList(currentList, timeToBlock);
        assertListSuccess("lb", blockList);
        assertListSuccess("list block", blockList);
        
        //list all the list
        assertListSuccess("la", currentList);
        
        //done a task that is the first in the list
        runCommand("done 1");
        doneList = TestUtil.addTasksToList(doneList, currentList[0]);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertListSuccess("ld", doneList);
        assertListSuccess("list done", doneList);
        
        //list all the list
        assertListSuccess("list all", currentList);
        
        //remove task from the list
        runCommand("delete 2");
        currentList = TestUtil.removeTaskFromList(currentList, 2);
        assertListSuccess("la", currentList);
    }
    
    private void runCommand(String input){
    	commandBox.runCommand(input);
    }

    /**
     * Runs the list command to change the task done status at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to done the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before done).
     */
    private void assertListSuccess(final String type, final TestTask[] currentList) {
        runCommand(type);
        //confirm the list now contains all previous tasks except the done task
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}