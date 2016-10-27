package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class UndoCommandTest extends FlexiTrackGuiTest {

    @Test
    public void list() {
        TestTask[] currentList = td.getTypicalSortedTasks();

        
        // undo unmark command 
        commandBox.runCommand("mark 2");
        commandBox.runCommand("unmark 2");
        commandBox.runCommand("undo");
        commandBox.runCommand("unmark 8");
        assertUndoSuccess();
        
       // undo add command 
        commandBox.runCommand("add a task");
        commandBox.runCommand("undo");
        assertUndoSuccess();

        // undo delete command 
        commandBox.runCommand("delete 4");
        commandBox.runCommand("undo");
        assertUndoSuccess();
        
        // undo mark command 
        commandBox.runCommand("mark 3");
        commandBox.runCommand("undo");
        assertUndoSuccess();

        
        // undo edit command 
        commandBox.runCommand("edit 5 n/ play bridge with friends");
        commandBox.runCommand("undo");
        assertUndoSuccess();
        
        // undo clear command 
        commandBox.runCommand("clear");
        commandBox.runCommand("undo");
        assertUndoSuccess();
        
        // undo add command 
        commandBox.runCommand("list future");
        commandBox.runCommand("mark 2");
        commandBox.runCommand("list");
        commandBox.runCommand("undo");
        assertUndoSuccess();
        
        // undo add command 
        commandBox.runCommand("list past");
        commandBox.runCommand("mark 2");
        commandBox.runCommand("mark 1");
        commandBox.runCommand("undo");
        commandBox.runCommand("list");
        commandBox.runCommand("undo");

        assertUndoSuccess();
    }

    private void assertUndoSuccess() {

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = td.getTypicalSortedTasks();
        assertTrue(taskListPanel.isListMatching(expectedList));
        
    }

}
