package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTaskList;
import seedu.todolist.model.task.Status;

import static seedu.todolist.logic.commands.DoneCommand.MESSAGE_MARK_TASK_SUCCESS;

//@@author A0138601M
public class DoneCommandTest extends ToDoListGuiTest {

    @Test
    public void done() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //mark the first in the incomplete list
        int[] targetIndexes = new int[]{1};
        assertDoneSuccess(targetIndexes, currentList, Status.Type.Incomplete);

        //mark the last in the incomplete list
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDoneSuccess(targetIndexes, currentList, Status.Type.Incomplete);

        //mark from the middle of the incomplete list
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDoneSuccess(targetIndexes, currentList, Status.Type.Incomplete);
        
        //mark multiple
        targetIndexes = new int[]{1,2};
        assertDoneSuccess(targetIndexes, currentList, Status.Type.Incomplete);
        
        //mark overdue task
        targetIndexes = new int[]{1};
        assertDoneSuccess(targetIndexes, currentList, Status.Type.Overdue);
        
        //mark completed task
        taskListPanel.clickOnListTab(Status.Type.Complete);
        commandBox.runCommand("done 1");
        assertResultMessage("This task is already completed!");

        //invalid index from incomplete list
        taskListPanel.clickOnListTab(Status.Type.Incomplete);
        commandBox.runCommand("done " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to mark the task at specified index from incomplete list and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking).
     */
    private void assertDoneSuccess(int[] targetIndexes, final TestTaskList currentList, Status.Type type) {
        currentList.markTasksFromList(targetIndexes, type);
        taskListPanel.clickOnListTab(type);
        commandBox.runCommand(getCommand(targetIndexes));

        //confirm the mark task are now in completed list
        assertAllListMatching(currentList);

        //confirm the result message is correct
        assertResultMessage(MESSAGE_MARK_TASK_SUCCESS);
    }
    
    /**
     * Returns the command to be entered
     */
    private String getCommand(int[] targetIndexes) {
        StringBuilder builder = new StringBuilder();
        builder.append("done ");
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
