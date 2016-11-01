//@@author A0138420N
package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.ggist.logic.commands.UndoCommand.MESSAGE_UNDO_COMMAND_SUCCESS;

public class UndoCommandTest extends TaskManagerGuiTest {
    private String command;

    @Test
    public void undo() throws IllegalArgumentException, IllegalValueException {

        // undo previous add command
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add play soccer");
        command = "add";
        assertUndoSuccess(command);

        // undo previous delete command
        commandBox.runCommand("delete 1");
        command = "delete";
        assertUndoSuccess(command);

        // undo previous done command
        commandBox.runCommand("done 2");
        command = "done";
        assertUndoSuccess(command);

        // undo previous edit command which edits the task name
        commandBox.runCommand("edit 2 task drink milk");
        command = "edit";
        assertUndoSuccess(command);

        // undo previous edit command which edits the start date
        commandBox.runCommand("edit 1 start date tomorrow");
        command = "edit";
        assertUndoSuccess(command);

        // undo previous edit command which edits the end date
        commandBox.runCommand("edit 3 end date tomorrow");
        command = "edit";
        assertUndoSuccess(command);

        // undo previous edit command which edits the start time
        commandBox.runCommand("edit 1 start time 10 am");
        command = "edit";
        assertUndoSuccess(command);

        // undo previous edit command which edits the end time
        commandBox.runCommand("edit 1 end time 10 pm");
        command = "edit";
        assertUndoSuccess(command);

        // undo previous edit command which edit the priority
        commandBox.runCommand("edit 1 priority low");
        command = "edit";
        assertUndoSuccess(command);

        // unable to undo due to no previous command
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertResultMessage(Messages.MESSAGE_NO_PREVIOUS_COMMAND);

    }

    /**
     * Runs the undo command to undo the prevous command and confirms the result
     * is correct.
     * 
     * @param command
     *            e.g. to undo a command, add should be given as the command
     * @throws IllegalValueException
     * @throws IllegalArgumentException
     */
    private void assertUndoSuccess(String command) throws IllegalArgumentException, IllegalValueException {
        if (command.equals("add") || command.equals("delete") || command.equals("done")) {
            commandBox.runCommand("undo");
            assertListSize(3);
            // confirm the previous add or delete command has been undone
            // assertListSize(int size);
        }

        if (command.equals("edit")) {
            commandBox.runCommand("undo");
            // confirm the previous edit command has been undone
        }

        assertResultMessage("Undo previous command: " + command);

        // confirm the result message is correct
        // assertResultMessage("Undo previous command: " + command));
    }

}
// @@author
