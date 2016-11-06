package seedu.todo.guitests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.util.DateUtil;
import seedu.todo.controllers.ListController;
import seedu.todo.controllers.concerns.Disambiguator;
import seedu.todo.models.Event;
import seedu.todo.models.Task;

// @@author A0139812A
public class ListCommandTest extends GuiTest {
    private final LocalDateTime oneDayBeforeNow = LocalDateTime.now().minusDays(1);
    private final String oneDayBeforeNowString = DateUtil.formatDate(oneDayBeforeNow);
    private final String oneDayBeforeNowIsoString = DateUtil.formatIsoDate(oneDayBeforeNow);
    private final LocalDateTime oneDayFromNow = LocalDateTime.now().plusDays(1);
    private final String oneDayFromNowString = DateUtil.formatDate(oneDayFromNow);
    private final String oneDayFromNowIsoString = DateUtil.formatIsoDate(oneDayFromNow);
    private final LocalDateTime twoDaysFromNow = LocalDateTime.now().plusDays(2);
    private final String twoDaysFromNowString = DateUtil.formatDate(twoDaysFromNow);
    private final String twoDaysFromNowIsoString = DateUtil.formatIsoDate(twoDaysFromNow);
    private final LocalDateTime fiveDaysFromNow = LocalDateTime.now().plusDays(5);
    private final String fiveDaysFromNowString = DateUtil.formatDate(fiveDaysFromNow);
    private final String fiveDaysFromNowIsoString = DateUtil.formatIsoDate(fiveDaysFromNow);
    
    String commandAddPastEvent = String.format("add event Your 5th birthday from \"%s 00:00\" to \"%s 23:59\"", 
            oneDayBeforeNowString, oneDayBeforeNowString);
    Event pastEvent = new Event();
    
    String commandAddFloatingTask1 = "add task Buy a floatation device";
    Task floatingTask1 = new Task();
    
    String commandAddCompleteFloatingTask2 = "add task Buy a helicopter";
    String commandCompleteFloatingTask2 = "complete 2";
    Task completeFloatingTask2 = new Task();
    
    String commandAddOverdueTask2 = String.format("add task Do laundry by \"%s 5pm\"",
            oneDayBeforeNowString);
    String commandCompleteOverdueTask2 = "complete 2";
    Task completeOverdueTask2 = new Task();
    
    String commandAdd2 = String.format("add task Buy Milk by \"%s 9pm\"", twoDaysFromNowString);
    Task task2 = new Task();
    
    String commandAdd3 = String.format("add event Some Event from \"%s 4pm\" to \"%s 5pm\"",
            oneDayFromNowString, oneDayFromNowString);
    Event event3 = new Event();
    
    String commandAdd4 = String.format("add event Another Event from \"%s 8pm\" to \"%s 9pm\"",
            twoDaysFromNowString, twoDaysFromNowString);
    String commandTag4 = "tag 3 someTag";
    Event event4 = new Event();
    
    String commandAddCompleteFutureTask5 = String.format("add Play Xbox by \"%s 5pm\"",
            fiveDaysFromNowString, fiveDaysFromNowString);
    String commandCompleteFutureTask5 = "complete 5";
    Task completeFutureTask5 = new Task();
    
    // Flag to initialize items once per test run.
    private static boolean hasInitialized = false;
    
    public ListCommandTest() {
        System.out.println(commandAddPastEvent);
        
        completeFutureTask5.setName("Play Xbox");
        completeFutureTask5.setDueDate(DateUtil.parseDateTime(
                String.format("%s 17:00:00", fiveDaysFromNowIsoString)));
        
        floatingTask1.setName("Buy a floatation device");
        
        completeFloatingTask2.setName("Buy a helicopter");
        completeFloatingTask2.setCompleted();
        
        completeOverdueTask2.setName("Do laundry");
        completeOverdueTask2.setDueDate(DateUtil.parseDateTime(
                String.format("%s 17:00:00", oneDayBeforeNowIsoString)));
        completeOverdueTask2.setCompleted();
        
        pastEvent.setName("Your 5th birthday");
        pastEvent.setStartDate(DateUtil.parseDateTime(
                String.format("%s 00:00:00", oneDayBeforeNowIsoString)));
        pastEvent.setEndDate(DateUtil.parseDateTime(
                String.format("%s 23:59:00", oneDayBeforeNowIsoString)));
        
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
        event4.addTag("someTag");
    }
    
