//@@author A0127855W
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.flexitrack.logic.commands.RedoCommand;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;
import seedu.flexitrack.testutil.TypicalTestTasks;

public class RedoCommandTest extends FlexiTrackGuiTest {

    @Test
    public void redo() {
        TestTask[] expectedList = td.getTypicalSortedTasks();

        // redo fail
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_NOT_SUCCESS);
        
        // redo add command 
        TestTask taskToAdd = TypicalTestTasks.basketball;
        commandBox.runCommand(taskToAdd.getAddCommand());
        expectedList = TestUtil.addTasksToList(expectedList, taskToAdd);
        assertRedoSuccess(expectedList);
        
       // redo delete command 
        commandBox.runCommand("delete 6");
        expectedList = TestUtil.removeTasksFromList(expectedList, expectedList[5]);
        assertRedoSuccess(expectedList);

        // redo edit command
        commandBox.runCommand("edit 6 n/Name Edited");
        expectedList = TestUtil.editTasksToList(expectedList, 5, TypicalTestTasks.homework1EditName);
        assertRedoSuccess(expectedList);
    }

    private void assertRedoSuccess(TestTask[] expectedList){
        commandBox.runCommand("undo");
        commandBox.runCommand("redo");
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
   
}
