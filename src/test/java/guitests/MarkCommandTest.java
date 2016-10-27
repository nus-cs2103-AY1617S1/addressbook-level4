package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.MarkCommand;
import seedu.taskscheduler.testutil.TestTask;
import seedu.taskscheduler.ui.TaskCard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class MarkCommandTest extends TaskSchedulerGuiTest {
    
    //@@author A0138696L
    @Test
    public void mark() {

        //mark without index given
        commandBox.runCommand("mark");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        
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
        
        //invalid index
        commandBox.runCommand("mark " + currentList.length + 1);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //mark the first in the list again
        targetIndex = 1;
        commandBox.runCommand("mark " + targetIndex);
        assertResultMessage(MarkCommand.MESSAGE_MARK_TASK_FAIL);
        
        //mark empty list
        commandBox.runCommand("clear");
        commandBox.runCommand("mark " + currentList.length + 1);
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
        assertTrue(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getPaintFromShape()
                .equals(TaskCard.COMPLETED_INDICATION));
        assertFalse(taskListPanel.navigateToTask(targetIndexOneIndexed - 1).getPaintFromShape()
                .equals(TaskCard.OVERDUE_INDICATION));
        //confirm the result message is correct
        assertResultMessage(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }
}