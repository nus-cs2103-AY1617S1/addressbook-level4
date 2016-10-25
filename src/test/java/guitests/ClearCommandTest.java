package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clearAll() {
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();
    }
    
    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager has been cleared!");
    }
}
