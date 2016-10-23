package guitests;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.taskitty.commons.core.Messages;
import seedu.taskitty.logic.commands.SaveCommand;

public class SaveCommandTest extends TaskManagerGuiTest {

    @Test
    public void save() {
        commandBox.runCommand("save temp");
        assertResultMessage(String.format(SaveCommand.MESSAGE_SUCCESS, "temp"));
    }

}
