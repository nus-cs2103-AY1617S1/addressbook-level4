//@@author A0138420N
package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.ggist.logic.commands.RedoCommand.MESSAGE_REDO_COMMAND_SUCCESS;

public class RedoCommandTest extends TaskManagerGuiTest {
    private String command;

    @Test
    public void redo() throws IllegalArgumentException, IllegalValueException {

        // redo previous undo add command
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("add play soccer");
        command = "add";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo delete command
        commandBox.runCommand("delete 1");
        command = "delete";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo done command
        commandBox.runCommand("done 2");
        command = "done";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edits the task name
        commandBox.runCommand("edit 1 task drink milk");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edits the start date
        commandBox.runCommand("edit 1 start date today");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edits the end date
        commandBox.runCommand("edit 1 end date tomorrow");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edits the start time
        commandBox.runCommand("edit 1 start time 10 am");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edits the end time
        commandBox.runCommand("edit 1 end time 10 pm");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // redo previous undo edit command which edit the priority
        commandBox.runCommand("edit 1 priority low");
        command = "edit";
        commandBox.runCommand("undo");
        assertRedoSuccess(command);

        // unable to redo due to no previous undo command
        commandBox.runCommand("clear");
        commandBox.runCommand("redo");
        assertResultMessage(Messages.MESSAGE_NO_PREVIOUS_UNDO_COMMAND);

    }

    /**
     * Runs the redo command to redo the previous command and confirms the result
     * is correct.
     * 
     * @param command e.g. to redo a command, add should be given as the previous undo command
     * @throws IllegalValueException
     * @throws IllegalArgumentException
     */
    private void assertRedoSuccess(String command) throws IllegalArgumentException, IllegalValueException {
        if (command.equals("add")) {
            commandBox.runCommand("redo");
            assertListSize(4);
            // confirm the previous add command has been redone
            // assertListSize(int size);
        }

        if (command.equals("delete")) {
            commandBox.runCommand("redo");
            assertListSize(3);
            // confirm the previous delete command has been redone
            // assertListSize(int size);
        }
        
        if (command.equals("done")) {
            commandBox.runCommand("redo");
            assertListSize(2);
            // confirm the previous done command has been redone
            // assertListSize(int size);
        }
        
        if (command.equals("edit")) {
            commandBox.runCommand("redo");
            // confirm edit command has been redone
        }

        assertResultMessage("Redo previous undo command: " + command);

        // confirm the result message is correct
        // assertResultMessage("Redo previous undo command: " + command));
    }

}
// @@author

