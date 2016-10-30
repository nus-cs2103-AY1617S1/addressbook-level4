package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;
import seedu.flexitrack.testutil.TypicalTestTasks;

//@@author A0127686R
public class AddCommandTest extends FlexiTrackGuiTest {

    TestTask[] currentList = td.getTypicalSortedTasks();
    TestTask taskToAdd;
    
    @Test
    public void addAnEvent() {
        TestTask[] currentList = td.getTypicalSortedTasks();
        taskToAdd = TypicalTestTasks.basketball;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    @Test
    public void addADeadLineTask() {
        taskToAdd = TypicalTestTasks.lecture;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
    
    @Test
    public void addAFloatingTask() {
        taskToAdd = TypicalTestTasks.job;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

    }
    @Test
    public void addADuplicateTask() {
        commandBox.runCommand(TypicalTestTasks.soccer.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void addToAnEmptyList() {
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.homework1);

    }
    
    @Test
    public void invalidAddCommand () {
        commandBox.runCommand("adds cs tutorial");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        // confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getName().toString());
        assertMatching(taskToAdd, addedCard);

        // confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
