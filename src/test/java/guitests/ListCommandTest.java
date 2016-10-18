package guitests;

import org.junit.Test;

import harmony.mastermind.logic.commands.ListCommand;
import harmony.mastermind.testutil.TestTask;
import harmony.mastermind.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list() {

        //start with the Home tab
        TestTask[] currentList = td.getTypicalTasks();
        String targetTab = "Home";
        assertCurrentTab(targetTab);

        //list floating tasks
        targetTab = "Tasks";
        assertListSuccess(targetTab);

        //list events
        targetTab = "Events";
        assertListSuccess(targetTab);
        
        //list deadlines
        targetTab = "Deadlines";
        assertListSuccess(targetTab);

        //list archives
        targetTab = "Archives";
        assertListSuccess(targetTab);

        //list an invalid tab
        targetTab = "event";
        commandBox.runCommand("list " + targetTab);
    }

    /**
     * Runs the delete command to delete the task at specified index and confirms the result is correct.
     * @param targetIndexOneIndexed e.g. to delete the first task in the list, 1 should be given as the target index.
     * @param currentList A copy of the current list of tasks (before deletion).
     */
    private void assertListSuccess(String tab) {
        commandBox.runCommand("list " + tab);

        //confirm the current view is in the correct tab
        assertCurrentTab(tab);
    }

}
