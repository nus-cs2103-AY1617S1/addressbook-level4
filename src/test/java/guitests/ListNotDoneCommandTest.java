package guitests;

import static seedu.taskmanager.logic.commands.ListNotDoneCommand.COMMAND_WORD;
import static seedu.taskmanager.logic.commands.ListNotDoneCommand.SHORT_COMMAND_WORD;
import static seedu.taskmanager.logic.commands.ListNotDoneCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.taskmanager.commons.core.Messages;

//@@author A0140060A
public class ListNotDoneCommandTest extends TaskManagerGuiTest {
	
	@Test
	public void listNotDone_success() {
	    assertListNotDoneCommandSuccess(COMMAND_WORD);
        assertListNotDoneCommandSuccess(SHORT_COMMAND_WORD);
	}
	
	@Test
    public void listNotDone_invalidCommand_unknownCommandMessage() {
        commandBox.runCommand("listnotdones");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
	
	
	private void assertListNotDoneCommandSuccess(String commandWord) {
        commandBox.runCommand(commandWord);
        assertResultMessage(MESSAGE_SUCCESS);
    }

}


