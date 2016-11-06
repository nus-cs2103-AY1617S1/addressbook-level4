package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.ClearController;
import seedu.todo.controllers.concerns.Renderer;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

/*
 * @@author A0093907W
 */
public class ClearCommandTest extends GuiTest {
    private final LocalDateTime oneDayFromNow = LocalDateTime.now().plusDays(1);
    private final String oneDayFromNowString = DateUtil.formatDate(oneDayFromNow);
    private final String oneDayFromNowIsoString = DateUtil.formatIsoDate(oneDayFromNow);
    private final LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private final String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
    private final String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    
    private String commandAdd1 = String.format("add task Buy KOI by \"%s 8pm\"", oneDayFromNowString);
    private Task task1 = new Task();
    private String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", twoDaysFromNowString);
    private Task task2 = new Task();
    
    private String commandAdd3 = String.format("add event Some Event from \"%s 4pm\" to \"%s 5pm\"",
            oneDayFromNowString, oneDayFromNowString);
    private Event event3 = new Event();
    private String commandAdd4 = String.format("add event Another Event from \"%s 8pm\" to \"%s 9pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    private Event event4 = new Event();
    
    public ClearCommandTest() {
        task1.setName("Buy KOI");
        task1.setCalendarDateTime(DateUtil.parseDateTime(
                String.format("%s 20:00:00", oneDayFromNowIsoString)));
        task2.setName("Buy Milk");
        
        task2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", twoDaysFromNowIsoString)));
        
        event3.setName("Some Event");
        event3.setStartDate(DateUtil.parseDateTime(
                String.format("%s 16:00:00", oneDayFromNowIsoString)));
        event3.setEndDate(DateUtil.parseDateTime(
                String.format("%s 17:00:00", oneDayFromNowIsoString)));
        
        event4.setName("Another Event");
        event4.setStartDate(DateUtil.parseDateTime(
                String.format("%s 20:00:00", twoDaysFromNowIsoString)));
        event4.setEndDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", twoDaysFromNowIsoString)));
    }
    
    @Before
    public void initFixtures() {
        console.runCommand("clear");
        console.runCommand(commandAdd1);
        console.runCommand(commandAdd2);
        console.runCommand(commandAdd3);
        console.runCommand(commandAdd4);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
    }
    
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisible(task1);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
    }
    
    @Test
    public void clear_allTasks_success() {
        console.runCommand("clear tasks");
        assertTaskNotVisible(task1);
        assertTaskNotVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
    }
    
    @Test
    public void clear_allEvents_success() {
        console.runCommand("clear events");
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
    }
    
    @Test
    public void clear_unknownCommand_disambiguate() {
        console.runCommand("clear yellow");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + String.format(ClearController.MESSAGE_UNKNOWN_TOKENS, "yellow");
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void clear_ambiguousCommand_disambiguate() {
        console.runCommand("clear completed over");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + ClearController.MESSAGE_AMBIGUOUS_TYPE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void clear_invalidTaskDate_disambiguate() {
        console.runCommand("clear tasks before mcdonalds");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + ClearController.MESSAGE_INVALID_DATE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
    @Test
    public void clear_invalidEventDate_disambiguate() {
        console.runCommand("clear events before mcdonalds");
        String consoleMessage = Renderer.MESSAGE_DISAMBIGUATE + "\n\n"
                + ClearController.MESSAGE_INVALID_DATE;
        assertEquals(consoleMessage, console.getConsoleTextArea());
    }
    
}
