package seedu.todo.guitests;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;

public class AddTaskCommandTest extends GuiTest {

    @Test
    public void addTaskDeadlineToEmptyList() {
        Task[] currentList = {};
        String command = "add task Buy milk by Oct 15 2016 2pm";
        Task task = new Task();
        task.setName("Buy milk");
        task.setCalendarDT(DateUtil.parseDateTime("2016-10-15 14:00:00"));
        assertAddSuccess(command, task, currentList);
    }

    /**
     * Utility method for testing if task has been successfully added to the GUI.
     * This runs a command and checks if TaskList contains TaskListTaskItem that matches
     * the task that was just added. <br><br>
     * 
     * TODO: Abstract out method in AddController that can return task from command,
     *       and possibly remove the need to have taskToAdd.
     * 
     * @param command
     * @param taskToAdd
     * @param currentList
     */
    private void assertAddSuccess(String command, Task taskToAdd, Task... currentList) {
        // Run the command in the console.
        console.runCommand(command);
        
        // Check TaskList if it contains a TaskListTaskItem with the task data.
        // TODO 
    }
}
