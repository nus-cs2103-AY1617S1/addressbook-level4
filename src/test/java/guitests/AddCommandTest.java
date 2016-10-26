package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;

import seedu.todo.commons.core.Messages;
import seedu.todo.logic.commands.AddCommand;
import seedu.todo.testutil.TestTask;
import seedu.todo.testutil.TestUtil;
import seedu.todo.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

//@@author A0093896H
public class AddCommandTest extends ToDoListGuiTest {
    
    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getEmptyTaskList();
        TestTask taskToAdd = TypicalTestTasks.buyMilk;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add another task
        taskToAdd = TypicalTestTasks.buyRice;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);

        //add duplicate task
        commandBox.runCommand(TypicalTestTasks.buyMilk.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(taskListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(TypicalTestTasks.buyGroceries);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }
//@@author
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedTask = taskListPanel.navigateToTask(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedTask);

        //confirm the list now contains all previous tasks plus the new tasks
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
}
