package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;

//@@author A0138601M
public class DeleteCommandTest extends ToDoListGuiTest {

    @Test
    public void delete() {

        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //delete the first in the list of incomplete tasks
        int[] targetIndexes = new int[]{1};
        assertDeleteSuccess(targetIndexes, currentList, true);

        //delete the last in the list of incomplete tasks
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDeleteSuccess(targetIndexes, currentList, true);

        //delete from the middle of the list of incomplete tasks
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDeleteSuccess(targetIndexes, currentList, true);
        
        //delete multiple of incomplete tasks
        targetIndexes = new int[]{3,2};
        assertDeleteSuccess(targetIndexes, currentList, true);

        //invalid index
        commandBox.runCommand("delete " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the delete command to delete incomplete tasks at the specified indices and confirms the result is correct.
     * @param targetIndexes a list of indexes to be deleted
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDeleteSuccess(int[] targetIndexes, final TestTaskList currentList, boolean isFromIncompleteList) {
        currentList.removeTasksFromList(getTasks(targetIndexes, currentList, isFromIncompleteList), isFromIncompleteList);

        commandBox.runCommand(getCommand(targetIndexes));
        
        //confirm the incomplete list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(currentList.getIncompleteList()));
        
        //confirm the complete list remains unchanged
        assertTrue(completeTaskListPanel.isListMatching(currentList.getCompleteList()));

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
            }
            else {
                builder.append(targetIndexes[i] + ", ");
            }
        }
        return builder.toString();
    }
    
    /**
     * Returns an array of tasks to be deleted
     */
    private TestTask[] getTasks(int[] targetIndexes, TestTaskList currentList, boolean isFromIncompleteList) {
        TestTask[] tasksToDelete = new TestTask[targetIndexes.length];
        for (int i = 0; i < targetIndexes.length; i++) {
            if (isFromIncompleteList) {
                tasksToDelete[i] = currentList.getIncompleteList()[targetIndexes[i] - 1]; //-1 because array uses zero indexing
            }
            else {
                tasksToDelete[i] = currentList.getCompleteList()[targetIndexes[i] - 1];
            }
        }
        return tasksToDelete;
    }

}
