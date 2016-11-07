package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.CompleteTaskController;
import seedu.todo.controllers.UncompleteTaskController;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

// @@author A0093907W
public class CompleteUncompleteTaskTest extends GuiTest {
    
    private final LocalDateTime oneDayFromNow = LocalDateTime.now().plusDays(1);
    private final String oneDayFromNowString = DateUtil.formatDate(oneDayFromNow);
    private final String oneDayFromNowIsoString = DateUtil.formatIsoDate(oneDayFromNow);
    private final LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private final String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
    private final String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    private final LocalDateTime oneDayToNow = LocalDateTime.now().minusDays(1);
    private final String oneDayToNowString = DateUtil.formatDate(oneDayToNow);
    private final String oneDayToNowIsoString = DateUtil.formatIsoDate(oneDayToNow);
    
    private String commandAdd1 = String.format("add task Buy KOI by \"%s 8pm\"", oneDayToNowString);
    private Task task1 = new Task();
    private String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", oneDayFromNowString);
    private Task task2 = new Task();
    private String commandAdd3 = String.format("add event Some Event from \"%s 4pm\" to \"%s 5pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    private Event event3 = new Event();
    
    public CompleteUncompleteTaskTest() {
        task1.setName("Buy KOI");
        task1.setDueDate(DateUtil.parseDateTime(
                String.format("%s 20:00:00", oneDayToNowIsoString)));
        task2.setName("Buy Milk");
        task2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", oneDayFromNowIsoString)));
        event3.setName("Some Event");
        event3.setStartDate(DateUtil.parseDateTime(
                String.format("%s 16:00:00", twoDaysFromNowIsoString)));
        event3.setEndDate(DateUtil.parseDateTime(
                String.format("%s 17:00:00", twoDaysFromNowIsoString)));
    }
    
    @Before
    public void fixtures() {
        console.runCommand("clear");
        console.runCommand(commandAdd1);
        console.runCommand(commandAdd2);
        console.runCommand(commandAdd3);
    }
    
    @Test
    public void complete_futureTask_show() {
        console.runCommand("complete 2");
        assertTaskVisible(task2);
    }
    
    @Test
    public void complete_overdueTask_hide() {
        console.runCommand("complete 1");
        assertTaskNotVisible(task1);
    }
    
    @Test
    public void uncomplete_futureTask_show() {
        console.runCommand("complete 2");
        console.runCommand("uncomplete 2");
        assertTaskVisible(task2);
    }
    
    @Test
    public void uncomplete_overdueTask_show() {
        console.runCommand("complete 1");
        console.runCommand("list completed");
        console.runCommand("uncomplete 1");
        assertTaskVisible(task1);
    }
    
    @Test
    public void complete_event_error() {
        console.runCommand("complete 3");
        String consoleMessage = CompleteTaskController.MESSAGE_CANNOT_COMPLETE_EVENT;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void uncomplete_event_error() {
        console.runCommand("uncomplete 3");
        String consoleMessage = UncompleteTaskController.MESSAGE_CANNOT_UNCOMPLETE_EVENT;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void complete_completedTask_error() {
        console.runCommand("complete 2");
        console.runCommand("complete 2");
        String consoleMessage = CompleteTaskController.MESSAGE_ALREADY_COMPLETED;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void uncomplete_uncompleteTask_error() {
        console.runCommand("uncomplete 1");
        String consoleMessage = UncompleteTaskController.MESSAGE_ALREADY_INCOMPLETE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void complete_wrongIndex_error() {
        console.runCommand("complete 10");
        String consoleMessage = CompleteTaskController.MESSAGE_INVALID_ITEM;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void uncomplete_wrongIndex_error() {
        console.runCommand("uncomplete 10");
        String consoleMessage = UncompleteTaskController.MESSAGE_INVALID_ITEM;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
}
