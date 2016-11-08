package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
//@@author A0146749N
public class ClearCommandTest extends DailyPlannerGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.learnPython.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.learnPython));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Daily Planner has been cleared!");
    }
}
