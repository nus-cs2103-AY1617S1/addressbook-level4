package guitests;

import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.logic.commands.CompleteCommand;
import seedu.malitio.logic.commands.EditCommand;
import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.testutil.TestUtil;
import seedu.malitio.ui.DeadlineListPanel;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//@@author A0122460W
public class CompleteCommandTest extends MalitioGuiTest {

    @Test
    public void completeFloatingtask() {

        // complete floating task
        TestFloatingTask[] currentList = td.getTypicalFloatingTasks();
        TestFloatingTask toComplete = td.floatingTask1;
        commandBox.runCommand("complete f1");
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_TASK_SUCCESS));
        
        // cannot complete a completed floating task
        commandBox.runCommand("complete f1");
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_TASK));
        
        // complete error command
        commandBox.runCommand("complete");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));
        
        commandBox.runCommand("complete asdf");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CompleteCommand.MESSAGE_USAGE));

        // complete with an invalid index
        commandBox.runCommand("complete f200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX));
    }

    @Test
    public void completeDeadline() {

        // complete deadline
        TestDeadline[] currentList = td.getTypicalDeadlines();
        TestDeadline toComplete = td.deadline1;
        commandBox.runCommand("complete d1");
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_DEADLINE_SUCCESS));
        
        // cannot complete a completed deadline
        commandBox.runCommand("complete d1");
        assertResultMessage(String.format(CompleteCommand.MESSAGE_COMPLETED_DEADLINE));

        // complete with an invalid index
        commandBox.runCommand("complete d200");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX));
    }

}