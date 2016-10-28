package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.testutil.TestTask;
import seedu.flexitrack.testutil.TestUtil;
import seedu.flexitrack.testutil.TypicalTestTasks;

//@@author A0127686R
public class AddCommandTest extends FlexiTrackGuiTest {

    @Test
    public void add() {
        // add an event
        TestTask[] currentList = td.getTypicalSortedTasks();
        TestTask taskToAdd = TypicalTestTasks.basketball;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add a deadline task
        taskToAdd = TypicalTestTasks.lecture;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add a floating task
        taskToAdd = TypicalTestTasks.job;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        // add duplicate task
        commandBox.runCommand(TypicalTestTasks.basketball.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        // add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.homework1);

        // invalid command
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
