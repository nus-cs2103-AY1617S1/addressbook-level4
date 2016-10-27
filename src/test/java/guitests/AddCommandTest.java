package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.model.task.Task.MESSAGE_DATETIME_CONSTRAINTS;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

//@@author A0153467Y
public class AddCommandTest extends TaskManagerGuiTest {
    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //add another task
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.cs2103);
       
        //add a task which has endTime < openTime 
        commandBox.runCommand("add testEvent s/tomorrow c/today");
        assertResultMessage(MESSAGE_DATETIME_CONSTRAINTS);
        
        //add test with only name
    }
    
    @Test
    public void add_same_task_name() {
        //add a task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));

        
        //add task with same task name as previous task but different openTime and endTime
        taskToAdd = td.same;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
    }
        
    @Test
    public void invalid_add() {
        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
        
        //invalid command format
        commandBox.runCommand("add");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    
    @Test
    public void add_recurring_task() {
        TestTask taskToAdd = td.recur;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recurring task number exceeds the maximum
        commandBox.runCommand("add testRecurring r/21");
        assertResultMessage(AddCommand.MESSAGE_WRONG_NUMBER_OF_RECURRENCE);
        
        // recurring number of task is negative
        commandBox.runCommand("add testRecurring r/-1");
        assertResultMessage(AddCommand.MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE);
        
        //invalid recurring argument with alphanumeric is not allowed
        commandBox.runCommand("add testRecurring r/abc");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //missing recurring argument 
        commandBox.runCommand("add testRecurring r/");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
      
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }

}
