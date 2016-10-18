package guitests;

import org.junit.Test;

import seedu.cmdo.testutil.TestTask;
import seedu.cmdo.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.cmdo.logic.commands.ListCommand.MESSAGE_SUCCESS;;

public class ListCommandTest extends ToDoListGuiTest {

    @Test
    public void list() {

        //done the first task in the list
        TestTask[] currentList = td.getTypicalTasks();
        assertListSuccess("list", currentList);
        assertListSuccess("la", currentList);

        //done a task that is the last in the list
        commandBox.runCommand("done 1");
        TestTask[] doneList = td.getEmptyTasks();
        doneList = TestUtil.addTasksToList(doneList, currentList[0]);
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertListSuccess("ld", doneList);
        assertListSuccess("list done", doneList);
        
        //remove task from the list
        commandBox.runCommand("list all");
        commandBox.runCommand("delete 1");
        currentList = TestUtil.removeTaskFromList(currentList, 1);
        assertListSuccess("la", currentList);
    }

    /**
     * Runs the list command to change the task done status at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to done the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before done).
     */
    private void assertListSuccess(final String type, final TestTask[] currentList) {
        commandBox.runCommand(type);
        //confirm the list now contains all previous tasks except the done task
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}