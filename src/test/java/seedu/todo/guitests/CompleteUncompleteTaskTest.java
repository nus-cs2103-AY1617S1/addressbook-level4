package seedu.todo.guitests;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.models.Task;

/**
 * @@author A0093907W
 */
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
    
    String commandAdd1 = String.format("add task Buy KOI by \"%s 8pm\"", oneDayToNowString);
    Task task1 = new Task();
    String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", oneDayFromNowString);
    Task task2 = new Task();
    String commandAdd3 = String.format("add task Buy Coffee by \"%s 9pm\"", twoDaysFromNowString);
    Task task3 = new Task();
    
    public CompleteUncompleteTaskTest() {
        task1.setName("Buy KOI");
        task1.setDueDate(DateUtil.parseDateTime(
                String.format("%s 20:00:00", oneDayToNowIsoString)));
        task2.setName("Buy Milk");
        task2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", oneDayFromNowIsoString)));
        task3.setDueDate(DateUtil.parseDateTime(
                String.format("%s 21:00:00", twoDaysFromNowIsoString)));
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
        assertTaskVisibleAfterCmd("complete 2", task2);
    }
    
    @Test
    public void complete_overdueTask_hide() {
        assertTaskNotVisibleAfterCmd("complete 1", task1);
    }
    
    @Test
    public void uncomplete_futureTask_show() {
        console.runCommand("complete 2");
        assertTaskVisibleAfterCmd("uncomplete 2", task2);
    }
    
    @Test
    public void uncomplete_overdueTask_show() {
        console.runCommand("complete 1");
        console.runCommand("list completed");
        assertTaskVisibleAfterCmd("uncomplete 1", task1);
    }

}
