//@@author A0144919W
package guitests;

import org.junit.Test;

import seedu.tasklist.commons.core.Messages;
import seedu.tasklist.commons.exceptions.IllegalValueException;
import seedu.tasklist.logic.commands.UpdateCommand;
import seedu.tasklist.model.task.EndTime;
import seedu.tasklist.model.task.Priority;
import seedu.tasklist.model.task.StartTime;
import seedu.tasklist.model.task.TaskDetails;
import seedu.tasklist.testutil.TestTask;
import seedu.tasklist.testutil.TypicalTestTasks;

public class UpdateCommandTest extends TaskListGuiTest {
    
    @Test
    public void update() throws IllegalValueException {
        TestTask[] currentList = td.getTypicalTasks();

        //updating a non-existing task
        commandBox.runCommand("update 20 Buy eggs");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        //updating all information for a task
        commandBox.runCommand("update 1 Attend yoga session from 2pm to 4pm p/high");
        TypicalTestTasks.task1.setTaskDetails(new TaskDetails("Attend yoga session"));
        TypicalTestTasks.task1.setStartTime(new StartTime("2pm"));
        TypicalTestTasks.task1.setEndTime(new EndTime("4pm"));
        TypicalTestTasks.task1.setPriority(new Priority("high"));      
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[0]));
        
        //updating task description for a task
        commandBox.runCommand("update 2 Attend yoga session");
        TypicalTestTasks.task2.setTaskDetails(new TaskDetails("Attend yoga session"));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[1]));

        //updating start time for a task
        commandBox.runCommand("update 7 at 10am");
        TypicalTestTasks.task7.setStartTime(new StartTime("10am"));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[6]));
        
        //updating end time for a task
        commandBox.runCommand("update 5 by 1pm");
        TypicalTestTasks.task5.setEndTime(new EndTime("1pm"));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[4]));

        //updating priority for a task, also updating an updated task again
        commandBox.runCommand("update 7 p/high");
        TypicalTestTasks.task7.setPriority(new Priority("high"));      
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[6]));
        
        //updating a floating task to a task with date and time
        commandBox.runCommand("update 3 at 5pm");
        TypicalTestTasks.task3.setStartTime(new StartTime("5pm"));
        assertResultMessage(String.format(UpdateCommand.MESSAGE_UPDATE_TASK_SUCCESS, currentList[2]));

    }
  
}