package guitests;

import org.junit.Test;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.logic.commands.MarkDoneCommand;
import seedu.whatnow.logic.commands.MarkUndoneCommand;
import seedu.whatnow.testutil.TestTask;
import seedu.whatnow.testutil.TestUtil;

import static org.junit.Assert.assertTrue;
import java.util.Arrays;
//@@author A0141021H
public class UndoneCommand extends WhatNowGuiTest {

    private static int MINUS1 = -1;

    @Test
    public void undone() {
        TestTask[] currentList = td.getTypicalTasks(); 
        Arrays.sort(currentList);

        //undone the first in the list
        int targetIndex = 1;
        commandBox.runCommand("done schedule " + targetIndex);
        commandBox.runCommand("list done");
        assertUndoneSuccess(targetIndex, currentList);     

        //undone the last in the list
        targetIndex = currentList.length;
        commandBox.runCommand("list");
        commandBox.runCommand("done schedule " + targetIndex);
        commandBox.runCommand("list done");
        assertUndoneSuccess(targetIndex, currentList);

        //undone from the middle of the list
        targetIndex = currentList.length/2;
        commandBox.runCommand("list");
        commandBox.runCommand("done schedule " + targetIndex);
        commandBox.runCommand("list done");
        assertUndoneSuccess(targetIndex, currentList);

        //invalid index
        commandBox.runCommand("list");
        commandBox.runCommand("undone " + "schedule " + currentList.length + 1);
        assertResultMessage("The task index provided is invalid");

        //invalid index
        commandBox.runCommand("list");
        commandBox.runCommand("undone " + "schedule " + MINUS1);
        assertResultMessage(String.format( Messages.MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_MISSING_INDEX));
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertUndoneSuccess(int targetIndexOneIndexed, TestTask[] currentList) {
        TestTask taskToMarkUndone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        currentList = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);
        TestTask[] doneList = new TestTask[0];
        commandBox.runCommand("undone schedule 1");

        //confirm the result message is correct
        assertResultMessage(String.format(MarkUndoneCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMarkUndone));

        commandBox.runCommand("list done");

        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isListMatching(doneList));
    }

}
