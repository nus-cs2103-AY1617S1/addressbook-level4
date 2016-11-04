package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.DoneCommand.MESSAGE_DONE_TASK_SUCCESS;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestTaskArraySorter;
import seedu.cmdo.testutil.TestUtil;

/*
 * @@author A0141128R
 */
public class DoneCommandTest extends ToDoListGuiTest {

    @Test
    public void done() {

        TestTask[] currentList = td.getTypicalTasks();
        
        //done the first task in the list
        int targetIndex = 1;
        assertdoneSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex, currentList);

        //done a task that is the last in the list
        targetIndex = currentList.length;
        assertdoneSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex, currentList);

        //done task from the middle of the list
        targetIndex = currentList.length/2;
        assertdoneSuccess(targetIndex, currentList);
        currentList = updateList(targetIndex, currentList);

        //invalid index
        runDoneCommand(currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }
    
    //run done command
    private void runDoneCommand(int targetIndex){
    	commandBox.runCommand("done " + targetIndex);
    }
    
    //update list
    private TestTask[] updateList(int targetIndex, TestTask... currentList){
    	return TestUtil.removeTaskFromList(currentList, targetIndex);
    }
    
    //confirm the list now contains all previous tasks except the deleted task
    private void compareList(TestTask[] expectedRemainder){
    	  assertTrue(taskListPanel.isListMatching(expectedRemainder));
    }


    /**
     * Runs the done command to change the task done status at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to done the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before done).
     */
    private void assertdoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        
        runDoneCommand(targetIndexOneIndexed);
        
        TestTask[] expectedRemainder = updateList(targetIndexOneIndexed, currentList);

        //confirm the list now contains all previous tasks except the done task
        compareList(TestTaskArraySorter.sortTestTasks(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DONE_TASK_SUCCESS, taskToDone));
    }

}
