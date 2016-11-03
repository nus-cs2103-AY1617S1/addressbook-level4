package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.task.model.task.Task.MESSAGE_DATETIME_CONSTRAINTS;

import java.time.temporal.ChronoUnit;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;
import seedu.task.model.task.DateTime;
import seedu.task.testutil.TestTask;
import seedu.task.testutil.TestUtil;

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
        commandBox.runCommand("add testEvent starts tomorrow ends today");
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
    
    //@@author A0153467Y
    @Test
    public void add_recurring_task() {
        TestTask[] currentList = td.getTypicalTasks();
        
        //recur a task zero times (i.e. no recurrence at all)
        TestTask taskToAdd = td.recur;
        currentList = assertAddRecurringSuccess(0, taskToAdd, currentList);
        assertResultMessage(String.format(AddCommand.MESSAGE_SUCCESS, taskToAdd));
        
        //recur a task twenty times (maximum amount)
        taskToAdd = td.recur2;
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
    //@@author
      
    private void assertAddSuccess(TestTask taskToAdd, TestTask... currentList) {
        commandBox.runCommand(taskToAdd.getAddCommand());

        //confirm the new card contains the right data
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToAdd);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
        assertTrue(taskListPanel.isListMatching(expectedList));
    }
    
    //@@author A0141052Y
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
