package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends FlexiTrackGuiTest {

    @Test
    public void add() {
        TestTask[] currentList = td.getTypicalTasks();

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

        commandBox.runCommand("mark 1");
        commandBox.runCommand("mark 4");
        commandBox.runCommand("mark 6");
        commandBox.runCommand("mark 8");

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

        // confirm the new card contains the right data
//        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskList.getName().fullName);
//        assertMatching(taskList, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.listTasksAccordingToCommand(currentList, listCommand);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
