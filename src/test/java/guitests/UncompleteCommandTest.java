package guitests;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.logic.commands.UncompleteCommand;

import org.junit.Test;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//@@author A0122460W
public class UncompleteCommandTest extends MalitioGuiTest {

    @Test
    public void uncompleteFloatingtask() {
        
        // cannot uncomplete a uncompleted floating task
        commandBox.runCommand("uncomplete f1");
        assertResultMessage(String.format(UncompleteCommand.MESSAGE_UNCOMPLETED_TASK));
        
        // uncomplete floating task
        commandBox.runCommand("complete f1");
        commandBox.runCommand("uncomplete f1");
        assertResultMessage(String.format(UncompleteCommand.MESSAGE_UNCOMPLETED_TASK_SUCCESS));
        
        // uncomplete error command
        commandBox.runCommand("uncomplete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("uncomplete asdf");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteCommand.MESSAGE_USAGE));

        // uncomplete with an invalid index
        commandBox.runCommand("uncomplete f200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

    @Test
    public void uncompleteDeadline() {
        
        // cannot uncomplete a uncompleted deadline
        commandBox.runCommand("uncomplete d1");
        assertResultMessage(String.format(UncompleteCommand.MESSAGE_UNCOMPLETED_DEADLINE));

        // uncomplete deadline
        commandBox.runCommand("complete d1");
        commandBox.runCommand("uncomplete d1");
        assertResultMessage(String.format(UncompleteCommand.MESSAGE_UNCOMPLETED_DEADLINE_SUCCESS));

        // uncomplete with an invalid index
        commandBox.runCommand("uncomplete d200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX));
    }

}