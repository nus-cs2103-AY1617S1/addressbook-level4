package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.model.task.Task.MESSAGE_DATETIME_CONSTRAINTS;

import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.google.api.services.tasks.model.Task;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.model.task.DateTime;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;
import seedu.task.testutil.TypicalTestTasks;

public class AddCommandTest extends TaskManagerGuiTest {
    //@@author A0141052Y
    @Test
    public void add() {
        //add task that will appear on the front of the list
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.first;
        assertAddSuccess(1, taskToAdd, currentList);
        
        currentList = TestUtil.insertTaskToList(currentList, taskToAdd, 0);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //adds a task that will be added at the back
        taskToAdd = TypicalTestTasks.last;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //adds a task to the middle, before fiona
        taskToAdd = TypicalTestTasks.hoon;
        assertAddSuccess(5, taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //add to empty list
        commandBox.runCommand("clear");
       
        //add a task which has endTime < openTime 
        commandBox.runCommand("add testEvent starts tomorrow ends today");
        assertResultMessage(MESSAGE_DATETIME_CONSTRAINTS);
        
        //add test with only name
    }
    //@@author
    
    @Test
    public void add_same_task_name() {
        //add a task
        TestTask[] currentList = td.getTypicalTasks();
        TestTask taskToAdd = TypicalTestTasks.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));

        
        //add task with same task name as previous task but different openTime and endTime
        taskToAdd = TypicalTestTasks.same;
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
    
    //@@author A0153467Y
    @Test
    public void add_recurring_task() {
        TestTask[] currentList = td.getTypicalTasks();
        
        //recur a task zero times (i.e. no recurrence at all)
        TestTask taskToAdd = TypicalTestTasks.recur;
        currentList = assertAddRecurringSuccess(0, taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recur a task twenty times (maximum amount)
        taskToAdd = TypicalTestTasks.recur2;
        currentList = assertAddRecurringSuccess(20, taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recurring task number exceeds the maximum
        commandBox.runCommand("add testRecurring recurs 21");
        assertResultMessage(AddCommand.MESSAGE_WRONG_NUMBER_OF_RECURRENCE);
        
        // recurring number of task is negative
        commandBox.runCommand("add testRecurring recurs -1");
        assertResultMessage(AddCommand.MESSAGE_NEGATIVE_NUMBER_OF_RECURRENCE);
        
        //invalid recurring argument with alphanumeric is not allowed
        commandBox.runCommand("add testRecurring recurs abc");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        
        //missing recurring argument 
        commandBox.runCommand("add testRecurring recurs ");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    
    //@@author A0141052Y
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        assertAddSuccess(currentList.length + 1, taskToAdd, currentList);
    }
    /**
     * Asserts the success of the add command operation.
     * @param taskId The expected ID of the Task to be added (according to the GUI)
     * @param taskToAdd The expected Task to be added
     * @param currentList The current list of Tasks that are being displayed
     */
    private void assertAddSuccess(int taskId, TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskId - 1);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.insertTaskToList(currentList, taskToAdd, taskId - 1);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    private TestTask[] assertAddRecurringSuccess(int numTimes, TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand() + " recurs " + numTimes);
        
        TestTask[] expectedList = currentList.clone();
        
        //confirm that instances have same distance and the other properties are same
        for (int i = 0; i <= numTimes; i++) {
            TestTask recurringTask = new TestTask(taskToAdd); // insulate the passed TestTask from changes
            
            DateTime newOpenTime = DateTime.fromDateTimeOffset(taskToAdd.getOpenTime(), i * 7, ChronoUnit.DAYS);
            DateTime newCloseTime = DateTime.fromDateTimeOffset(taskToAdd.getCloseTime(), i * 7, ChronoUnit.DAYS);
            
            recurringTask.setOpenTime(newOpenTime);
            recurringTask.setCloseTime(newCloseTime);
            
            TaskCardHandle addedCard = taskListPanel.navigateToTask(recurringTask);
            assertMatching(recurringTask, addedCard);
            
            expectedList = TestUtil.addTasksToList(expectedList, recurringTask);
        }
        
        //assert that the listing is correct after checking individually
        assertTrue(taskListPanel.isListMatching(expectedList));
        
        return expectedList;
    }

}
