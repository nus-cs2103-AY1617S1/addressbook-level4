package guitests;

import org.junit.Test;

import seedu.ggist.commons.exceptions.IllegalValueException;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() throws IllegalArgumentException, IllegalValueException {

        //verify a non-empty list can be cleared
       // assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.dance.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.dance));
        commandBox.runCommand("delete 0");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("GGist has been cleared!");
    }
}
