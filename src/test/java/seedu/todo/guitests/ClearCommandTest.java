package seedu.todo.guitests;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
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
    
    String commandAdd1 = String.format("add task Buy KOI by \"%s 8pm\"", oneDayFromNowString);
    Task task1 = new Task();
    String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", twoDaysFromNowString);
    Task task2 = new Task();
    
    String commandAdd3 = String.format("add event Some Event from \"%s 4pm\" to \"%s 5pm\"",
            oneDayFromNowString, oneDayFromNowString);
    Event event3 = new Event();
    String commandAdd4 = String.format("add event Another Event from \"%s 8pm\" to \"%s 9pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    Event event4 = new Event();
    
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
        assertTaskVisibleAfterCmd(commandAdd1, task1);
        assertTaskVisibleAfterCmd(commandAdd2, task2);
        assertEventVisibleAfterCmd(commandAdd3, event3);
        assertEventVisibleAfterCmd(commandAdd4, event4);
    }
    
    @Test
    public void fixtures_test() {
        console.runCommand("clear");
        assertTaskNotVisibleAfterCmd("list", task1);
        assertTaskNotVisibleAfterCmd("list", task2);
        assertEventNotVisibleAfterCmd("list", event3);
        assertEventNotVisibleAfterCmd("list", event4);
    }
}