    @Before
    public void initFixtures() {
        if (hasInitialized) {
            return;
        }
        
        console.runCommand(commandAddPastEvent);
        
        console.runCommand(commandAddFloatingTask1);
        console.runCommand(commandAddCompleteFloatingTask2);
        console.runCommand(commandCompleteFloatingTask2);
        
        console.runCommand(commandAddOverdueTask2);
        console.runCommand(commandCompleteOverdueTask2);
        
        console.runCommand(commandAdd2);
        console.runCommand(commandAdd3);
        console.runCommand(commandAdd4);
        console.runCommand(commandTag4);

        console.runCommand(commandAddCompleteFutureTask5);
        console.runCommand(commandCompleteFutureTask5);
        
        hasInitialized = true;
    }
    
    @Test
    public void list_noArguments_showAll() {
        console.runCommand("list");
        assertEventNotVisible(pastEvent);
        assertTaskVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
        assertTaskVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasks_showTasks() {
        console.runCommand("list tasks");
        assertShowTasks();
        console.runCommand("list task");
        assertShowTasks();
    }
    
    private void assertShowTasks() {
        assertEventNotVisible(pastEvent);
        assertTaskVisible(floatingTask1);
        assertTaskVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksComplete_showCompleteTasks() {
        console.runCommand("list tasks complete");
        assertShowCompleteTasks();
        console.runCommand("list tasks completed");
        assertShowCompleteTasks();
        console.runCommand("list complete tasks");
        assertShowCompleteTasks();
        console.runCommand("list completed tasks");
        assertShowCompleteTasks();
        console.runCommand("list task complete");
        assertShowCompleteTasks();
        console.runCommand("list task completed");
        assertShowCompleteTasks();
        console.runCommand("list complete task");
        assertShowCompleteTasks();
        console.runCommand("list completed task");
        assertShowCompleteTasks();
        console.runCommand("list complete");
        assertShowCompleteTasks();
        console.runCommand("list completed");
        assertShowCompleteTasks();
    }
    
    private void assertShowCompleteTasks() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksComplete_showIncompleteTasks() {
        console.runCommand("list tasks incomplete");
        assertShowIncompleteTasks();
        console.runCommand("list tasks incompleted");
        assertShowIncompleteTasks();
        console.runCommand("list incomplete tasks");
        assertShowIncompleteTasks();
        console.runCommand("list incompleted tasks");
        assertShowIncompleteTasks();
        console.runCommand("list task incomplete");
        assertShowIncompleteTasks();
        console.runCommand("list task incompleted");
        assertShowIncompleteTasks();
        console.runCommand("list incomplete task");
        assertShowIncompleteTasks();
        console.runCommand("list incompleted task");
        assertShowIncompleteTasks();
        console.runCommand("list incomplete");
        assertShowIncompleteTasks();
        console.runCommand("list incompleted");
        assertShowIncompleteTasks();
    }
    
    private void assertShowIncompleteTasks() {
        assertEventNotVisible(pastEvent);
        assertTaskVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_events_showEvents() {
        console.runCommand("list events");
        assertShowEvents();
        console.runCommand("list event");
        assertShowEvents();
    }
    
    private void assertShowEvents() {
        assertEventVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_eventsOver_showOverEvents() {
        console.runCommand("list events over");
        assertShowOverEvents();
        console.runCommand("list event over");
        assertShowOverEvents();
        console.runCommand("list over events");
        assertShowOverEvents();
        console.runCommand("list over event");
        assertShowOverEvents();
        console.runCommand("list events past");
        assertShowOverEvents();
        console.runCommand("list event past");
        assertShowOverEvents();
        console.runCommand("list past events");
        assertShowOverEvents();
        console.runCommand("list past event");
        assertShowOverEvents();
        console.runCommand("list past");
        assertShowOverEvents();
        console.runCommand("list over");
        assertShowOverEvents();
    }
    
    private void assertShowOverEvents() {
        assertEventVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_eventsFuture_showFutureEvents() {
        console.runCommand("list events future");
        assertShowFutureEvents();
        console.runCommand("list event future");
        assertShowFutureEvents();
        console.runCommand("list future events");
        assertShowFutureEvents();
        console.runCommand("list future event");
        assertShowFutureEvents();
        console.runCommand("list future");
        assertShowFutureEvents();
    }
    
    private void assertShowFutureEvents() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksBeforeDate_showBefore() {
        console.runCommand(String.format("list tasks before %s 1pm", oneDayFromNowIsoString));
        assertShowTasksBefore();
        console.runCommand(String.format("list before %s 1pm tasks", oneDayFromNowIsoString));
        assertShowTasksBefore();
    }
    
    private void assertShowTasksBefore() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksAfterDate_showAfter() {
        console.runCommand(String.format("list tasks after %s 1pm", oneDayFromNowIsoString));
        assertShowTasksAfter();
        console.runCommand(String.format("list after %s 1pm tasks", oneDayFromNowIsoString));
        assertShowTasksAfter();
    }
    
    private void assertShowTasksAfter() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskVisible(completeFutureTask5);
    }
    
    @Test
    public void list_beforeDate_showBefore() {
        console.runCommand(String.format("list before %s 1pm", oneDayFromNowIsoString));
        assertShowBefore();
    }
    
    private void assertShowBefore() {
        assertEventVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_afterDate_showAfter() {
        console.runCommand(String.format("list after %s 1pm", oneDayFromNowIsoString));
        assertShowAfter();
    }
    
    private void assertShowAfter() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskVisible(task2);
        assertEventVisible(event3);
        assertEventVisible(event4);
        assertTaskVisible(completeFutureTask5);
    }
    
    @Test
    public void list_dateRange_showDateRange() {
        console.runCommand(String.format("list from %s 1pm to %s 1pm", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowDateRange();
    }
    
    private void assertShowDateRange() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksDateRange_showDateRange() {
        console.runCommand(String.format("list tasks from %s 1pm to %s 1pm", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowTasksDateRange();
        console.runCommand(String.format("list task from %s 1pm to %s 1pm", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowTasksDateRange();
        console.runCommand(String.format("list from %s 1pm to %s 1pm tasks", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowTasksDateRange();
        console.runCommand(String.format("list from %s 1pm to %s 1pm task", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowTasksDateRange();
    }
    
    private void assertShowTasksDateRange() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_eventsDateRange_showDateRange() {
        console.runCommand(String.format("list events from %s 1pm to %s 1pm", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowEventsDateRange();
        console.runCommand(String.format("list event from %s 1pm to %s 1pm", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowEventsDateRange();
        console.runCommand(String.format("list from %s 1pm to %s 1pm events", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowEventsDateRange();
        console.runCommand(String.format("list from %s 1pm to %s 1pm event", oneDayBeforeNowIsoString, twoDaysFromNowIsoString));
        assertShowEventsDateRange();
    }
    
    private void assertShowEventsDateRange() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_byTag_showWithTag() {
        console.runCommand("list tag someTag");
        assertShowWithTag();
    }
    
    private void assertShowWithTag() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_tasksByTag_showWithTag() {
        console.runCommand("list tasks tag someTag");
        assertShowTasksWithTag();
    }
    
    private void assertShowTasksWithTag() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventNotVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_eventsByTag_showWithTag() {
        console.runCommand("list events tag someTag");
        assertShowEventsWithTag();
    }
    
    private void assertShowEventsWithTag() {
        assertEventNotVisible(pastEvent);
        assertTaskNotVisible(floatingTask1);
        assertTaskNotVisible(completeFloatingTask2);
        assertTaskNotVisible(completeOverdueTask2);
        assertTaskNotVisible(task2);
        assertEventNotVisible(event3);
        assertEventVisible(event4);
        assertTaskNotVisible(completeFutureTask5);
    }
    
    @Test
    public void list_ambiguousArguments_disambig() {
        console.runCommand("list over tasks");
        assertDisambigAmbiguous();
        console.runCommand("list future tasks");
        assertDisambigAmbiguous();
        console.runCommand("list complete events");
        assertDisambigAmbiguous();
        console.runCommand("list incomplete events");
        assertDisambigAmbiguous();
    }
    
    @Test
    public void list_unknownTokens_disambig() {
        console.runCommand("list me out");
        assertDisambigAmbiguous();
    }
    
    private void assertDisambigAmbiguous() {
        String expected = String.format(ListController.TEMPLATE_LIST, Disambiguator.PLACEHOLDER_STARTTIME, 
                Disambiguator.PLACEHOLDER_ENDTIME, Disambiguator.PLACEHOLDER_TAG);
        assertEquals(expected, console.getConsoleInputText());
    }
    
    @Test
    public void list_invalidDate_disambig() {
        console.runCommand("list from invalidfrom to invalidto");
        String expected = String.format(ListController.TEMPLATE_LIST, "invalidfrom", "invalidto", Disambiguator.PLACEHOLDER_TAG);
        assertEquals(expected, console.getConsoleInputText());
    }
    
}
