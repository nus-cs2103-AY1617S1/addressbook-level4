package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.RsvTaskCardHandle;
import tars.commons.core.Messages;
import tars.testutil.TestRsvTask;
import tars.testutil.TestUtil;

public class RsvCommandTest extends TarsGuiTest {

    @Test
    public void rsv() {
        // reserve one task
        TestRsvTask[] currentList = td.getTypicalRsvTasks();
        TestRsvTask taskToRsv = td.rsvTaskC;
        assertRsvSuccess(taskToRsv, currentList);
        currentList = TestUtil.addRsvTasksToList(currentList, taskToRsv);

        // reserve another task
        taskToRsv = td.rsvTaskD;
        assertRsvSuccess(taskToRsv, currentList);
        currentList = TestUtil.addRsvTasksToList(currentList, taskToRsv);

        // add duplicate task
        commandBox.runCommand(td.rsvTaskD.getRsvCommand());
        assertResultMessage(Messages.MESSAGE_DUPLICATE_TASK);
        assertTrue(rsvTaskListPanel.isListMatching(currentList));

        // delete a reserved task
        TestRsvTask rsvTaskToDel = td.rsvTaskC;
        commandBox.runCommand("rsv /del 3");
        TestRsvTask[] expectedList = TestUtil.delRsvTaskFromList(currentList, rsvTaskToDel);
        assertTrue(rsvTaskListPanel.isListMatching(expectedList));
        currentList = TestUtil.delRsvTaskFromList(currentList, taskToRsv);

        // add to empty list
        commandBox.runCommand("clear");
        assertRsvSuccess(td.rsvTaskA);

        // invalid command

        commandBox.runCommand("reserves Meeting");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    private void assertRsvSuccess(TestRsvTask taskToRsv, TestRsvTask... currentList) {
        commandBox.runCommand(taskToRsv.getRsvCommand());

        // confirm the new card contains the right data
        RsvTaskCardHandle addedCard = rsvTaskListPanel.navigateToRsvTask(taskToRsv.getName().taskName);
        assertMatching(taskToRsv, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestRsvTask[] expectedList = TestUtil.addRsvTasksToList(currentList, taskToRsv);
        assertTrue(rsvTaskListPanel.isListMatching(expectedList));
    }

}
