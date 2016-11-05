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
        assertSortOrderCorrect("deadline");
        assertSortOrderCorrect("startTime");
        assertSortOrderCorrect("eNdtime");
        assertSortOrderCorrect("cOMPLETED");
        assertSortOrderCorrect("DEFAULT");

        assertSortOrderCorrect("dead");
        assertSortOrderCorrect("start");
        assertSortOrderCorrect("end");
        assertSortOrderCorrect("done");
        assertSortOrderCorrect("name");

        assertSortOrderCorrect("");
        assertSortOrderCorrect("d");
        assertSortOrderCorrect("s");
        assertSortOrderCorrect("e");
        assertSortOrderCorrect("c");
        assertSortOrderCorrect("n");

        // Verify if taskmanager rejects invalid parameters
        assertInvalidParameterRejected("invalidParameter");
        assertInvalidParameterRejected("1");
        assertInvalidParameterRejected("X");
        assertInvalidParameterRejected("yz");
    }

    private void assertSortOrderCorrect(String parameter) {
        int size = td.getTypicalTasks().length;

        if (ThreadLocalRandom.current().nextBoolean()) {
            commandBox.runCommand("sort " + parameter);
        } else {
            commandBox.runCommand("s " + parameter);
        }

        parameter = parameter.toLowerCase();
        
        if (parameter.equals("d") || parameter.equals("deadline") || parameter.equals("dead")) {
            // deadline
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Deadline"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getDeadline()
                        .compareTo(taskListPanel.getTask(i + 1).getDeadline()) >= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
        } else if (parameter.equals("s") || parameter.equals("starttime") || parameter.equals("start")) {
            // start time
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Start Time"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStartTime()
                        .compareTo(taskListPanel.getTask(i + 1).getStartTime()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
        } else if (parameter.equals("e") || parameter.equals("endtime") || parameter.equals("end")) {
            // end time
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "End Time"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getEndTime()
                        .compareTo(taskListPanel.getTask(i + 1).getEndTime()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
        } else if (parameter.equals("c") || parameter.equals("completed") || parameter.equals("done")) {
            // done status
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Completed"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getStatus()
                        .getDoneStatus() == (taskListPanel.getTask(i + 1).getStatus().getDoneStatus())
                        || (taskListPanel.getTask(i).getStatus().getDoneStatus() == false
                                && taskListPanel.getTask(i + 1).getStatus().getDoneStatus() == true));
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
        } else if (parameter.equals("n") || parameter.equals("name")) {
            // name
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS, "Name"));
            for (int i = 0; i < size - 1; i++) {
                assertTrue(taskListPanel.getTask(i).getName().compareTo(taskListPanel.getTask(i + 1).getName()) <= 0);
                logger.info("Comparing '" + taskListPanel.getTask(i).getName().toString() + "' to '"
                        + taskListPanel.getTask(i + 1).getName().toString() + "'");
            }
        } else if (parameter.equals("default") || parameter.equals("")) {
            // default sorting
            assertResultMessage(String.format(SortCommand.MESSAGE_SUCCESS_DEFAULT));
            return;
        }

    }

    private void assertInvalidParameterRejected(String parameter) {

        if (ThreadLocalRandom.current().nextBoolean()) {
            commandBox.runCommand("sort " + parameter);
        } else {
            commandBox.runCommand("s " + parameter);
        }
        parameter = parameter.toLowerCase();
        assertResultMessage(String.format(SortCommand.MESSAGE_FAILURE, parameter));
    }

}
