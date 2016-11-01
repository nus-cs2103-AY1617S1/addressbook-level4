package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

//@@author A0138411N
public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        
        //list done after done one result
        commandBox.runCommand("done 1");
        assertListResult("list done", TypicalTestTasks.floating);
        assertListResult("list all", TypicalTestTasks.floating, TypicalTestTasks.event, TypicalTestTasks.dance); //multiple results
        
        //list all after deleting one result
        commandBox.runCommand("delete 1");
        assertListResult("list all",TypicalTestTasks.event, TypicalTestTasks.dance);
        
        //list date after editing
        commandBox.runCommand("edit 1 start date 1 Jan 2017");
        assertListResult("list 1 Jan 17", TypicalTestTasks.event);
        
        //list all undone
        commandBox.runCommand("list");
        assertListResult("list", TypicalTestTasks.event, TypicalTestTasks.dance);
        
    }
    
    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException{
        commandBox.runCommand("clear");
        assertListResult("list all"); //no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("find milk");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertListResult(String command, TestTask... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
    }
}