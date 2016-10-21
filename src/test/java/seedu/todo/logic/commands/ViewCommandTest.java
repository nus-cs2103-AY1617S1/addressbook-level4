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
import seedu.todo.testutil.TaskBuilder;
import seedu.todo.testutil.TimeUtil;

//@@ author A0092382A
public class ViewCommandTest extends CommandTest {
    private EventsCollector eventsCollector = new EventsCollector();
    
    private int[] all, events, dueSoon, completed, incomplete;
    
    private List<ImmutableTask> tasks;
    
    @Override
    protected BaseCommand commandUnderTest() {
        return new ViewCommand();
    }
    
    @Before
    public void setUp() throws Exception {
        tasks = ImmutableList.of(
            TaskBuilder.name("0. Completed, no deadline")
                .completed()
                .build(),

            TaskBuilder.name("1. Due tomorrow")
                .due(TimeUtil.tomorrow().plusHours(8))
                .build(),

            TaskBuilder.name("2. Due today")
                .due(TimeUtil.today().plusHours(8))
                .build(),

            TaskBuilder.name("3. Pinned task")
                .pinned()
                .build(),

            TaskBuilder.name("4. Due today, completed")
                .completed()
                .due(TimeUtil.today().plusHours(10))
                .build(),

            TaskBuilder.name("5. Event happening today")
                .event(TimeUtil.today().plusHours(12), TimeUtil.today().plusHours(14))
                .build(),

            TaskBuilder.name("6. Event happened yesterday")
                .event(TimeUtil.today(). minusHours(14), TimeUtil.today().minusHours(12))
                .build()
        );
        
        todolist.setTasks(tasks);
        
        for (ImmutableTask task : model.getObservableList()) {
            System.out.println(task.getTitle() + " " + task.getLastUpdated());
        }
        System.out.println();
        
        all = new int[]{ 3, 6, 5, 4, 2, 1, 0 };
        events = new int[]{ 6, 5 };
        dueSoon = new int[]{ 2, 1 };
        completed = new int[]{ 4, 0 };
        incomplete = new int[]{ 3, 6, 2, 5, 1 };
    }
    
    private void assertViewChangeEventFired(TaskViewFilter filter) {
        assertThat(eventsCollector.get(0), instanceOf(ChangeViewRequestEvent.class));
        ChangeViewRequestEvent event = (ChangeViewRequestEvent) eventsCollector.get(0);
        assertEquals(event.getNewView(), filter);
    }
    
    private void assertTasksVisible(int[] taskIndices) {
        int i = 0;
        for (ImmutableTask task : model.getObservableList()) {
            assertEquals("Item " + (i + 1) + " in the list is incorrect", 
                tasks.get(taskIndices[i++]), task);
        }
        assertVisibleTaskCount(taskIndices.length);
    }

    @Test
    public void testViewDefault() throws ValidationException {
        assertVisibleTaskCount(all.length);
        setParameter("show all");
        execute(true);
        
        assertTasksVisible(all);
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
        assertTasksVisible(completed);
    }
    
    @Test
    public void testViewIncomplete() throws ValidationException {
        setParameter("incomplete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTasksVisible(incomplete);
    }
    
    @Test
    public void testCaseInsensitive() throws ValidationException {
        setParameter("IncompLete");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.INCOMPLETE);
        assertTasksVisible(incomplete);
    }
    
    @Test
    public void testViewDueSoon() throws ValidationException {
        setParameter("due soon");
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(dueSoon);
    }
    
    @Test
    public void testEvents() throws Exception {
        setParameter("events"); 
        execute(true);
        
        assertViewChangeEventFired(TaskViewFilter.EVENTS);
        assertTasksVisible(events);
    }

    @Test
    public void testShortcut() throws ValidationException {
        setParameter("d");
        execute(true);

        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(dueSoon);
    }
    
    @Test
    public void testShortcutCaseInsensitive() throws Exception {
        setParameter("D");
        execute(true);

        assertViewChangeEventFired(TaskViewFilter.DUE_SOON);
        assertTasksVisible(dueSoon);
    }
    
    @Test
    public void testSuccessiveCommands() throws ValidationException {
        setParameter("due soon");
        execute(true);
        assertTasksVisible(dueSoon);
        
        setParameter("incomplete");
        execute(true);
        assertTasksVisible(incomplete);
        
        setParameter("a");
        execute(true);
        assertTasksVisible(all);
        
        assertEquals(eventsCollector.size(), 3);
    }
}
