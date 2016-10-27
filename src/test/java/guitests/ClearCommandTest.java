package guitests;

import org.junit.Test;

import seedu.todoList.commons.exceptions.IllegalValueException;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends ListGuiTest {

    @Test
    public void clear() throws IllegalValueException {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicaltasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.a6.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.a6));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("TodoList has been cleared!");
    }
}
