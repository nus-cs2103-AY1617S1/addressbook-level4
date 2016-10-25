package guitests;

import static seedu.menion.logic.commands.UnCompleteCommand.MESSAGE_UNCOMPLETED_ACTIVITY_SUCCESS;
import org.junit.Test;

import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.menion.commons.core.Messages;
import seedu.menion.model.activity.Activity;
import seedu.menion.testutil.TestActivity;

//@@author A0139164A
public class UnCompleteCommandTest extends ActivityManagerGuiTest {
    
    @Test
    public void uncomplete() {
        
        TestActivity activityToUncomplete;
        
        // Runs uncomplete  on a task.
        activityToUncomplete = td.task;
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToUncomplete.getAddCommand());
        commandBox.runCommand(activityToUncomplete.getCompleteCommand(1));
        assertUncompleteSuccess(activityToUncomplete, 1);
        
        // Runs uncomplete command on a floatingTask
        activityToUncomplete = td.floatingTask;
        commandBox.runCommand(activityToUncomplete.getAddCommand());
        commandBox.runCommand(activityToUncomplete.getCompleteCommand(1));
        assertUncompleteSuccess(activityToUncomplete, 1);
        
        // Runs uncomplete command on positive/negative/0 invalid indexes
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToUncomplete.getAddCommand());
        assertInvalidIndex(activityToUncomplete, 2); // Only 1 activity in the list.
        assertInvalidIndex(activityToUncomplete, 0);
        assertInvalidIndex(activityToUncomplete, -1);

        // Runs uncomplete command on empty list
        commandBox.runCommand("clear");
        assertInvalidIndex(activityToUncomplete, 1);
    }
    
    /**
     * checks whether a uncomplete command correctly updates the UI
     * @author Marx Low (A0139164A)
     * @param activityToUncomplete
     * @param index
     */
    private void assertUncompleteSuccess(TestActivity activityToUncomplete, int index) {
        
        commandBox.runCommand(activityToUncomplete.getUncompleteCommand(index));
        boolean isTask = false;
        boolean isFloating = false;
        
        //Confirms new Activity card has correct Completed status.
        if (activityToUncomplete.getActivityType().equals(Activity.TASK_TYPE)) {
            isTask = true;
            TaskCardHandle uncompletedCard = activityListPanel.navigateToTask(activityToUncomplete);
            assertTaskMatching(activityToUncomplete, uncompletedCard);
        }
        else if (activityToUncomplete.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            isFloating = true;
            FloatingTaskCardHandle uncompletedCard = activityListPanel.navigateToFloatingTask(activityToUncomplete);
            assertFloatingTaskMatching(activityToUncomplete, uncompletedCard);
        }
        
        if (isTask) {
            activityToUncomplete = activityListPanel.returnsUpdatedTask(activityToUncomplete.getActivityName().fullName);
        }
        else if (isFloating) {
            activityToUncomplete = activityListPanel.returnsUpdatedFloatingTask(activityToUncomplete.getActivityName().fullName);
        }
        
        // Confirms the result message is correct
        assertResultMessage(String.format(MESSAGE_UNCOMPLETED_ACTIVITY_SUCCESS, activityToUncomplete));
    }
    
    private void assertInvalidIndex(TestActivity activityToUncomplete, int index) {
        commandBox.runCommand(activityToUncomplete.getUncompleteCommand(index));
        assertResultMessage(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }
}