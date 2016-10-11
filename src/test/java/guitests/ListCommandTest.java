package guitests;

import seedu.taskman.commons.core.Messages;
import seedu.taskman.model.event.Activity;
import seedu.taskman.model.event.Task;
import seedu.taskman.testutil.TestTask;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListCommandTest extends TaskManGuiTest {

    @Test
    public void list_nonEmptyList() {
        assertListResult("list Mark"); //no results
        assertListResult("list Project", td.taskCS2103T, td.taskCS3244); //multiple results

        //list after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list Project",td.taskCS3244);
    }

    @Test
    public void list_emptyList(){
        commandBox.runCommand("clear");
        assertListResult("list IS1103"); //no results
    }

    @Test
    public void list_invalidCommand_fail() {
        commandBox.runCommand("listBLAH");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        Activity[] expectedActivities = new Activity[expectedHits.length];
        for(int i = 0; i < expectedHits.length; i++){
            expectedActivities[i] = new Activity(new Task(expectedHits[i]));
        }
        assertListSize(expectedActivities.length);
        assertResultMessage(expectedActivities.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedActivities));
    }
}
