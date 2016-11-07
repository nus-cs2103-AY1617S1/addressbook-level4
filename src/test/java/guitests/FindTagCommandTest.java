package guitests;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class FindTagCommandTest extends TaskManagerGuiTest {

    @Test
    public void findTagNonEmptyList() {
        assertFindTagResult("find-tag notimportant");
        assertFindTagResult("find-tag friends", td.cs2103, td.carl);

        // Find a tag after deletion
        commandBox.runCommand("delete 1");
        assertFindTagResult("find-tag friends", td.carl);
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
