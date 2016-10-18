package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.malitio.logic.commands.EditCommand.MESSAGE_EDIT_TASK_SUCCESS;

import org.junit.Test;

import seedu.malitio.testutil.TestTask;
import seedu.malitio.testutil.TestUtil;

public class EditCommandTest extends MalitioGuiTest {
   
    @Test
    public void edit() {

        //edit the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertEditSuccess(targetIndex, currentList);

        //edit the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertEditSuccess(targetIndex, currentList);

        //edit from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertEditSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("edit " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }
    
    /**
     * Runs the edit command to edit the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertEditSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToEdit = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("edit " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }
}
