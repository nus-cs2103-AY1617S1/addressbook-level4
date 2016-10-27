package guitests;

import org.junit.Test;

import seedu.taskmanager.logic.commands.ClearCommand;
import seedu.taskmanager.logic.commands.UndoCommand;

import static org.junit.Assert.assertTrue;
import static seedu.taskmanager.logic.commands.ClearCommand.COMMAND_WORD;
import static seedu.taskmanager.logic.commands.ClearCommand.SHORT_COMMAND_WORD;

public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(shortItemListPanel.isListMatching(td.getTypicalItems()));
        assertClearCommandSuccess(COMMAND_WORD);
        commandBox.runCommand(UndoCommand.COMMAND_WORD);
        assertTrue(shortItemListPanel.isListMatching(td.getTypicalItems()));
        assertClearCommandSuccess(SHORT_COMMAND_WORD);

        //verify other commands can work after a clear command
        commandBox.runCommand(td.deadline3.getAddCommand(false, false, false, false, false));
        assertTrue(shortItemListPanel.isListMatching(td.deadline3));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess(COMMAND_WORD);
        assertClearCommandSuccess(SHORT_COMMAND_WORD);
    }

    private void assertClearCommandSuccess(String commandWord) {
        commandBox.runCommand(commandWord);
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS);
    }
}
