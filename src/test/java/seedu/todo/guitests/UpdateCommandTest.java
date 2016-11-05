package seedu.todo.guitests;

import java.time.LocalDateTime;

import org.junit.Ignore;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

public class UpdateCommandTest extends GuiTest {
    
    @Test
    public void updateCommand_updateTaskName_success() {
        // Clear first
        console.runCommand("clear");
        
        // Add a task
        console.runCommand("add Buy milk by 2016-10-15 2pm");
        
        // Update the task
        String command = "update 1 name Buy bread";
        Task task = new Task();
        task.setName("Buy bread");
        task.setDueDate(DateUtil.parseDateTime("2016-10-15 14:00:00"));
        assertTaskVisibleAfterCmd(command, task);
    }
    
    @Test
    public void updateCommand_updateTaskWithDeadlineToNewDeadline_success() {
        // Clear first
        console.runCommand("clear");
        
        // Add a task
        console.runCommand("add Buy milk by 2016-10-15 2pm");
        
        // Update the task
        String command = "update 1 by today 5pm";
        Task task = new Task();
        task.setName("Buy milk");
        task.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        assertTaskVisibleAfterCmd(command, task);
    }
    
    @Test
    public void updateCommand_updateFloatingTaskToNewDeadline_success() {
        // Clear first
        console.runCommand("clear");
        
        // Add a task
        console.runCommand("add Buy milk");
        
        // Update the task
        String command = "update 1 by today 5pm";
        Task task = new Task();
        task.setName("Buy milk");
        task.setDueDate(LocalDateTime.now().toLocalDate().atTime(17, 0));
        assertTaskVisibleAfterCmd(command, task);
    }

    @Ignore
    @Test
    public void updateCommand_updateTaskWithDeadlineToFloatingTask() {
        // TODO: Make this pass
        
        // Clear first
        console.runCommand("clear");
        
        // Add a task
        console.runCommand("add Buy milk by today");
        
        // Update the task
        String command = "update 1 nodeadline";
        Task task = new Task();
        task.setName("Buy milk");
        task.setDueDate(DateUtil.NO_DATETIME_VALUE);
        assertTaskVisibleAfterCmd(command, task);
    }

    @Test
    public void updateCommand_updateEventName_success() {
        // Clear first
        console.runCommand("clear");
        
        // Get formatted string for two days from now, e.g. 17 Oct 2016
        LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
        String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
        String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
        
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = "update 1 name Updated presentation";
        Event event = new Event();
        event.setName("Updated presentation");
        event.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysFromNowIsoString)));
        event.setEndDate(DateUtil.parseDateTime(String.format("%s 21:00:00", twoDaysFromNowIsoString)));
        assertEventVisibleAfterCmd(command, event);
    }
    
    @Test
    public void updateCommand_updateEventStartDate_success() {
        // Clear first
        console.runCommand("clear");
        
        // Get formatted string for two days from now, e.g. 17 Oct 2016
        LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
        String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
        String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
        
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = String.format("update 1 from %s 5pm", twoDaysFromNowString);
        Event event = new Event();
        event.setName("Presentation");
        event.setStartDate(DateUtil.parseDateTime(String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        event.setEndDate(DateUtil.parseDateTime(String.format("%s 21:00:00", twoDaysFromNowIsoString)));
        assertEventVisibleAfterCmd(command, event);
    }

    @Test
    public void updateCommand_updateEventEndDate_success() {
        // Clear first
        console.runCommand("clear");
        
        // Get formatted string for two days from now, e.g. 17 Oct 2016
        LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
        String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
        String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
        
        // Add a task
        console.runCommand(String.format("add event Presentation from %s 2pm to %s 9pm", twoDaysFromNowString, twoDaysFromNowString));
        
        // Update the task
        String command = String.format("update 1 to %s 5pm", twoDaysFromNowString);
        Event event = new Event();
        event.setName("Presentation");
        event.setStartDate(DateUtil.parseDateTime(String.format("%s 14:00:00", twoDaysFromNowIsoString)));
        event.setEndDate(DateUtil.parseDateTime(String.format("%s 17:00:00", twoDaysFromNowIsoString)));
        assertEventVisibleAfterCmd(command, event);
    }

}
