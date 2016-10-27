package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.flexitrack.testutil.TypicalTestTasks;

public class ClearCommandTest extends FlexiTrackGuiTest {

    @Test
    public void clear() {

        // verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalSortedTasks()));
        assertClearCommandSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.basketball.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.basketball));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("FlexiTrack has been cleared!");
    }
}
