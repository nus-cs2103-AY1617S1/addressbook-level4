package guitests;

import org.junit.Test;

import seedu.oneline.logic.commands.ClearCommand;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

public class ClearCommandTest extends TaskBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTask[] ts = td.getTypicalTasks();
        Arrays.sort(ts);
        assertTrue(taskPane.isListMatching(ts));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.todo1.getAddCommand());
        assertTrue(taskPane.isListMatching(td.todo1));
        commandBox.runCommand("del 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
