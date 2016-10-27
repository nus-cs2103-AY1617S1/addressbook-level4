package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

//@@author A0127686R
public class ListCommandTest extends FlexiTrackGuiTest {

    @Test
    public void list() {
        TestTask[] currentList = td.getTypicalSortedTasks();

       // list all future tasks
        String listCommand = "list future";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);

        // list all past tasks
        listCommand = "list past";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);

        // list all tasks
        listCommand = "list";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);

        currentList = TestUtil.markTasksToList(currentList, 6);
        currentList = TestUtil.markTasksToList(currentList, 4);
        currentList = TestUtil.markTasksToList(currentList, 3);
        currentList = TestUtil.markTasksToList(currentList, 1);
        
        commandBox.runCommand("mark 6");
        commandBox.runCommand("mark 4");
        commandBox.runCommand("mark 3");
        commandBox.runCommand("mark 1");
        
        // list all marked tasks
        listCommand = "list mark";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);
        
        // list all unmarked tasks
        listCommand = "list unmark";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);
        
        // list future tasks that are marked
        listCommand = "list future mark";
        assertFindSuccess(listCommand, currentList);
        currentList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);
        
    }

    private void assertFindSuccess(String listCommand, TestTask... currentList) {
        commandBox.runCommand(listCommand);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
