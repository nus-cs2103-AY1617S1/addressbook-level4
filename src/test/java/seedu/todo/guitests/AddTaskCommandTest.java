package seedu.todo.guitests;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.guitests.guihandles.TaskListDateItemHandle;
import seedu.todo.guitests.guihandles.TaskListTaskItemHandle;
import seedu.todo.models.Task;

import static seedu.todo.testutil.AssertUtil.assertSameDate;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddTaskCommandTest extends GuiTest {

    @Test
    public void addTaskWithDeadline() {
        String command = "add task Buy milk by Oct 15 2016 2pm";
        Task task = new Task();
        task.setName("Buy milk");
        task.setCalendarDT(DateUtil.parseDateTime("2016-10-15 14:00:00"));
        assertAddSuccess(command, task);
    }

    @Test
    public void addFloatingTask() {
        String command = "add task Buy milk";
        Task task = new Task();
        task.setName("Buy milk");
        assertAddSuccess(command, task);
    }

    /**
     * Utility method for testing if task has been successfully added to the GUI.
     * This runs a command and checks if TaskList contains TaskListTaskItem that matches
     * the task that was just added. <br><br>
     * 
     * TODO: Extract out method in AddController that can return task from command,
     *       and possibly remove the need to have taskToAdd.
     */
    private void assertAddSuccess(String command, Task taskToAdd) {
        // Run the command in the console.
        console.runCommand(command);
        
        // Get the task date.
        LocalDateTime taskDateTime = taskToAdd.getCalendarDT();
        if (taskDateTime == null) {
            taskDateTime = DateUtil.NO_DATETIME_VALUE;
        }
        LocalDate taskDate = taskDateTime.toLocalDate();
        
        // Check TaskList if it contains a TaskListDateItem with the date.
        TaskListDateItemHandle dateItem = taskList.getTaskListDateItem(taskDate);
        assertSameDate(taskDate, dateItem);
        
        // Check TaskListDateItem if it contains the TaskListTaskItem with the same data.
        TaskListTaskItemHandle taskItem = dateItem.getTaskListTaskItem(taskToAdd.getName());
        assertEquals(taskItem.getName(), taskToAdd.getName());
    }
}
