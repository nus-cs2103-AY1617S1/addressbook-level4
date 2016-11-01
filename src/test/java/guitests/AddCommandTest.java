//@@author A0147994J
package guitests;

import guitests.guihandles.TaskCardHandle;
import org.junit.Test;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.ArrayUtils;

import seedu.ggist.commons.core.Messages;
import seedu.ggist.commons.exceptions.IllegalValueException;
import seedu.ggist.logic.commands.AddCommand;
import seedu.ggist.testutil.TestTask;
import seedu.ggist.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends TaskManagerGuiTest {
    
    //@@author A0138411N
    @Test
    public void add() throws IllegalArgumentException, IllegalValueException {
        //add one task
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("list all");
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
        
        //add event task
        TestTask eventTaskToAdd = td.lunch;
        assertAddSuccess(eventTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventTaskToAdd);
        
        //add deadline task
        TestTask deadlineTaskToAdd = td.report;
        assertAddSuccess(deadlineTaskToAdd, currentList);
        currentList = TestUtil.addTasksToList(currentList, deadlineTaskToAdd); 
        
        //add deadline task with no time specified
        TestTask deadlineTaskToAddWithNoTime = td.reportWithNoTime;
        assertAddSuccess(deadlineTaskToAddWithNoTime, currentList);
        currentList = TestUtil.addTasksToList(currentList, deadlineTaskToAddWithNoTime);
        
        //add event task with no time specified
        TestTask eventTaskToAddWithNoTime = td.lunchWithNoTime;
        assertAddSuccess(eventTaskToAddWithNoTime, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventTaskToAddWithNoTime);
    
       
        //add event task with no date specified
        TestTask eventTaskToAddWithNoDate = td.lunchWithNoDate;
        assertAddSuccess(eventTaskToAddWithNoDate, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventTaskToAddWithNoDate);
 
        //add event task with no start date specified
        TestTask eventTaskToAddWithNoStartDate = td.dinnerWithNoStartDate;
        assertAddSuccess(eventTaskToAddWithNoStartDate, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventTaskToAddWithNoStartDate);
        
        //add event task with no end date specified
        TestTask eventTaskToAddWithNoEndDate = td.dinnerWithNoEndDate;
        assertAddSuccess(eventTaskToAddWithNoEndDate, currentList);
        currentList = TestUtil.addTasksToList(currentList, eventTaskToAddWithNoEndDate);

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

        //confirm the list now contains all previous persons plus the new task
        TestTask[] expectedList = TestUtil.addTasksToList(currentList, taskToAdd);
    }
}
