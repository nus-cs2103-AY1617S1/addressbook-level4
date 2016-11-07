package guitests;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.TaskManager;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

//@@author A0093960X
public class ClearCommandGuiTest extends DearJimGuiTest {

    @Override
    protected TaskManager getInitialData() {
        TaskManager ab = TestUtil.generateEmptyTaskManager();
        TypicalTestTasks.loadTaskManagerUndoneListWithSampleData(ab);
        TypicalTestTasks.loadTaskManagerDoneListWithSampleDate(ab);
        return ab;
    }

    /**
     * Runs the clear command on the undone task list without any arguments.
     */
    @Test
    public void clearCommand_clearUndoneListNormalCommand_listCleared() {
        // verify a non-empty list can be cleared
        assertTrue(personListPanel.isListMatching(td.getTypicalUndoneTasks()));
        assertListSize(7);
        assertUndoneListClearCommandSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertTrue(personListPanel.isListMatching(td.hoon));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertUndoneListClearCommandSuccess();
    }

    /**
     * Runs the clear command on the done task list without any arguments.
     */
    @Test
    public void clearCommand_clearDoneListNormalCommand_listCleared() {

        // verify a non-empty list can be cleared
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(td.getTypicalDoneTasks()));
        assertListSize(7);
        assertDoneListClearCommandSuccess();

        // verify other commands can work after a clear command
        commandBox.runCommand("list");
        commandBox.runCommand("done 1");
        commandBox.runCommand("list done");
        assertTrue(personListPanel.isListMatching(td.alice));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        // verify clear command works when the list is empty
        assertDoneListClearCommandSuccess();
    }

    /**
     * Runs the clear command on both undone task list and done task list with
     * arguments and asserts that list is cleared in both cases.
     */
    @Test
    public void clearCommand_clearAnyListWithArgs_listCleared() {

        commandBox.runCommand("clear    1fasfs#!@32312");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS_UNDONE_LIST);

        commandBox.runCommand("list done");
        commandBox.runCommand("clear    1asdf123@#213");
        assertListSize(0);
        assertResultMessage(ClearCommand.MESSAGE_SUCCESS_DONE_LIST);

    }

    /**
     * Runs the clear command and asserts that the displayed list is empty, and
     * the message is a successful undone list clear.
     */
    private void assertUndoneListClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager undone list has been cleared!");
    }

    /**
     * Runs the clear command and asserts that the displayed list is empty, and
     * the message is a successful done list clear.
     */
    private void assertDoneListClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager done list has been cleared!");
    }
}
