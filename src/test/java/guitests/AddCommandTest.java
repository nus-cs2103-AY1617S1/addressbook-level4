package guitests;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.SomedayTaskCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.task.TaskType;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        if (taskToAdd.getTaskType().value.equals(TaskType.Type.SOMEDAY)) {
        	SomedayTaskCardHandle addedCard = taskListPanel.navigateToSomedayTask(taskToAdd.getName().fullName);
        	assertSomedayTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.DEADLINE)) {
        	DeadlineTaskCardHandle addedCard = taskListPanel.navigateToDeadlineTask(taskToAdd.getName().fullName);
        	assertDeadlineTaskMatching(taskToAdd, addedCard);
        } else if (taskToAdd.getTaskType().value.equals(TaskType.Type.EVENT)) {
        	EventTaskCardHandle addedCard = taskListPanel.navigateToEventTask(taskToAdd.getName().fullName);
        	assertEventTaskMatching(taskToAdd, addedCard);
        } 

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
