//@@author A0147994J
package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.AddCommand;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {
/*
    @Test
    public void add() throws IllegalArgumentException, IllegalValueException {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask TaskToAdd = td.soccer;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, TaskToAdd);

        //add another task
        TaskToAdd = td.floating;
        assertAddSuccess(TaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, TaskToAdd);

        //add duplicate task
        commandBox.runCommand(td.dance.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.floating);

        //invalid command
        commandBox.runCommand("adds flying");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) throws IllegalArgumentException, IllegalValueException {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTaskName().taskName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
*/
}
