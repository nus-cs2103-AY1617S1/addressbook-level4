package guitests;

import org.junit.Test;

import seedu.inbx0.commons.exceptions.IllegalValueException;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskListGuiTest {

    @Test
    public void clear() throws IllegalArgumentException, IllegalValueException {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.hoon));
        commandBox.runCommand("del 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clr");
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }
}
