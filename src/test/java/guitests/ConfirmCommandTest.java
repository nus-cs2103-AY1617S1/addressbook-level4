package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import tars.testutil.TestRsvTask;
import tars.testutil.TestTask;
import tars.testutil.TestUtil;

public class ConfirmCommandTest extends TarsGuiTest {

    @Test
    public void confirm() {
        TestTask[] currentTaskList = td.getTypicalTasks();
        TestRsvTask[] currentRsvTaskList = td.getTypicalRsvTasks();
        TestRsvTask rsvTaskToConfirm = td.rsvTaskA;
        TestTask confirmedTask = td.cfmTaskA;
        commandBox.runCommand("confirm 1 2 /p h");
        TestTask[] expectedTaskList = TestUtil.addTasksToList(currentTaskList, confirmedTask);
        TestRsvTask[] expectedRsvTaskList = TestUtil.delRsvTaskFromList(currentRsvTaskList, rsvTaskToConfirm);

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(confirmedTask.getName().taskName);
        assertMatching(confirmedTask, addedCard);

        // confirm that the rsv task list does not contain the confirmed task,
        // and that the task list contains the confirmed task
        assertTrue(taskListPanel.isListMatching(expectedTaskList));
        assertTrue(rsvTaskListPanel.isListMatching(expectedRsvTaskList));

    }

}
