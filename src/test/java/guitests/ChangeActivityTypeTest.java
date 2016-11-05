package guitests;

import org.junit.Test;

import seedu.menion.testutil.TestActivity;

//@@author A0139164A
public class ChangeActivityTypeTest extends ActivityManagerGuiTest {
    @Test
    public void ChangeType() {
        
        TestActivity floating = td.floatingTask;
        TestActivity task = td.task;
        commandBox.runCommand("clear");
        commandBox.runCommand(floating.getAddCommand());
        assertFloatingTaskChangeSuccess();
        
        commandBox.runCommand("clear");
        commandBox.runCommand(task.getAddCommand());
        assertTaskChangeSuccess();
        
    }
    
    private void assertFloatingTaskChangeSuccess() {
        
    }
    
    private void assertTaskChangeSuccess() {
        
    }
}
