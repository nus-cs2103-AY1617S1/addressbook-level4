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

    //In the tests below, we assume event,floating task and deadline lists are identical, hence to save resources only work on them equally
    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalValueException {

        assertFindEventResult("find e with", td.event1, td.event2); //multiple results
        assertResultMessage("2 tasks found!");
        
        assertFindFloatingTaskResult("find peN HOMEWORK", td.floatingTask2);
        assertFindDeadlineResult("find peN HOMEWORK", td.deadline3, td.deadline5);
        assertResultMessage("3 tasks found!");
        
        assertFindDeadlineResult("find 12-25", td.deadline4); //find dates
        assertFindEventResult("find 12-25", td.event5);
        assertResultMessage("2 tasks found!");
        
        assertFindEventResult("find wedding"); //no result
        
        //find after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete f1");
        assertFindFloatingTaskResult("find bring",td.floatingTask2);
    }

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertFindEventResult("find eat"); //no result
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finddonothing");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
 
    @Test
    public void find_specificTasks() throws IllegalArgumentException, IllegalValueException {  
        assertFindEventResult("find e with", td.event1, td.event2); //multiple results
        assertResultMessage("2 tasks found!");
        
        assertFindDeadlineResult("find d H", td.deadline1, td.deadline4, td.deadline5);
        assertResultMessage("3 tasks found!");
        
        assertFindFloatingTaskResult("find f tell", td.floatingTask3);
        assertResultMessage("1 tasks found!");
        
     commandBox.runCommand("find e");
     assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
             FindCommand.MESSAGE_USAGE)); // recognise as finding in event but no keywords
    }
    
    /**
     * Overload functions to assert result in each floating task, deadline and event list is correct
     */
    private void assertFindFloatingTaskResult(String command, TestFloatingTask... expectedHits ) {
        commandBox.runCommand(command);
        assertFloatingTaskListSize(expectedHits.length);
        
        assertTrue(floatingTaskListPanel.isListMatching(expectedHits));
    }
    private void assertFindDeadlineResult(String command, TestDeadline... expectedHits ) {
        commandBox.runCommand(command);
        assertDeadlineListSize(expectedHits.length);
        
        assertTrue(deadlineListPanel.isListMatching(expectedHits));
    }
    private void assertFindEventResult(String command, TestEvent... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        assertEventListSize(expectedHits.length);
        
        assertTrue(eventListPanel.isListMatching(expectedHits));
    }
}
