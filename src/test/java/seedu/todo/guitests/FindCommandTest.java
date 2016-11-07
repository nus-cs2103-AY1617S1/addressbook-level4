package seedu.todo.guitests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.FindController;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

// @@author A0093907W
public class FindCommandTest extends GuiTest {
    
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
    private String commandAdd3 = String.format("add event Some Milk from \"%s 4pm\" to \"%s 5pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    private Event event3 = new Event();
    
    public FindCommandTest() {
        task1.setName("Buy KOI");
        task1.setDueDate(DateUtil.parseDateTime(
                String.format("%s 20:00:00", oneDayToNowIsoString)));
        task2.setName("Buy Milk");
        task2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", oneDayFromNowIsoString)));
        event3.setName("Some Milk");
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
    public void find_unique_success() {
        console.runCommand("find KOI");
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
    }
    
    @Test
    public void find_uniqueCaseInsensitive_success() {
        console.runCommand("find koi");
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
    }
    
    @Test
    public void find_uniquePartialMatch_success() {
        console.runCommand("find KO");
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
    }
    
    @Test
    public void find_uniqueEndingMatch_fail() {
        console.runCommand("find OI");
        assertEquals(FindController.MESSAGE_LISTING_FAILURE, console.getConsoleTextArea());
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertEventVisible(event3);
    }
    
    @Test
    public void find_multiple_success() {
        console.runCommand("find Milk");
        assertTaskNotVisible(task1);
        assertTaskVisible(task2);
        assertEventVisible(event3);
    }
}
