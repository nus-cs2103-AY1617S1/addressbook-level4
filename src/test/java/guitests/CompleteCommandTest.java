package guitests;

import static seedu.menion.logic.commands.CompleteCommand.MESSAGE_COMPLETED_ACTIVITY_SUCCESS;
import org.junit.Test;
import guitests.guihandles.ActivityCardHandle;
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
        
        // Runs complete command on an event.
        activityToComplete = td.event;
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
        assertCompleteSuccess(activityToComplete, 1);
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
            ActivityCardHandle completedCard = activityListPanel.navigateToTask(activityToComplete);
            System.out.println("task is: " + completedCard.toString());
            assertMatching(activityToComplete, completedCard);
        }
        else if (activityToComplete.getActivityType().equals("floatingTask")) {
            ActivityCardHandle completedCard = activityListPanel.navigateToFloatingTask(activityToComplete);
            System.out.println("floating task is: " + completedCard.toString());
            assertMatching(activityToComplete, completedCard);
        }
        else if (activityToComplete.getActivityType().equals("event")) {
            ActivityCardHandle completedCard = activityListPanel.navigateToEvent(activityToComplete);
            System.out.println("event is: " + completedCard.toString());
            assertMatching(activityToComplete, completedCard);
        }
        
        // Confirms the result message is correct
        // Debug System.out.println(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
        // assertResultMessage(String.format(MESSAGE_COMPLETED_ACTIVITY_SUCCESS, activityToComplete));
    }
}
