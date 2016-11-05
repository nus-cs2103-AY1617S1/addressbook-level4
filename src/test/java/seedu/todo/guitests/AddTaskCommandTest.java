package seedu.todo.guitests;

import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;

/**
 * @@author A0139812A
 */
public class AddTaskCommandTest extends GuiTest {

    @Test
    public void addTaskWithDeadline() {
        String command = "add task Buy milk by Oct 15 2016 2pm";
        Task task = new Task();
        task.setName("Buy milk");
        task.setCalendarDateTime(DateUtil.parseDateTime("2016-10-15 14:00:00"));
        assertTaskVisibleAfterCmd(command, task);
    }

    @Test
    public void addFloatingTask() {
        String command = "add task Buy milk";
        Task task = new Task();
        task.setName("Buy milk");
        assertTaskVisibleAfterCmd(command, task);
    }
}
