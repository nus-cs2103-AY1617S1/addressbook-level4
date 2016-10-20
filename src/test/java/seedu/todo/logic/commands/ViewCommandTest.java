package seedu.todo.logic.commands;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.core.TaskViewFilter;
import seedu.todo.commons.events.ui.ChangeViewRequestEvent;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.EventsCollector;

//@@ author A0092382A
public class ViewCommandTest extends CommandTest {

    EventsCollector eventsCollector = new EventsCollector();
    ChangeViewRequestEvent event;
    ImmutableTask task1, task2, task3;
    List<ImmutableTask> tasks;
    
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
        
        tasks = ImmutableList.of(task1, task2, task3);
    }
    
    private void assertViewChangeEventFired(TaskViewFilter filter) {
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), filter);
    }
    
    private void assertTasksVisible(int[] taskIndices) {
        assertVisibleTaskCount(taskIndices.length);
        int i = 0;
        for (ImmutableTask task : model.getObservableList()) {
            assertEquals(tasks.get(taskIndices[i++]), task);
        }
    }

    @Test
    public void testViewDefault() throws ValidationException {
        assertVisibleTaskCount(3);
        setParameter("show all");
        execute(true);
        
        assertTasksVisible(new int[]{ 0, 1, 2 });
        assertViewChangeEventFired(TaskViewFilter.DEFAULT);
    }
    
    @Test (expected = ValidationException.class)
    public void testInvalidView() throws ValidationException {
        setParameter("de fault");
        execute(false);
    }
    
    @Test
    public void testViewCompleted() throws ValidationException {
        setParameter("completed");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.COMPLETED);
        assertTasksVisible(new int[]{ 2 });
    }
    
    @Test
    public void testViewIncomplete() throws ValidationException {
        setParameter("incomplete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTasksVisible(new int[]{ 0, 1 });
    }
    
    @Test
    public void testCaseInsensitive() throws ValidationException {
        setParameter("Incomplete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTasksVisible(new int[]{ 0, 1 });
    }
    
    @Test
    public void testViewDueSoon() throws ValidationException {
        setParameter("due soon");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(new int[]{ 0 });
    }


    @Test
    public void testShortcut() throws ValidationException {
        setParameter("d");
        execute(true);

        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(new int[]{ 0 });
    }
    
    @Test
    public void testShortcutCaseInsensitive() throws Exception {
        setParameter("D");
        execute(true);

        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(new int[]{ 0 });
    }
    
    @Test
    public void testSuccessiveCommands() throws ValidationException {
        setParameter("due soon");
        execute(true);
        assertTaskVisible(task1);
        assertTaskNotVisible(task2);
        assertTaskNotVisible(task3);
        
        setParameter("incomplete");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskNotVisible(task3);
        
        setParameter("a");
        execute(true);
        assertTaskVisible(task1);
        assertTaskVisible(task2);
        assertTaskVisible(task3);
        
        assertEquals(eventsCollector.size(), 3);
    }
}
