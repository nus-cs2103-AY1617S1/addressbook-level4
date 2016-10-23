package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.task.logic.commands.AddTaskCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.taskcommons.core.Messages;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.arts;
        currentList = TestUtil.addTasksToListAtIndex(currentList, 0,taskToAdd);
        assertAddSuccess(taskToAdd, currentList);
        

        //add another task
        taskToAdd = td.socSciences;
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertAddSuccess(taskToAdd, currentList);
        

        //add duplicate task
        commandBox.runCommand(td.arts.getFullAddCommand());
        assertResultMessage(AddTaskCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getFullAddCommand());
        
        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd.getTask().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        assertTrue(taskListPanel.isListMatching(currentList));
    }

}
