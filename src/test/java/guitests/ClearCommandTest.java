package guitests;

import seedu.taskman.model.event.Activity;
import seedu.taskman.testutil.TestTask;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends TaskManGuiTest {

    //@Test
    public void clear() {

        //verify a non-empty list can be cleared
        TestTask[] currentList= td.getTypicalTasks();
        Activity[] expectedList = new Activity[currentList.length];
        for(int i = 0; i < expectedList.length; i++){
            expectedList[i] = new Activity(currentList[i]);
        }
        assertTrue(taskListPanel.isListMatching(expectedList));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.taskCS2102.getAddCommand());
        assertTrue(taskListPanel.isListMatching(new Activity(td.taskCS2102)));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task man has been cleared!");
    }
}
