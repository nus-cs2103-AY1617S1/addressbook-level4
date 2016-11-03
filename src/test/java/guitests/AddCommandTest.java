package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.flexitrack.commons.core.Messages;
import seedu.flexitrack.commons.exceptions.IllegalValueException;
import seedu.flexitrack.logic.commands.AddCommand;
import seedu.flexitrack.testutil.TaskBuilder;
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
    public void invalidAddCommand() {
        commandBox.runCommand("adds cs tutorial");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    // @@ author
    @Test
    public void assertAddRecursiveEventSuccess() throws IllegalValueException {
        commandBox.runCommand("add Attend PC1222 lecture fr/3 ty/week from/Fri 3pm to/Fri 5pm");

        for (int i = 0; i < 3; i++) {
            TestTask recursiveEvent = new TaskBuilder().withName("Attend PC1222 lecture")
                    .withStartTime("Nov " + (4 + (i * 7)) + " 2016 15:00")
                    .withEndTime("Nov " + (4 + (i * 7)) + " 2016 17:00").withDueDate("Feb 29 2000 00:00").build();

            currentList = TestUtil.addTasksToList(currentList, recursiveEvent);

        }
        assertTrue(taskListPanel.isListMatching(0, currentList));
    }

    @Test
    public void assertAddRecursiveTaskSuccess() throws IllegalValueException {
        commandBox.runCommand("add Submit PC1222 Lab Assignment fr/3 ty/week by/Nov 1 2016 17:00");

        for (int i = 0; i < 3; i++) {
            TestTask recursiveTask = new TaskBuilder().withName("Submit PC1222 Lab Assignment")
                    .withStartTime("Feb 29 2000 00:00").withEndTime("Feb 29 2000 00:00")
                    .withDueDate("Nov " + (1 + (i * 7)) + " 2016 17:00").build();

            currentList = TestUtil.addTasksToList(currentList, recursiveTask);

        }
        assertTrue(taskListPanel.isListMatching(0, currentList));
    }

    // @@author A0127696R
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
