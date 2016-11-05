package guitests;

import org.junit.Test;

import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.testutil.TestTask;
import seedu.agendum.testutil.TypicalTestTasks;

public class FindCommandTest extends ToDoListGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalValueException {
        assertFindResult("find Mark"); //no results
        assertFindResult("find Meier", TypicalTestTasks.BENSON, TypicalTestTasks.DANIEL); //multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Meier", TypicalTestTasks.DANIEL);
    }

    @Test
    public void find_emptyList(){
        commandBox.runCommand("delete 1-7");
        assertFindResult("find Jean"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findgeorge");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
    
    //@@author A0148031R
    @Test
    public void find_showMesssage() {
        commandBox.runCommand("find Meier");
        assertShowingMessage(Messages.MESSAGE_ESCAPE_HELP_WINDOW);
        assertFindResult("find Meier", TypicalTestTasks.BENSON, TypicalTestTasks.DANIEL); 
    }
    
    @Test
    public void find_showMessage_fail() {
        commandBox.runCommand("find2");
        assertShowingMessage(null);
    }
    
    @Test
    public void find_backToAllTasks_WithEscape() {
        assertFindResult("find Meier", TypicalTestTasks.BENSON, TypicalTestTasks.DANIEL);
        assertShowingMessage(Messages.MESSAGE_ESCAPE_HELP_WINDOW);
        mainGui.pressEscape();
        assertAllPanelsMatch(td.getTypicalTasks());
    }

    //@@author
    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, expectedHits.length));
        assertAllPanelsMatch(expectedHits);
    }
}
