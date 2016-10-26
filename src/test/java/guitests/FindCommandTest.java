package guitests;

import org.junit.Test;

import seedu.malitio.testutil.TestDeadline;
import seedu.malitio.testutil.TestEvent;
import seedu.malitio.testutil.TestFloatingTask;
import seedu.malitio.commons.core.Messages;
import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.logic.commands.FindCommand;

import static org.junit.Assert.assertTrue;
import static seedu.malitio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author a0126633j
public class FindCommandTest extends MalitioGuiTest {

    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {

        assertFindEventResult("find with", td.event1, td.event2); //multiple results
        assertFindEventResult("find hello"); //no result
        
        //find after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete f1");
        assertFindFloatingTaskResult("find bring",td.floatingTask2);
    }

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertFindFloatingTaskResult("find eat"); //no results
        assertFindDeadlineResult("find eat");
        assertFindEventResult("find eat");
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finddonothing");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
  
    @Test
    public void find_specificTasks() throws IllegalArgumentException, IllegalValueException {  
     assertFindDeadlineResult("find d SOME", td.deadline2);
     assertFindFloatingTaskResult("find f tell", td.floatingTask3);
     
     commandBox.runCommand("find e");
     assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
             FindCommand.MESSAGE_USAGE)); // recognise as finding in event but no keywords
    }
    
    private void assertFindFloatingTaskResult(String command, TestFloatingTask... expectedHits ) {
        commandBox.runCommand(command);
        assertFloatingTaskListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(floatingTaskListPanel.isListMatching(expectedHits));
    }
    private void assertFindDeadlineResult(String command, TestDeadline... expectedHits ) {
        commandBox.runCommand(command);
        assertDeadlineListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(deadlineListPanel.isListMatching(expectedHits));
    }
    private void assertFindEventResult(String command, TestEvent... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertEventListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks found!");
        
        assertTrue(eventListPanel.isListMatching(expectedHits));
    }
}
