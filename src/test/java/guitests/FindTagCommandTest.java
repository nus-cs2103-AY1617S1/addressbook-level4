//@@author A0141052Y
package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindTagCommandTest extends TaskManagerGuiTest {

    @Test
    public void findTagNonEmptyList() {
        assertFindTagResult("find-tag notimportant");
        assertFindTagResult("find-tag friends", TypicalTestTasks.cs2103, TypicalTestTasks.carl);

        // Find a tag after deletion
        commandBox.runCommand("delete 1");
        assertFindTagResult("find-tag friends", TypicalTestTasks.carl);
    }

    @Test
    public void findTagEmptyList(){
        commandBox.runCommand("clear");
        assertFindTagResult("find-tag friend");
    }

    @Test
    public void findInvalidCommand() {
        commandBox.runCommand("find-tagworld");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindTagResult(String command, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
