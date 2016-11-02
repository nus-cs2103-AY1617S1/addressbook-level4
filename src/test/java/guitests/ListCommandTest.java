package guitests;

import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.IncorrectCommand;
import seedu.ggist.logic.commands.ListCommand;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;
import static seedu.ggist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0138411N
public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void list_nonEmptyList() throws IllegalArgumentException, IllegalValueException {
        
        //list low priority task
        commandBox.runCommand("list low");
        assertListResult("list low",TypicalTestTasks.deadline);
        
        //list medium priority task
        commandBox.runCommand("list med");
        assertListResult("list med",TypicalTestTasks.event);
        
        //list high priority task
        commandBox.runCommand("list high");
        assertListResult("list high",TypicalTestTasks.dance);
        
        //list done and priority after done one result
        commandBox.runCommand("list all");
        commandBox.runCommand("done 1");
        assertListResult("list done", TypicalTestTasks.deadline);
        assertListResult("list low");
        assertListResult("list all", TypicalTestTasks.deadline, TypicalTestTasks.event, TypicalTestTasks.dance); //multiple results
        
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
    public void list_emptyList() throws IllegalArgumentException, IllegalValueException{
        commandBox.runCommand("clear");
        assertListResult("list all"); //no results
    }

    @Test
    public void list_invalidArguments_fail() {
        commandBox.runCommand("list #$%^&HGJHG");
        assertResultMessage(ListCommand.MESSAGE_USAGE);
    }

    private void assertListResult(String command, TestTask... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
    }
}