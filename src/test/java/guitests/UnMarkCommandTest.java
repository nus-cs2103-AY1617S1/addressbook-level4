package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.MarkCommand;
import seedu.taskscheduler.logic.commands.UnmarkCommand;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.ui.TaskCard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UnMarkCommandTest extends TaskSchedulerGuiTest {
    
    //@@author A0138696L
    @Test
    public void unmark() {

        //unmark without index given
        commandBox.runCommand("unmark");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        
        //mark the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        assertMarkSuccess(targetIndex, currentList);
        
        //mark the last in the list
        targetIndex = currentList.length;
        assertMarkSuccess(targetIndex, currentList);
        
        //mark the middle in the list
        targetIndex = currentList.length/2;
        assertMarkSuccess(targetIndex, currentList);
        
        //unmark the first in the list
        currentList = td.getTypicalTasks();
        targetIndex = 1;
        assertUnMarkSuccess(targetIndex, currentList);
        
        //unmark the last in the list
        targetIndex = currentList.length;
        assertUnMarkSuccess(targetIndex, currentList);
        
        //unmark the middle in the list
        targetIndex = currentList.length/2;
        assertUnMarkSuccess(targetIndex, currentList);
        
        //invalid index - unmark
        commandBox.runCommand("unmark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //unmark the first in the list again
        targetIndex = 1;
        commandBox.runCommand("unmark " + targetIndex);
        assertResultMessage(UnmarkCommand.MESSAGE_UNMARK_TASK_FAIL);
        
        //mark empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("unmark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

    }

    //@@author A0148145E
    /**
     * Runs the mark command to mark the task at specified index as completed and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to mark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    public void assertMarkSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        
        TestTask taskToMark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        
        commandBox.runCommand("mark " + targetIndexOneIndexed);
        
        //confirm the task card is now marked completed.
        assertTrue(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getHBoxStyle().equals(TaskCard.COMPLETED_INDICATION));
        assertFalse(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getHBoxStyle().equals(TaskCard.OVERDUE_INDICATION));
        //confirm the result message is correct
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
    
    
    //@@author A0138696L
    /**
     * Runs the unmark command to unmark the task at specified index as uncompleted and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to unmark the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     * Credits: A0148145E - Eugene
     */
    public void assertUnMarkSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        
        TestTask taskToUnMark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        
        commandBox.runCommand("unmark " + targetIndexOneIndexed);
        
        //confirm the task card is now marked completed.
        assertFalse(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getHBoxStyle().equals(TaskCard.COMPLETED_INDICATION));
        //confirm the result message is correct
        assertResultMessage(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, taskToUnMark));
    }
}