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

        assertFindResult("find with", td.event1, td.event2); //multiple results
        assertResultMessage("2 tasks found!");
        
        assertFindResult("find peN HOMEWORK", td.floatingTask2, td.deadline3, td.deadline5);
      //  assertFindResult("find peN HOMEWORK");
        assertResultMessage("3 tasks found!");
        
        assertFindResult("find 12-25", td.deadline4, td.event5); //find dates
       // assertFindResult("find 12-25");
        assertResultMessage("2 tasks found!");
        
        assertFindResult("find wedding"); //no result
        
        //find after deleting one result
        commandBox.runCommand("list");
        commandBox.runCommand("delete f1");
        assertFindResult("find bring",td.floatingTask2);
    }
       
    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand("clear");
        assertFindResult("find eat"); //no result
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("finddonothing");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
 
    @Test
    public void find_specificTasks() throws IllegalArgumentException, IllegalValueException {  
        assertFindResult("find e with", td.event1, td.event2); //multiple results
        assertResultMessage("2 tasks found!");
        
        assertFindResult("find d H", td.deadline1, td.deadline4, td.deadline5);
        assertResultMessage("3 tasks found!");
        
        assertFindResult("find f tell", td.floatingTask3);
        assertResultMessage("1 tasks found!");
        
     commandBox.runCommand("find e");
     assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
             FindCommand.MESSAGE_USAGE)); // recognise as finding in event but no keywords
    }
    
    /**
     * Overload functions to assert result in each floating task, deadline and event list is correct
     * @throws IllegalValueException 
     * @throws IllegalArgumentException 
     */
    
    private void assertFindResult(String command, Object... expectedHits ) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(command);
        
        switch (expectedHits.getClass().getSimpleName()) {
        case "TestFloatingTask":
        assertFloatingTaskListSize(expectedHits.length);
        assertTrue(floatingTaskListPanel.isListMatching((TestFloatingTask[]) expectedHits));
        break;
        case "TestDeadline":
            assertDeadlineListSize(expectedHits.length);
            assertTrue(deadlineListPanel.isListMatching((TestDeadline[]) expectedHits));
            break;
        case "TestEvent":
            assertEventListSize(expectedHits.length);
            assertTrue(eventListPanel.isListMatching((TestEvent[])expectedHits));
            break;
        }
    }
}
