package seedu.todo.logic.commands;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

import org.junit.Test;

import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.commons.events.ui.ExpandCollapseTaskEvent;
import seedu.todo.commons.events.ui.HighlightTaskEvent;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.testutil.EventsCollector;
import seedu.todo.testutil.TimeUtil;

//@@author A0092382A
public class AddCommandTest extends CommandTest {
    @Override
    protected BaseCommand commandUnderTest() {
        return new AddCommand();
    }

    @Test
    public void testAddTask() throws Exception {
        setParameter("Hello World");
        EventsCollector eventsCollector = new EventsCollector();
        execute(true);
        ImmutableTask addedTask = getTaskAt(1);
        assertEquals("Hello World", addedTask.getTitle());
        assertFalse(addedTask.isPinned());
        assertFalse(addedTask.getDescription().isPresent());
        assertFalse(addedTask.getLocation().isPresent());
        assertThat(eventsCollector.get(0), instanceOf(HighlightTaskEvent.class));
        assertThat(eventsCollector.get(1), instanceOf(ExpandCollapseTaskEvent.class));
    }
    
    @Test
    public void testAddTaskWithLocation() throws Exception {
        setParameter("Hello NUS");
        setParameter("l", "NUS");
        execute(true);
        
        ImmutableTask taskWithLocation = getTaskAt(1);
        
        assertEquals("Hello NUS", taskWithLocation.getTitle());
        assertFalse(taskWithLocation.isPinned());
        assertFalse(taskWithLocation.getDescription().isPresent());
        assertEquals("NUS", taskWithLocation.getLocation().get());
    }
    
    @Test
    public void testAddTaskWithDescription() throws Exception {
        setParameter("Destroy World");
        setParameter("m", "Remember to get Dynamites on sale!");
        execute(true);
        
        ImmutableTask taskWithDescription = getTaskAt(1);
        
        assertEquals("Destroy World", taskWithDescription.getTitle());
        assertEquals("Remember to get Dynamites on sale!", taskWithDescription.getDescription().get());
        assertFalse(taskWithDescription.isPinned());
        assertFalse(taskWithDescription.getLocation().isPresent());
    }
    
    @Test
    public void testAddPinnedTask() throws Exception {
        setParameter("Li Kai's Presentation");
        setParameter("p", null);
        execute(true);
        
        ImmutableTask pinnedAddedTask = getTaskAt(1);
        
        assertEquals("Li Kai's Presentation", pinnedAddedTask.getTitle());
        assertTrue(pinnedAddedTask.isPinned());
        assertFalse(pinnedAddedTask.getDescription().isPresent());
        assertFalse(pinnedAddedTask.getLocation().isPresent());
    }
    
    @Test
    public void testAddSingleDate() throws Exception {
        setParameter("Test Task");
        setParameter("d", "tomorrow 9am");
        execute(true);
        
        ImmutableTask task = getTaskAt(1);
        assertFalse(task.isEvent());
        assertEquals(TimeUtil.tomorrow().withHour(9), task.getEndTime().get());
    }

    @Test
    public void testAddDateRange() throws Exception {
        setParameter("Test Event");
        setParameter("d", "tomorrow 6 to 8pm");
        execute(true);

        ImmutableTask task = getTaskAt(1);
        assertTrue(task.isEvent());
        assertEquals(TimeUtil.tomorrow().withHour(18), task.getStartTime().get());
        assertEquals(TimeUtil.tomorrow().withHour(20), task.getEndTime().get());
    }
    
    @Test
    public void testAddMultipleParameters() throws Exception {
        setParameter("Task 1");
        setParameter("p", null);
        setParameter("l", "COM1");
        setParameter("m", "Useless task");
        execute(true);
        
        ImmutableTask taskWithParams = getTaskAt(1);
        
        assertEquals("Task 1", taskWithParams.getTitle());
        assertTrue(taskWithParams.isPinned());
        assertEquals("COM1", taskWithParams.getLocation().get());
        assertEquals("Useless task", taskWithParams.getDescription().get());
    }
    
    @Test
    public void testAdd_switchViewsNecessary() throws Exception {
        model.view(TaskViewFilter.COMPLETED);
        assertTotalTaskCount(0);
        setParameter("Task 1");
        setParameter("p", null);
        setParameter("l", "COM1");
        setParameter("m", "Useless task");
        execute(true);
        assertEquals(model.getViewFilter().get(), TaskViewFilter.DEFAULT);
        assertTotalTaskCount(1);
        assertVisibleTaskCount(1);
    }
    
    @Test
    public void testAdd_switchViewsUnnecessary() throws Exception {
        model.view(TaskViewFilter.INCOMPLETE);
        assertTotalTaskCount(0);
        setParameter("Task 1");
        setParameter("p", null);
        setParameter("l", "COM1");
        setParameter("m", "Useless task");
        execute(true);
        assertEquals(model.getViewFilter().get(), TaskViewFilter.INCOMPLETE);
        assertTotalTaskCount(1);
        assertVisibleTaskCount(1);
    }
    
    
}
