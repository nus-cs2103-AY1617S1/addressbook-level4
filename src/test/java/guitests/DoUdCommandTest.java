package guitests;

/**
 * @@author A0121533W
 */

import org.junit.Test;
import tars.commons.exceptions.IllegalValueException;
import tars.logic.commands.DoCommand;
import tars.logic.commands.UdCommand;
import tars.model.task.Status;
import tars.testutil.TestTask;
import tars.testutil.TestUtil;

import static org.junit.Assert.*;
import static tars.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;


public class DoUdCommandTest extends TarsGuiTest {

    @Test
    public void doTasks() throws IllegalValueException {
        // Initialize Tars list
        TestTask[] currentList = td.getTypicalTasks();

        // Mark tasks as done by indexes
        commandBox.runCommand("do 1 2 3");

        // Confirm the list now contains the specified tasks to be mark as undone
        Status done = new Status(true);
        int[] indexesToMarkDoneIndexes = {1, 2, 3};
        TestTask[] expectedDoneListIndexes = TestUtil.markTasks(currentList, indexesToMarkDoneIndexes, done);
        assertTrue(taskListPanel.isListMatching(expectedDoneListIndexes));

        // Mark tasks as done by range
        commandBox.runCommand("do 4..7");

        int[] indexesToMarkDoneRange = {1, 2, 3, 4, 5, 6, 7};
        TestTask[] expectedDoneListRange = TestUtil.markTasks(currentList, indexesToMarkDoneRange, done);
        assertTrue(taskListPanel.isListMatching(expectedDoneListRange));

        // Mark tasks as undone by indexes
        commandBox.runCommand("ud 1 2 3");

        // Confirm the list now contains the specified tasks to be mark as undone
        Status undone = new Status(false);
        int[] indexesToMarkUndoneIndexes = {1, 2, 3};
        TestTask[] expectedUndoneListIndexes = TestUtil.markTasks(currentList, indexesToMarkUndoneIndexes, undone);
        assertTrue(taskListPanel.isListMatching(expectedUndoneListIndexes));  

        // Mark tasks as undone by range
        commandBox.runCommand("ud 4..7");

        int[] indexesToMarkUndoneRange = {1, 2, 3, 4, 5, 6, 7};
        TestTask[] expectedUndoneListRange = TestUtil.markTasks(currentList, indexesToMarkUndoneRange, undone);
        assertTrue(taskListPanel.isListMatching(expectedUndoneListRange));

        // invalid do command
        commandBox.runCommand("do abc");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoCommand.MESSAGE_USAGE));

        // invalid do index
        commandBox.runCommand("do 8");
        assertResultMessage("The task index provided is invalid");

        // invalid do range
        commandBox.runCommand("do 3..2");
        assertResultMessage(String.format("Start index should be before end index."
                + "\n" + DoCommand.MESSAGE_USAGE));

        // invalid ud command
        commandBox.runCommand("ud abc");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UdCommand.MESSAGE_USAGE));

        // invalid ud index
        commandBox.runCommand("ud 8");
        assertResultMessage("The task index provided is invalid");

        // invalid do range
        commandBox.runCommand("ud 3..2");
        assertResultMessage(String.format("Start index should be before end index."
                + "\n" + UdCommand.MESSAGE_USAGE));
    }

}
