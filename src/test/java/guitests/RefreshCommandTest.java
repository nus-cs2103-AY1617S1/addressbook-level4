package guitests;

import org.junit.Test;

import seedu.toDoList.commons.core.Messages;
import seedu.toDoList.logic.commands.RefreshCommand;
import seedu.toDoList.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0142325R

/**
 * test for refresh command on gui
 * 
 * @author LiXiaowei
 *
 */
public class RefreshCommandTest extends TaskManagerGuiTest {

    // -------------------------------------valid cases----------------------------------------

    // test for valid cases
    
    /*
     * Equivalence partitions for itemType:
     *  - refresh out-dated recurring deadlineTask
     *  - refresh out-dated recurring event
     *  
     *  Equivalence partitions for recurring frequency:
     *  - monthly
     *  - weekly
     *  - daily
     */
    
    @Test
    public void refreshAllTasksAndEvents_successful() {

        TestTask[] currentList = td.getTypicalTasks();
        // refresh all non-recurring tasks
        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));

    }

    // refresh all tasks including one recurring event (recurring weekly)
    @Test
    public void refresh_RecurringDeadlineTaskWeekly_successful() {
        TestTask taskToAdd = td.lecture;
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel, td.lectureVerifier);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));
    }

    // refresh all tasks including one recurring deadlineTask (recurring daily)
    @Test
    public void refresh_RecurringEventDaily_successful() {
        TestTask taskToAdd = td.swimming;
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel, td.swimmingVerifier);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));
    }

    // refresh all tasks including one recurring deadlineTask (recurring monthly)
    @Test
    public void refresh_RecurringDeadlineTaskMonthly_successful() {
        TestTask taskToAdd = td.teaching;
        commandBox.runCommand(taskToAdd.getAddCommand());

        assertRefreshResult("refresh", td.friend, td.friendEvent, td.lunch, td.book, td.work, td.movie, td.meeting,
                td.travel, td.teachingVerifier);
        assertResultMessage(String.format(RefreshCommand.MESSAGE_SUCCESS));
    }

    // -------------------------------invalid cases----------------------------------

    // test for invalid command

    @Test
    public void refreshInvalidCommand_fail() {
        commandBox.runCommand("refreshes");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    private void assertRefreshResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

}
