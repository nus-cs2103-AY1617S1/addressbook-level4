package guitests;

/**
 * @@author A0124333U
 */

import org.junit.Test;

import tars.commons.exceptions.IllegalValueException;
import tars.logic.commands.EditCommand;
import tars.model.task.Name;
import tars.model.task.Priority;
import tars.testutil.TestTask;
import tars.testutil.TestUtil;

import static org.junit.Assert.*;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class EditCommandTest extends TarsGuiTest {

    @Test
    public void edit() throws IllegalValueException {
        // Initialize Tars list
        TestTask[] currentList = td.getTypicalTasks();

        // Edit one task
        Name nameToEdit = new Name("Edited Task A");
        Priority priorityToEdit = new Priority("l");
        commandBox.runCommand("edit 1 /n Edited Task A /p l");
        int indexToEdit = 1;

        // confirm the list now contains the edited task
        TestTask[] expectedList = TestUtil.editTask(currentList, indexToEdit-1, nameToEdit, priorityToEdit);
        assertTrue(taskListPanel.isListMatching(expectedList));

        // invalid command
        commandBox.runCommand("edit 1 Johnny");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // invalid index
        commandBox.runCommand("edit " + (currentList.length + 1) + " /n invalidIndex");
        assertResultMessage("The task index provided is invalid");
    }

}
