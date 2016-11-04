package guitests;

import org.junit.Test;

import seedu.oneline.logic.commands.ClearCommand;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clearCommand_clearTasks_success() {
        TestTask[] ts = td.getTypicalTasks();
        Arrays.sort(ts);
        assertTrue(taskPane.isListMatching(ts));
        assertClearCommandSuccess();
    }
    
    @Test
    public void clearCommand_addAfterClear_success() {
        assertClearCommandSuccess();
        commandBox.runCommand(TypicalTestTasks.todo1.getAddCommand());
        assertTrue(taskPane.isListMatching(TypicalTestTasks.todo1));
        commandBox.runCommand("del 1");
        assertListSize(0);
    }
    
    @Test
    public void clearCommand_clearEmptyList_success() {
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
