package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.agendum.commons.core.Messages;
import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.logic.commands.AddCommand;
import seedu.agendum.testutil.TestTask;
import seedu.agendum.testutil.TestUtil;
import seedu.agendum.testutil.TypicalTestTasks;

//@@author A0133367E
public class AddCommandTest extends ToDoListGuiTest {

    @Test
    public void add() throws IllegalValueException {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.getFloatingTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another deadline task
        taskToAdd = TypicalTestTasks.getDeadlineTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another event task
        taskToAdd = TypicalTestTasks.getEventTestTask();
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(TypicalTestTasks.getFloatingTestTask().getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertAllPanelsMatch(currentList);

        //add to empty list
        commandBox.runCommand("delete 1-10");
        assertAddSuccess(TypicalTestTasks.getFloatingTestTask());

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(String.format(Messages.MESSAGE_UNKNOWN_COMMAND_WITH_SUGGESTION, "add"));
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        if (taskToAdd.isCompleted()) {
            TaskCardHandle addedCard = completedTasksPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.isCompleted() && !taskToAdd.hasTime()) {
            TaskCardHandle addedCard = floatingTasksPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        } else if (!taskToAdd.isCompleted() && taskToAdd.hasTime()) {
            TaskCardHandle addedCard = upcomingTasksPanel.navigateToTask(taskToAdd.getName().fullName);
            assertMatching(taskToAdd, addedCard);
        }

        //confirm the list now contains all previous tasks plus the new task
        taskToAdd.setLastUpdatedTimeToNow();
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAllPanelsMatch(expectedList);
    }
}
