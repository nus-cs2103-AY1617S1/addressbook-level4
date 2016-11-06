package seedu.todo.guitests;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

/**
 * @@author A0093907W
 */
public class TagUntagCommandTest extends GuiTest {
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
    
    @Before
    public void initFixtures() {
        // Need to re-initialize these on each test because we are modifying
        // them on tags.        
        task1 = new Task();
        task2 = new Task();
        event3 = new Event();
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
        console.runCommand("clear");
        console.runCommand(commandAdd1);
        console.runCommand(commandAdd2);
        console.runCommand(commandAdd3);
    }
    
    @Test
    public void tag_task_success() {
        console.runCommand("tag 1 argh");
        task1.addTag("argh");
        assertTaskVisible(task1);
    }
    
    @Test
    public void tag_event_success() {
        console.runCommand("tag 3 zzz");
        event3.addTag("zzz");
        assertEventVisible(event3);
    }
    
    @Test
    public void untag_task_success() {
        console.runCommand("tag 1 bugs");
        console.runCommand("untag 1 bugs");
        assertTaskVisible(task1);
    }
    
    @Test
    public void untag_event_success() {
        console.runCommand("tag 3 errors");
        console.runCommand("untag 3 errors");
        assertEventVisible(event3);
    }
}
