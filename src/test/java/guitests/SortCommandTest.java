package guitests;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import org.junit.Test;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.ConfigUtil;
import seedu.task.logic.commands.SortCommand;

import static org.junit.Assert.assertTrue;

// @@author A0147944U
public class SortCommandTest extends TaskManagerGuiTest {

    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    @Test
    public void sort() {

        // Verify if tasks are sorted correctly
        commandBox.runCommand("add overduetask, by 3pm yesterday");
        commandBox.runCommand("add notoverduetask, by 3pm tomorrow");
        commandBox.runCommand("done 1");
        commandBox.runCommand("done 3");
        commandBox.runCommand("fav 2 ");
        commandBox.runCommand("fav 4 ");

        assertSortOrderCorrect("original");
        assertSortOrderCorrect("fav");

        assertSortOrderCorrect("deadline");
        assertSortOrderCorrect("startTime");
        assertSortOrderCorrect("eNdtime");
        assertSortOrderCorrect("cOMPLETED");
        assertSortOrderCorrect("favoUrite");
        assertSortOrderCorrect("OveRduE");
        assertSortOrderCorrect("name");
        assertSortOrderCorrect("DEFAULT");

        assertSortOrderCorrect("d");
        assertSortOrderCorrect("s");
        assertSortOrderCorrect("e");
        assertSortOrderCorrect("c");
        assertSortOrderCorrect("f");
        assertSortOrderCorrect("o");
        assertSortOrderCorrect("n");
        assertSortOrderCorrect("");

        assertSortOrderCorrect("dead");
        assertSortOrderCorrect("start");
        assertSortOrderCorrect("end");
        assertSortOrderCorrect("done");
        assertSortOrderCorrect("favourite");
        assertSortOrderCorrect("over");
        assertSortOrderCorrect("title");
        assertSortOrderCorrect("original");

        // Verify if taskmanager rejects invalid sortParameters
        assertInvalidParameterRejected("invalidParameter");
        assertInvalidParameterRejected("1");
        assertInvalidParameterRejected("Xyz");
    }

    /**
     * Runs sort command with given keyword and asserts list is correctly sorted
     * according to keyword
     */
    private void assertSortOrderCorrect(String sortParameter) {
        int size = td.getTypicalTasks().length;

        // enter sort command with its short form or in full at random
        if (ThreadLocalRandom.current().nextBoolean()) {
            commandBox.runCommand("sort " + sortParameter);
        } else {
            commandBox.runCommand("s " + sortParameter);
        }

        String sortParameterLowerCased = sortParameter.toLowerCase();

        switch (sortParameterLowerCased) {
        case "d":
        case "deadline":
        case "dead":
            // deadline
            checkListSortedByDeadline(size);
            break;
        case "s":
        case "starttime":
        case "start":
            // start time
            checkListSortedByStartTime(size);
            break;
        case "e":
        case "endtime":
        case "end":
            // end time
            checkListSortedByEndTime(size);
            break;
        case "c":
        case "completed":
        case "done":
            // done status
            checkListSortedByDoneStatus(size);
            break;
        case "f":
        case "favorite":
        case "favourite": // Because British
        case "fav":
            // favorite status
            checkListSortedByFavoriteStatus(size);
            break;
        case "o":
        case "overdue":
        case "over":
            // overdue status
            checkListSortedByOverdueStatus(size);
            break;
        case "n":
        case "name":
        case "title":
            // name
            checkListSortedByName(size);
            break;
        case "default":
        case "":
        case "standard":
        case "original":
            // default sorting
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS_DEFAULT));
            return;
        default:
            break;
        }

    }

    /**
     * Runs sort command with given invalid keyword and asserts the command
     * fails with the appropriate failure message
     */
    private void assertInvalidParameterRejected(String sortParameter) {

        if (ThreadLocalRandom.current().nextBoolean()) {
            commandBox.runCommand("sort " + sortParameter);
        } else {
            commandBox.runCommand("s " + sortParameter);
        }
        assertResultMessage(String.format(SortCommand.MESSAGE_FAILURE, sortParameter.toLowerCase()));
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to name
     */
    private void checkListSortedByName(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Name"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getName().compareTo(taskListPanel.getTask(i + 1).getName()) <= 0);
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to overdue status
     */
    private void checkListSortedByOverdueStatus(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Overdue"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getStatus()
                    .getOverdueStatus() == (taskListPanel.getTask(i + 1).getStatus().getOverdueStatus())
                    || (taskListPanel.getTask(i).getStatus().getOverdueStatus()
                            && !taskListPanel.getTask(i + 1).getStatus().getOverdueStatus()));
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to favorite status
     */
    private void checkListSortedByFavoriteStatus(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Favorite"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getStatus()
                    .getFavoriteStatus() == (taskListPanel.getTask(i + 1).getStatus().getFavoriteStatus())
                    || (taskListPanel.getTask(i).getStatus().getFavoriteStatus()
                            && !taskListPanel.getTask(i + 1).getStatus().getFavoriteStatus()));
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to done status
     */
    private void checkListSortedByDoneStatus(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Completed"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getStatus()
                    .getDoneStatus() == (taskListPanel.getTask(i + 1).getStatus().getDoneStatus())
                    || (!taskListPanel.getTask(i).getStatus().getDoneStatus()
                            && taskListPanel.getTask(i + 1).getStatus().getDoneStatus()));
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to end time
     */
    private void checkListSortedByEndTime(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "End Time"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getEndTime().compareTo(taskListPanel.getTask(i + 1).getEndTime()) <= 0);
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to start time
     */
    private void checkListSortedByStartTime(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Start Time"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(taskListPanel.getTask(i).getStartTime()
                    .compareTo(taskListPanel.getTask(i + 1).getStartTime()) <= 0);
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Asserts the message returned is as expected and checks if list is
     * correctly sorted to deadline
     */
    private void checkListSortedByDeadline(int size) {
        assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Deadline"));
        for (int i = 0; i < size - 1; i++) {
            assertTrue(
                    taskListPanel.getTask(i).getDeadline().compareTo(taskListPanel.getTask(i + 1).getDeadline()) >= 0);
            printTasksBeingComparedToLogger(i);
        }
    }

    /**
     * Post info on logger when each comparison is made to easily pinpoint what
     * is not working
     */
    private void printTasksBeingComparedToLogger(int i) {
        logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                + taskListPanel.getTask(i + 1).getName().toString() + "'");
    }

}
