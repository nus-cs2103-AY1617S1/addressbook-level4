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
        model.add("Task 3", task -> { task.setCompleted(true); task.setEndTime(LocalDateTime.now().plusDays(1)); });
        model.add("Task 2", task -> { task.setCompleted(false); task.setEndTime(LocalDateTime.now().plusDays(4));});
        model.add("Task 1", task -> { task.setCompleted(false); task.setEndTime(LocalDateTime.now());});
        task1 = getTaskAt(1);
        task2 = getTaskAt(2);
        task3 = getTaskAt(3);
        
    }

    @Test
    public void testViewDefault() throws ValidationException {
        assertVisibleTaskCount(3);
        setParameter("show all");
        execute(true);
        assertVisibleTaskCount(3);
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), TaskViewFilter.DEFAULT);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskVisible(task3);
    }
    
    @Test (expected = ValidationException.class)
    public void testInvalidView() throws ValidationException {
        assertEquals( model.getObserveableList().size(), 3);
        setParameter("de fault");
        execute(false);
        //checkFeedback received
    }
    
    @Test
    public void testViewCompleted() throws ValidationException {
        assertVisibleTaskCount(3);
        setParameter("completed");
        execute(true);
        assertVisibleTaskCount(1);
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), TaskViewFilter.COMPLETED);
        assertTaskNotVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskVisible(task3);
    }
    
    @Test
    public void testViewIncomplete() throws ValidationException {
        assertVisibleTaskCount(3);
        setParameter("incomplete");
        execute(true);
        assertVisibleTaskCount(2);
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), TaskViewFilter.INCOMPLETE);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
        }
        
    
    @Test
    public void testCaseInsensitive() throws ValidationException {
        assertEquals( model.getObserveableList().size(), 3);
        setParameter("Incomplete");
        execute(true);
        assertEquals( model.getObserveableList().size(), 2);
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), TaskViewFilter.INCOMPLETE);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
    }
    
    @Test
    public void testViewDueToday() throws ValidationException {
        assertEquals( model.getObserveableList().size(), 3);
        setParameter("due today");
        execute(true);
        assertVisibleTaskCount(1);
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), TaskViewFilter.DUE_TODAY);
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskNotVisible(task3);
        
    }
    
    @Test
    public void testSuccessiveCommands() throws ValidationException {
        assertEquals( model.getObserveableList().size(), 3);
        setParameter("due today");
        execute(true);
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskNotVisible(task3);
        assertVisibleTaskCount(1);
        
        
        setParameter("incomplete");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
        assertVisibleTaskCount(2);
        
        setParameter("show all");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskVisible(task3);
        assertVisibleTaskCount(3);
        assertEquals(eventsCollector.size(), 3);
        
    }
    
    

    

}
