package guitests;

import org.junit.Test;

import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.commands.Command;
import seedu.oneline.model.task.Task;
import seedu.oneline.testutil.TestTask;
import seedu.oneline.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class FindCommandTest extends TaskBookGuiTest {

    @Test
    public void findCommand_nonEmptyList_correctResults() {
        assertFindResult("find NotPartOfName"); //no results
        assertFindResult("find Consolidate", TypicalTestTasks.todo2, TypicalTestTasks.float1); //multiple results

        //find after deleting one result
        try {
            int index = taskPane.indexOf(new Task(TypicalTestTasks.todo2));
            commandBox.runCommand("del " + index);
        } catch (IllegalValueException e) {
            assert false;
        }
        assertFindResult("find Consolidate", TypicalTestTasks.float1);
    }

    @Test
    public void findCommand_emptyList_correctResults(){
        commandBox.runCommand("clear");
        assertFindResult("find NotATask"); //no results
    }

    @Test
    public void findCommand_invalidCommand_unknownMessage() {
        commandBox.runCommand("findtask");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits ) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(Command.getMessageForTaskListShownSummary(expectedHits.length));
        assertTrue(taskPane.isListMatching(false, expectedHits));
    }
}
