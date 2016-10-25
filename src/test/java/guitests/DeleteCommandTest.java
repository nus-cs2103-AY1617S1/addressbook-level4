package guitests;

import org.junit.Test;

import seedu.address.model.task.TaskComponent;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

import java.util.ArrayList;
import java.util.List;

public class DeleteCommandTest extends TaskMasterGuiTest {

    @Test
    public void delete() {

        //delete the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentList);

        //delete the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDeleteSuccess(targetIndex, currentList);

        //delete from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDeleteSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("delete " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete the floatingTask at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first floatingTask in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of floatingTasks (before deletion).
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask floatingTaskToDelete = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("delete " + targetIndexOneIndexed);

        List<TaskComponent> componentList = new ArrayList<TaskComponent>();
        for(TestTask t : expectedRemainder) {
            componentList.addAll(t.getTaskDateComponent());
        }
        //confirm the list now contains all previous floatingTasks except the deleted floatingTask
        assertTrue(taskListPanel.isListMatching(TestUtil.convertTasksToDateComponents(expectedRemainder)));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, floatingTaskToDelete));
    }

}
