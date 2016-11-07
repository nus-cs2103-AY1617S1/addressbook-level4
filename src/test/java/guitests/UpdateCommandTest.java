package guitests;

import org.junit.Test;
import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.testutil.TestUtil;
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.logic.commands.AddCommand;
import seedu.whatnow.logic.commands.UpdateCommand;

import static org.junit.Assert.assertTrue;
import static seedu.whatnow.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatnow.logic.commands.UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS;

public class UpdateCommandTest extends WhatNowGuiTest {

    @Test
    public void update() {
        //invalid command
        commandBox.runCommand("updates Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid update format
        commandBox.runCommand("update hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        commandBox.runCommand("update todo hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        commandBox.runCommand("update schedule 1 hello");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
    }
    
    /**
     * Runs the update command to update the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to update the first task in the list, 1 should be given as the target index.
     * @param task The updated task
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertUpdateSuccess(int targetIndexOneIndexed, TestTask task, TestTask[] currentList) {
        TestTask taskToUpdate = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] updatedTaskList = TestUtil.replaceTaskFromList(currentList, task, targetIndexOneIndexed);

        commandBox.runCommand("update todo " + targetIndexOneIndexed + " description " + task.getName());

        //confirm the list now contains all updated tasks
        assertTrue(taskListPanel.isListMatching(updatedTaskList));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UPDATE_TASK_SUCCESS, taskToUpdate));
    }
}
