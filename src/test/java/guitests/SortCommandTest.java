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

    private void assertSortOrderCorrect(String sortParameter) {
        int size = td.getTypicalTasks().length;

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
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Deadline"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getDeadline()
                        .compareTo(taskListPanel.getTask(i + 1).getDeadline()) >= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "s":
        case "starttime":
        case "start":
            // start time
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Start Time"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStartTime()
                        .compareTo(taskListPanel.getTask(i + 1).getStartTime()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "e":
        case "endtime":
        case "end":
            // end time
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "End Time"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getEndTime()
                        .compareTo(taskListPanel.getTask(i + 1).getEndTime()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "c":
        case "completed":
        case "done":
            // done status
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Completed"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStatus()
                        .getDoneStatus() == (taskListPanel.getTask(i + 1).getStatus().getDoneStatus())
                        || (!taskListPanel.getTask(i).getStatus().getDoneStatus()
                                && taskListPanel.getTask(i + 1).getStatus().getDoneStatus()));
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "f":
        case "favorite":
        case "favourite": // Because British
        case "fav":
            // favorite status
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Favorite"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStatus()
                        .getFavoriteStatus() == (taskListPanel.getTask(i + 1).getStatus().getFavoriteStatus())
                        || (taskListPanel.getTask(i).getStatus().getFavoriteStatus()
                                && !taskListPanel.getTask(i + 1).getStatus().getFavoriteStatus()));
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "o":
        case "overdue":
        case "over":
            // overdue status
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Overdue"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStatus()
                        .getOverdueStatus() == (taskListPanel.getTask(i + 1).getStatus().getOverdueStatus())
                        || (taskListPanel.getTask(i).getStatus().getOverdueStatus()
                                && !taskListPanel.getTask(i + 1).getStatus().getOverdueStatus()));
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
            break;
        case "n":
        case "name":
        case "title":
            // name
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Name"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getName().compareTo(taskListPanel.getTask(i + 1).getName()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
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

    private void assertInvalidParameterRejected(String sortParameter) {

        if (ThreadLocalRandom.current().nextBoolean()) {
            commandBox.runCommand("sort " + sortParameter);
        } else {
            commandBox.runCommand("s " + sortParameter);
        }
        assertResultMessage(String.format(SortCommand.MESSAGE_FAILURE, sortParameter.toLowerCase()));
    }

}
