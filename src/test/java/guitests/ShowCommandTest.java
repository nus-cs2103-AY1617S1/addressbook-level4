package guitests;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class ShowCommandTest extends TaskListGuiTest {

    @Test
    public void show_nonEmptyList() {
        assertShowResult("show all"); //no results
        assertShowResult("show all", TypicalTestTasks.task2, TypicalTestTasks.task4); //multiple results

        //show after deleting one result
        commandBox.runCommand("delete 1");
        assertShowResult("show all",TypicalTestTasks.task4);
    }

    @Test
    public void show_emptyList(){
        commandBox.runCommand("clear");
        assertShowResult("show all"); //no results
    }

    @Test
    public void show_invalidCommand_fail() {
        commandBox.runCommand("showall");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertShowResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " persons listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}