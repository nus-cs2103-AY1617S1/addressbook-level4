package guitests;

import org.junit.Test;

import seedu.malitio.logic.commands.ClearCommand;

import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ClearCommandTest extends MalitioGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(floatingTaskListPanel.isListMatching(td.getTypicalFloatingTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.manualFloatingTask1.getAddCommand());
        assertTrue(floatingTaskListPanel.isListMatching(td.manualFloatingTask1));
        commandBox.runCommand("delete f1");
        assertTotalListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }
    
    //@@author a0126633j
    @Test
    public void clearExpired_containsExpiredTasks_success() {
        commandBox.runCommand("complete d2");
        commandBox.runCommand("complete f1");
        commandBox.runCommand("complete f3");

        commandBox.runCommand(td.manualEvent3.getAddCommand());
        commandBox.runCommand("listall");
        assertTotalListSize(17);
        
        commandBox.runCommand("clear expired"); //clear 4 tasks
        commandBox.runCommand("listall");
        assertTotalListSize(13);
    
        commandBox.runCommand("clear expired"); //does not clear anything
        commandBox.runCommand("listall");
        assertTotalListSize(13);
    }
    
    @Test
    public void clearExpired_invalidArgument_unsuccessful() {
        commandBox.runCommand("clear something");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));        
    }
    //@@author
    
    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertTotalListSize(0);
        assertResultMessage("Malitio has been cleared!");
    }
}
