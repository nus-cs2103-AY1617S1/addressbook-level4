package guitests;

import static seedu.menion.logic.commands.CompleteCommand.MESSAGE_COMPLETED_ACTIVITY_SUCCESS;
import org.junit.Test;

import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.address.testutil.TestActivity;

// @author Marx Low (A0139164A)
public class CompleteCommandTest extends AddressBookGuiTest {
    
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
        
        // Runs complete command on positive invalid index
        commandBox.runCommand("clear");
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertCompleteSuccess(activityToComplete, 2);
        
        // Runs complete command on negative index
        commandBox.runCommand(activityToComplete.getAddCommand());
        assertCompleteSuccess(activityToComplete, -1);
        
        // Runs complete command on empty list
        commandBox.runCommand("clear");
        assertCompleteSuccess(activityToComplete, 1); //this should be assertSOMETHINGELSE 
    }
    
    /**
     * checks whether a complete command correctly updates the UI
     * @author Marx Low (A0139164A)
     * @param activityToComplete
     * @param index
     */
    private void assertCompleteSuccess(TestActivity activityToComplete, int index) {
        
        commandBox.runCommand(activityToComplete.getCompleteCommand(index));
        
        //Confirms new Activity card has correct Completed status.
        if (activityToComplete.getActivityType().equals("task")) {
            TaskCardHandle completedCard = activityListPanel.navigateToTask(activityToComplete);
            System.out.println("task is: " + completedCard.toString());
            assertTaskMatching(activityToComplete, completedCard);
        }
        else if (activityToComplete.getActivityType().equals("floatingTask")) {
            FloatingTaskCardHandle completedCard = activityListPanel.navigateToFloatingTask(activityToComplete);
            System.out.println("floating task is: " + completedCard.toString());
            assertFloatingTaskMatching(activityToComplete, completedCard);
        }
        
        // Confirms the result message is correct
        // Debug System.out.println(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
        // assertResultMessage(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }
}
