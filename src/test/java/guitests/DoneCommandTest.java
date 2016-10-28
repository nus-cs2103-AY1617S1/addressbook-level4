package guitests;

import org.junit.Test;

import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestTaskList;

import static org.junit.Assert.assertTrue;
import static seedu.todolist.logic.commands.DoneCommand.MESSAGE_MARK_TASK_SUCCESS;

//@@author A0138601M
public class DoneCommandTest extends ToDoListGuiTest {

    @Test
    public void done() {
        
        TestTaskList currentList = new TestTaskList(td.getTypicalTasks());
        
        //mark the first in the incomplete list
        int[] targetIndexes = new int[]{1};
        assertDoneSuccess(targetIndexes, currentList);

        //mark the last in the incomplete list
        targetIndexes = new int[]{currentList.getIncompleteList().length};
        assertDoneSuccess(targetIndexes, currentList);

        //mark from the middle of the incomplete list
        targetIndexes = new int[]{currentList.getIncompleteList().length/2};
        assertDoneSuccess(targetIndexes, currentList);
        
        //delete multiple
        targetIndexes = new int[]{1,2};
        assertDoneSuccess(targetIndexes, currentList);

        //invalid index from incomplete list
        commandBox.runCommand("done " + currentList.getIncompleteList().length + 1);
        assertResultMessage("The task index provided is invalid");

    }

    /**
     * Runs the done command to mark the task at specified index from incomplete list and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before marking).
     */
    private void assertDoneSuccess(int[] targetIndexes, final TestTaskList currentList) {
        currentList.markTasksFromList(getTasks(targetIndexes, currentList));   
        
        commandBox.runCommand(getCommand(targetIndexes));

        //confirm the incomplete list now contains all previous tasks except the marked task
        assertTrue(taskListPanel.isListMatching(currentList.getIncompleteList()));
        
        //confirm complete list contains all marked task
        assertTrue(completeTaskListPanel.isListMatching(currentList.getCompleteList()));

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
            }
            else {
                builder.append(targetIndexes[i] + ", ");
            }
        }
        return builder.toString();
    }
    
    /**
     * Returns an array of tasks to be marked
     */
    private TestTask[] getTasks(int[] targetIndexes, TestTaskList currentList) {
        TestTask[] tasksToMark = new TestTask[targetIndexes.length];
        for (int i = 0; i < targetIndexes.length; i++) {

            tasksToMark[i] = currentList.getIncompleteList()[targetIndexes[i] - 1]; //-1 because array uses zero indexing
        }
        return tasksToMark;
    }

}
