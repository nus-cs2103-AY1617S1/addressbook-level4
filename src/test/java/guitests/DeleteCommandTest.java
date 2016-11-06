package guitests;

import org.junit.Test;
import seedu.task.testutil.TestTaskList;
import seedu.todolist.model.task.Status;

import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

//@@author A0138601M
public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {

        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //delete the first in the list of incomplete tasks
        int[] targetIndexes = new int[]{1};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Incomplete);

        //delete the last in the list of incomplete tasks
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Incomplete);

        //delete from the middle of the list of incomplete tasks
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Incomplete);
        
        //delete multiple of incomplete tasks
        targetIndexes = new int[]{2,1};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Incomplete);
        
        //delete completed task
        targetIndexes = new int[]{1};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Complete);
        
        //delete overdue task
        targetIndexes = new int[]{1};
        assertDeleteSuccess(targetIndexes, currentList, Status.Type.Overdue);

        //invalid index
        commandBox.runCommand("delete " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete incomplete tasks at the specified indices and confirms the result is correct.
     * @param targetIndexes a list of indexes to be deleted
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndexes, TestTaskList currentList, Status.Type type) {
        currentList.removeTasksFromList(targetIndexes, type);
        taskListPanel.clickOnListTab(type);
        commandBox.runCommand(getCommand(targetIndexes));
        
        //confirm the delete task are no longer in the list
        assertAllListMatching(currentList);

        //confirm the result message is correct
        assertResultMessage(MESSAGE_DELETE_TASK_SUCCESS);
    }
    
    /**
     * Returns the command to be entered
     */
    private String getCommand(int[] targetIndexes) {
        StringBuilder builder = new StringBuilder();
        builder.append("delete ");
        for (int i = 0; i < targetIndexes.length; i++) {
            if (i == targetIndexes.length - 1) {
                builder.append(targetIndexes[i]);
            } else {
                builder.append(targetIndexes[i] + ", ");
            }
        }
        return builder.toString();
    }
}
