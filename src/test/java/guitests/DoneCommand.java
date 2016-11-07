package guitests;

import org.junit.Test;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.logic.commands.MarkDoneCommand;
import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import static seedu.whatnow.logic.commands.MarkDoneCommand.MESSAGE_MARK_TASK_SUCCESS;

import java.util.Arrays;
//@@author A0141021H
public class DoneCommand extends WhatNowGuiTest {
    
    private static int MINUS1 = -1;

    @Test
    public void done() {

        //done the first in the list
        TestTask[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        Arrays.sort(currentList);
        assertDoneSuccess(targetIndex, currentList);

        //done the last in the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length;
        assertDoneSuccess(targetIndex, currentList);

        //done from the middle of the list
        currentList = TestUtil.removeTaskFromList(currentList, targetIndex);
        targetIndex = currentList.length/2;
        assertDoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("done " + "schedule " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");
        
        //invalid index
        commandBox.runCommand("done " + "schedule " + MINUS1);
        assertResultMessage(String.format( Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertDoneSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToMarkDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("done " + "schedule " + targetIndexOneIndexed);
        
        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMarkDone));
    }

}
