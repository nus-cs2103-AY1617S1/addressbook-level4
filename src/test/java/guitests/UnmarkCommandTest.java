package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.logic.commands.UnmarkCommand;
import seedu.agendum.testutil.TestTask;

//@@author A0148031R
public class UnmarkCommandTest extends ToDoListGuiTest {
    @Test
    public void unmark_nonEmptytask_succeed() {
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToUnmark = currentList[0];
        commandBox.runCommand("mark 1");
        assertUnmarkSuccess("unmark 7", taskToUnmark, currentList);
    }
    
    @Test
    public void unmark_nonEmptytask_duplicates() {
        assertUnmarkDuplicates("unmark 1");
    }
    
    @Test
    public void unmark_emptytask() {
        assetUnmarkEmptyTask("unmark 8");
    }
    
    private void assertUnmarkSuccess(String command, TestTask taskToUnmark, TestTask... currentList) {
        commandBox.runCommand(command);
        
        //confirm the new card contains the right data
        if (taskToUnmark.isCompleted()) {
            TaskCardHandle addedCard = completedTasksPanel.navigateToTask(taskToUnmark.getName().fullName);
            assertMatching(taskToUnmark, addedCard);
        } else if (!taskToUnmark.isCompleted() && !taskToUnmark.hasTime()) {
            TaskCardHandle addedCard = floatingTasksPanel.navigateToTask(taskToUnmark.getName().fullName);
            assertMatching(taskToUnmark, addedCard);
        } else if (!taskToUnmark.isCompleted() && taskToUnmark.hasTime()) {
            TaskCardHandle addedCard = upcomingTasksPanel.navigateToTask(taskToUnmark.getName().fullName);
            assertMatching(taskToUnmark, addedCard);
        }
        
        //confirm the list now contains all previous tasks plus the new task
        taskToUnmark.setLastUpdatedTimeToNow();
        TestTask[] expectedList = currentList;
        assertAllPanelsMatch(expectedList);
        assertResultMessage(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS);
    }
    
    private void assertUnmarkDuplicates(String command) {
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_DUPLICATE_TASK);
    }
    
    private void assetUnmarkEmptyTask(String command) {
        commandBox.runCommand(command);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
