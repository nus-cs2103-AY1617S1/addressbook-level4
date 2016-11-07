package guitests;

import java.util.List;

import org.junit.Test;

import w15c2.tusk.commons.core.Messages;
import w15c2.tusk.logic.commands.taskcommands.DeleteTaskCommand;
import w15c2.tusk.model.task.Task;
import w15c2.tusk.testutil.TestUtil;

import static org.junit.Assert.*;

//@@author A0138978E
public class DeleteCommandTest extends TaskManagerGuiTest {

    @Test
    public void deleteCommand() {

        //add one task
        List<Task> currentTaskList = TestUtil.getInitialTasks().getInternalList();
        int targetIndex = 1;
        assertDeleteSuccess(targetIndex, currentTaskList);

        //delete the last in the list
        targetIndex = currentTaskList.size();
        assertDeleteSuccess(targetIndex, currentTaskList);

        //delete from the middle of the list
        targetIndex = currentTaskList.size()/2;
        assertDeleteSuccess(targetIndex, currentTaskList);

        //invalid index
        commandBox.runCommand("delete " + currentTaskList.size() + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentTaskList The current list of tasks
     */
    private void assertDeleteSuccess(int targetIndexOneIndexed, List<Task> currentTaskList) {
        Task taskToDelete = currentTaskList.get(targetIndexOneIndexed-1); //-1 because array uses zero indexing
        currentTaskList.remove(taskToDelete);
        
        commandBox.runCommand("delete " + targetIndexOneIndexed);

        //confirm the list now contains all previous tasks except the deleted taks
        assertTrue(taskListPanel.isListMatching(currentTaskList));

        //confirm the result message is correct
        assertResultMessage(String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
