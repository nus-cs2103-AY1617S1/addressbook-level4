package guitests;

import org.junit.Test;

import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;

import static harmony.mastermind.logic.commands.UnmarkCommand.MESSAGE_UNMARK_SUCCESS;
import static harmony.mastermind.logic.commands.UnmarkCommand.MESSAGE_UNMARK_FAILURE;
import static harmony.mastermind.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static org.junit.Assert.assertTrue;

public class UnmarkCommandTest extends TaskManagerGuiTest {
    //@@author A0124797R
    @Test
    public void unmark() {
        
        //Invalid Tab
        TestTask[] currentList = td.getTypicalArchivedTasks();
        commandBox.runCommand("unmark 1");
        assertResultMessage(MESSAGE_UNMARK_FAILURE);

        //unmark the first task in the archived list
        //ensure that is on correct tab
        commandBox.runCommand("list archives");
        
        int targetIndex = 2;
        assertUnmarkSuccess(targetIndex, currentList);
        
        //undo Unmark command
        commandBox.runCommand("undo");
        assertTrue(taskListPanel.isArchivedListMatching(currentList));
        
        commandBox.runCommand("redo");
        TestTask[] expectedList = TestUtil.removeTaskFromList(currentList, targetIndex);
        assertTrue(taskListPanel.isArchivedListMatching(expectedList));
        
        //invalid index
        commandBox.runCommand("unmark " + (currentList.length + 1));
        assertResultMessage(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //@@author A0124797R
    private void assertUnmarkSuccess(int targetIndexOneIndexed, final TestTask[] currentList) {
        TestTask taskToUnmark = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        TestTask[] expectedRemainder = TestUtil.removeTaskFromList(currentList, targetIndexOneIndexed);

        commandBox.runCommand("unmark " + targetIndexOneIndexed);
        
        //confirm the list now contains all previous tasks except the deleted task
        assertTrue(taskListPanel.isArchivedListMatching(expectedRemainder));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_UNMARK_SUCCESS, taskToUnmark));
    }

}

