package guitests;


import static seedu.jimi.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.jimi.logic.commands.ShowCommand;

public class ShowCommandTest extends AddressBookGuiTest {

    @Test
    public void show_floatingTasks() {
        assertShowResult("show floating");
    }
    
    //tests dynamic day titles
    @Test
    public void show_Tuesday() {
        assertShowResult("show Tuesday");
    }
    
    @Test
    public void show_invalidDay() {
        commandBox.runCommand("show floatingtasks");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ShowCommand.MESSAGE_USAGE));
    }

    private void assertShowResult(String command) {
        commandBox.runCommand(command);
        assertResultMessage("Displayed tasks and events.");
    }
}
