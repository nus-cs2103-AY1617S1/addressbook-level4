package seedu.todo.logic.commands;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.enumerations.TaskViewFilter;
import seedu.todo.commons.events.ui.ChangeViewRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.logic.commands.ViewCommand;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.EventsCollector;

//@@ author A0092382A
public class ViewCommandTest extends CommandTest {

    EventsCollector eventsCollector = new EventsCollector();
    ChangeViewRequestEvent event;
    ImmutableTask task1, task2, task3;
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new ViewCommand();
    }
    
    @Before
    public void setUp() throws Exception {
        task3 = model.add("Task 3", task -> { 
            task.setCompleted(true); 
            task.setEndTime(LocalDateTime.now().plusDays(1)); 
        });
        
        task2 = model.add("Task 2", task -> { 
            task.setCompleted(false); 
            task.setEndTime(LocalDateTime.now().plusDays(4));
        });
        
        task1 = model.add("Task 1", task -> { 
            task.setCompleted(false); 
            task.setEndTime(LocalDateTime.now());
        });
    }
    
    private void assertViewChangeEventFired(TaskViewFilter filter) {
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), filter);
    }

    @Test
    public void testViewDefault() throws ValidationException {
        assertVisibleTaskCount(3);
        setParameter("show all");
        execute(true);
        
        assertVisibleTaskCount(3);
        assertViewChangeEventFired(TaskViewFilter.DEFAULT);
    }
    
    @Test (expected = ValidationException.class)
    public void testInvalidView() throws ValidationException {
        setParameter("de fault");
        execute(false);
        // checkFeedback received
    }
    
    @Test
    public void testViewCompleted() throws ValidationException {
        setParameter("completed");
        execute(true);
        
        assertVisibleTaskCount(1);
        assertViewChangeEventFired(TaskViewFilter.COMPLETED);
        
        assertTaskNotVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskVisible(task3);
    }
    
    @Test
    public void testViewIncomplete() throws ValidationException {
        setParameter("incomplete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
    }
        
    
    @Test
    public void testCaseInsensitive() throws ValidationException {
        setParameter("Incomplete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
    }
    
    @Test
    public void testViewDueToday() throws ValidationException {
        setParameter("due today");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.DUE_TODAY);
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskNotVisible(task3);
    }
    
    @Test
    public void testSuccessiveCommands() throws ValidationException {
        setParameter("due today");
        execute(true);
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskNotVisible(task3);
        
        setParameter("incomplete");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
        
        setParameter("show all");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskVisible(task3);
        
        assertEquals(eventsCollector.size(), 3);
    }
}
