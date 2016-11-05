package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskManagerGuiTest {

    @Test
    public void findNonEmptyList() {
        assertFindResult("find UnknownTask"); // no results
        assertFindResult("find the", TypicalTestTasks.taskA, TypicalTestTasks.taskF); // multiple results

        // find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find homework", TypicalTestTasks.taskD);
    }

    @Test
    public void findEmptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find weirdTask"); // no results
    }

    @Test
    public void findInvalidCommand_fail() {
        commandBox.runCommand("findtask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
