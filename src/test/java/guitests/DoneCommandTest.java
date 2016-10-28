package guitests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.testutil.TestTask;

import static org.junit.Assert.assertTrue;

//@@author A0146123R
public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void done() {
        TestTask[] currentList = td.getTypicalTasks();
        
        // Mark task as done by index
        currentList[0].markAsDone();
        assertDoneSuccess("done 1", currentList);
        
        // Mark task as done by name
        currentList[4].markAsDone();
        assertDoneSuccess("done Work", currentList);
        
        // Mark task as done by name with multiple satisfied
        currentList[2].markAsDone();
        commandBox.runCommand("done friends");
        assertResultMessage(DoneCommand.MULTIPLE_TASK_SATISFY_KEYWORD);
        assertDoneSuccess("done 3", currentList);
        
        // Invalid index
        commandBox.runCommand("done 12");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        // Invalid name
        commandBox.runCommand("done dinner");
        assertResultMessage(DoneCommand.TASK_NOT_FOUND);
    }

    private void assertDoneSuccess(String command, TestTask... currentList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
