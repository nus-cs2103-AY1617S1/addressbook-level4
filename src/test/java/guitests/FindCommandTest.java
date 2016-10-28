package guitests;

import org.junit.Test;

import seedu.taskscheduler.commons.core.Messages;
import seedu.taskscheduler.logic.commands.ListCommand;
import seedu.taskscheduler.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0138696L

public class FindCommandTest extends TaskSchedulerGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Mark"); //no results
        assertFindResult("find project", td.benson, td.carl, td.george); //multiple results

        //undo list command
        commandBox.runCommand("list");
        assertResultMessage(ListCommand.MESSAGE_SUCCESS);
        assertFindResult("undo", td.benson, td.carl, td.george);
        
        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Groupwork", td.daniel);
        //undo find command
        assertFindResult("undo", td.carl, td.george);
    }
    
    @Test
    public void find_Date() {
        assertFindResult("find 14-Oct-2016", td.benson, td.carl);
    }
    
    @Test
    public void find_address() {
        assertFindResult("find hall", td.daniel, td.elle);
    }
    
    @Test
    public void find_completed() {
        commandBox.runCommand("mark 1");
        commandBox.runCommand("mark 4");
        assertFindResult("find Completed", td.alice, td.daniel);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("clear");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findsomething");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
