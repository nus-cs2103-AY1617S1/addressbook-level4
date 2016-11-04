package guitests;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;


public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void clear() {
        // verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasksNotDone()));
        assertClearCommandSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand(TypicalTestTasks.someday1.getAddCommand());
        assertTrue(taskListPanel.isListMatching(TypicalTestTasks.someday1));
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertClearCommandSuccess();
        
        // verify the lists in the tab pane can be cleared
        // TODO
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertTodayListSize(0);
        assertTomorrowListSize(0);
        assertIn7DaysListSize(0);
        assertIn30DaysListSize(0);
        assertSomedayListSize(0);
        assertResultMessage("Task manager has been cleared!");
    }
}
