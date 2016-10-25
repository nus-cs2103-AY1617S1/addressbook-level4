package guitests;

import static seedu.taskitty.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskitty.logic.commands.Command;
import seedu.taskitty.logic.commands.SaveCommand;

//@@author A0135793W
public class SaveCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() {
        commandBox.runCommand("save temp");
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, "temp"));
        
        //no filepath
        commandBox.runCommand("save");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + SaveCommand.MESSAGE_PARAMETER));
        
        commandBox.runCommand("save ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Command.MESSAGE_FORMAT + SaveCommand.MESSAGE_PARAMETER));
    }

}
