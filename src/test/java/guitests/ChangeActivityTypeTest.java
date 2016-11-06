package guitests;

import org.junit.Test;

import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.TaskCardHandle;
import seedu.menion.testutil.TestActivity;

//@@author A0139164A
public class ChangeActivityTypeTest extends ActivityManagerGuiTest {
    @Test
    public void ChangeType() {
        
        TestActivity floating = td.floatingTask;
        TestActivity task = td.task;
        
        commandBox.runCommand("clear");
        commandBox.runCommand(floating.getAddCommand());
        assertFloatingTaskChangeSuccess(floating, 1, "12-12-2016 1700");
        
        commandBox.runCommand("clear");
        commandBox.runCommand(task.getAddCommand());
        assertTaskChangeSuccess(task, 1);
        
    }
    
    private void assertFloatingTaskChangeSuccess(TestActivity floating, int index, String newDateTime) {
        commandBox.runCommand(floating.getFloatingTaskChangeCommand(index, newDateTime));
        System.out.println(floating.getFloatingTaskChangeCommand(index, newDateTime));
        TestActivity task = activityListPanel.returnsUpdatedTask(floating.getActivityName().fullName);
        TaskCardHandle editedCard = activityListPanel.navigateToTask(task);
        assertTaskMatching(task, editedCard);
    }
    
    private void assertTaskChangeSuccess(TestActivity task, int index) {
        commandBox.runCommand(task.getTaskChangeCommand(index));
        TestActivity floating = activityListPanel.returnsUpdatedFloatingTask(task.getActivityName().fullName); // Update floatingTask with new changes
        FloatingTaskCardHandle editedCard = activityListPanel.navigateToFloatingTask(floating); // Check against card.
        assertFloatingTaskMatching(floating, editedCard);
    }
}
