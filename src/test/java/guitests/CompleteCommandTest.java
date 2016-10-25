package guitests;

import static seedu.menion.logic.commands.CompleteCommand.MESSAGE_COMPLETED_ACTIVITY_SUCCESS;
import org.junit.Test;

import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.menion.commons.core.Messages;
import seedu.menion.model.activity.Activity;
import seedu.menion.testutil.TestActivity;

// @author A0139164A
public class CompleteCommandTest extends ActivityManagerGuiTest {
    
    @Test
    public void complete() {
        
        TestActivity activityToComplete;
        // Runs complete command on a task.
        activityToComplete = td.task;
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertCompleteSuccess(activityToComplete, 1);
        
        // Runs complete command on a floatingTask
        activityToComplete = td.floatingTask;
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertCompleteSuccess(activityToComplete, 1);
        
        // Runs complete command on positive/negative/0 invalid indexes
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertInvalidIndex(activityToComplete, 2); // Only 1 activity in the list.
        assertInvalidIndex(activityToComplete, 0);
        assertInvalidIndex(activityToComplete, -1);

        // Runs complete command on empty list
        commandBox.runCommand("clear");
        assertInvalidIndex(activityToComplete, 1);
    }
    
    /**
     * checks whether a complete command correctly updates the UI
     * @author Marx Low (A0139164A)
     * @param activityToComplete
     * @param index
     */
    private void assertCompleteSuccess(TestActivity activityToComplete, int index) {
        
        commandBox.runCommand(activityToComplete.getCompleteCommand(index));
        boolean isTask = false;
        boolean isFloating = false;
        
        //Confirms new Activity card has correct Completed status.
        if (activityToComplete.getActivityType().equals(Activity.TASK_TYPE)) {
            isTask = true;
            TaskCardHandle completedCard = activityListPanel.navigateToTask(activityToComplete);
            assertTaskMatching(activityToComplete, completedCard);
        }
        else if (activityToComplete.getActivityType().equals(Activity.FLOATING_TASK_TYPE)) {
            isFloating = true;
            FloatingTaskCardHandle completedCard = activityListPanel.navigateToFloatingTask(activityToComplete);
            assertFloatingTaskMatching(activityToComplete, completedCard);
        }
        
        if (isTask) {
            activityToComplete = activityListPanel.returnsUpdatedTask(activityToComplete.getActivityName().fullName);
        }
        else if (isFloating) {
            activityToComplete = activityListPanel.returnsUpdatedFloatingTask(activityToComplete.getActivityName().fullName);
        }
        // Confirms the result message is correct
        assertResultMessage(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }
    
    private void assertInvalidIndex(TestActivity activityToComplete, int index) {
        commandBox.runCommand(activityToComplete.getCompleteCommand(index));
        assertResultMessage(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }
}
