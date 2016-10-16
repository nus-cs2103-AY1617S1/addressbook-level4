package seedu.todo.model;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.MoveableStorage;
import seedu.todo.testutil.TimeUtil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class TodoTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock private MoveableStorage<ImmutableTodoList> storage;
    @Mock private ImmutableTodoList storageData; 
    private TodoList todo;
    private UnmodifiableObservableList<ImmutableTask> observableList;
    
    @Before
    public void setUp() throws Exception {
        when(storage.read()).thenReturn(storageData);
        todo = new TodoList(storage);
        observableList = todo.getObserveableList();
    }
    
    @Test
    public void testEmptyStorage() throws Exception {
        when(storage.read()).thenThrow(new FileNotFoundException());
        todo = new TodoList(storage);
        
        assertThat(todo.getTasks(), empty());
    }
    
    @Test
    public void testRestoreFromStorage() {
        // Create a mock todo list, add it to mock storage and try to get
        // TodoList to retrieve it
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        
        when(storageData.getTasks()).thenReturn(ImmutableList.of(task1, task2));
        todo = new TodoList(storage);

        assertEquals(2, todo.getTasks().size());
        assertTrue(todo.getTasks().contains(task1));
        assertTrue(todo.getTasks().contains(task2));
    }

    @Test
    public void testAdd() throws Exception {
        assertNotNull(todo.add("Test Task 1"));
        assertEquals(1, todo.getTasks().size());
        assertFalse(getTask(0).isPinned());
        assertFalse(getTask(0).isCompleted());
        verify(storage).save(todo);

        assertNotNull(todo.add("Test Task 2"));
        assertEquals(2, todo.getTasks().size());
        assertEquals("Test Task 1", getTask(0).getTitle());
        assertEquals("Test Task 2", getTask(1).getTitle());
        verify(storage, times(2)).save(todo);
    }

    @Test
    public void testUpdate() throws Exception {
        final String DESCRIPTION = "Really long description blah blah blah";
        final String TITLE = "New title";
        
        todo.add("Old Title");

        // Check that updating string fields work
        assertNotNull(todo.update(1, t -> {
            t.setTitle(TITLE);
            t.setDescription(DESCRIPTION);
        }));
        
        assertEquals(TITLE, getTask(0).getTitle());
        assertEquals(DESCRIPTION, getTask(0).getDescription().get());
        verify(storage, times(2)).save(todo);
    }

    @Test
    public void testUpdateBooleanFields() throws Exception {
        todo.add("Test Task");

        // Check that updating boolean fields work
        assertNotNull(todo.update(1, t -> t.setPinned(true)));
        assertTrue(getTask(0).isPinned());
        verify(storage, times(2)).save(todo);

        assertNotNull(todo.update(1, t -> t.setCompleted(true)));
        assertTrue(getTask(0).isCompleted());
        verify(storage, times(3)).save(todo);
    }

    @Test
    public void testUpdateDateFields() throws Exception {
        todo.add("Test Task");

        assertNotNull(todo.update(1, t -> {
            t.setStartTime(TimeUtil.now);
            t.setEndTime(TimeUtil.now.plusHours(4));
        }));
        
        ImmutableTask task = observableList.get(0);
        assertEquals(TimeUtil.now, task.getStartTime().get());
        assertEquals(TimeUtil.now.plusHours(4), task.getEndTime().get());
        
        todo.update(1, t -> t.setEndTime(TimeUtil.now));
        task = observableList.get(0);
        assertEquals(TimeUtil.now, task.getStartTime().get());
        assertEquals(TimeUtil.now, task.getEndTime().get());
    }

    @Test
    public void testSorting() throws Exception {
        todo.add("Task 3", p -> p.setEndTime(TimeUtil.now));
        todo.add("Task 2", p -> p.setEndTime(TimeUtil.now.plusHours(2)));
        todo.add("Task 1", p -> p.setEndTime(TimeUtil.now.plusHours(1)));

        // Check that the items are sorted in lexicographical order by title
        todo.view(null, (a, b) -> a.getTitle().compareTo(b.getTitle()));
        assertEquals("Task 1", observableList.get(0).getTitle());
        assertEquals("Task 2", observableList.get(1).getTitle());
        assertEquals(3, observableList.size());

        // Insert an item that comes before all others lexicographically
        todo.add("Task 0", p -> p.setEndTime(TimeUtil.now.plusHours(3)));
        assertEquals("Task 0", observableList.get(0).getTitle());

        // Change its title and check that it has moved down
        todo.update(1, t -> t.setTitle("Task 4"));
        assertEquals("Task 1", observableList.get(0).getTitle());
        assertEquals("Task 4", observableList.get(3).getTitle());

        // Check that sorting by time works
        // Chronological ordering would give us Task 3, 1, 2, 4
        todo.view(null, (a, b) -> a.getEndTime().get().compareTo(b.getEndTime().get()));
        assertEquals("Task 3", observableList.get(0).getTitle());
        assertEquals("Task 1", observableList.get(1).getTitle());
        assertEquals("Task 2", observableList.get(2).getTitle());
        assertEquals("Task 4", observableList.get(3).getTitle());
    }

    @Test
    public void testFiltering() {
        todo.add("Foo");
        todo.add("FooBar");
        todo.add("Bar");
        todo.add("Bar Bar");

        // Give us only tasks with "Foo" in the title
        todo.view(t -> t.getTitle().contains("Foo"), null);
        assertEquals(2, observableList.size());
    }

    @Test
    public void testDeleting() throws Exception {
        todo.add("Foo");
        todo.add("FooBar");
        todo.add("Bar");
        todo.add("Bar Bar");

        // Delete the first task, then check that it has been deleted
        ImmutableTask topTask = todo.getObserveableList().get(0);
        todo.delete(1);
        assertFalse(todo.getTasks().contains(topTask));
        assertEquals(3, todo.getTasks().size());
        verify(storage, times(5)).save(todo);

        // Continue deleting the top task until the list is empty
        todo.delete(1);
        verify(storage, times(6)).save(todo);

        todo.delete(1);
        verify(storage, times(7)).save(todo);

        todo.delete(1);
        verify(storage, times(8)).save(todo);

        assertTrue(todo.getTasks().isEmpty());
    }

    @Test(expected = ValidationException.class)
    public void testIllegalUpdate() throws Exception {
        todo.add("Foo Bar Test");
        todo.update(2, t -> t.setTitle("Test 2"));
    }

    @Test(expected = ValidationException.class)
    public void testIllegalDelete() throws Exception {
        todo.add("Foo Bar Test");
        todo.delete(2);
    }

    @Test
    public void testPinning() throws Exception {
        todo.add("Task 1");
        todo.add("Task 2");
        todo.add("Task 3");

        // Get the last item and pin it
        ImmutableTask lastTask = observableList.get(2);
        todo.update(3, t -> t.setPinned(true));

        assertTrue(observableList.get(0).isPinned());
        assertEquals(lastTask.getTitle(), observableList.get(0).getTitle());
    }

    @Test
    public void testEditSetEmptyTitle() throws Exception {
        todo.add("Task 1");

        try {
            todo.update(1, t -> t.setTitle(""));
            fail();
        } catch (ValidationException e) {
            // Check that the bad update didn't go through
            assertEquals("Task 1", getTask(0).getTitle());
        }
    }

    @Test
    public void testEdit() throws Exception {
        todo.add("Task 1");

        try {
            todo.update(1, t -> {
                t.setStartTime(LocalDateTime.now());
                t.setTitle("Title changed");
            });
            fail();
        } catch (ValidationException e) {
            assertEquals("Task 1", getTask(0).getTitle());
        }
    }

    @Test
    public void testAddOnlyStartDate() throws Exception {
        try {
            todo.add("Task 1", t -> t.setStartTime(LocalDateTime.now()));
            fail();
        } catch (ValidationException e) {
            // Check that the new task is not added to the list
            assertEquals(0, todo.getTasks().size());
        }
    }

    @Test(expected = ValidationException.class)
    public void testAddInvalidDateRange() throws Exception {
        todo.add("Task 1", t -> {
            t.setStartTime(LocalDateTime.now());
            t.setEndTime(LocalDateTime.now().minusHours(2));
        });
    }
    
    @Test
    public void testSave() throws Exception {
        todo.save("new location");
        verify(storage).save(todo, "new location");
    }
    
    @Test
    public void testLoad() throws Exception {
        when(storageData.getTasks())
            .thenReturn(ImmutableList.of(new Task("Hello world")));
        when(storage.read("new location")).thenReturn(storageData);
        
        todo.load("new location");
        assertEquals("Hello world", getTask(0).getTitle());
    }
    
    @Test
    public void testGetSaveLocation() throws Exception {
        when(storage.getLocation()).thenReturn("test location");
        assertEquals("test location", todo.getStorageLocation());
    }

    private ImmutableTask getTask(int index) {
        return todo.getTasks().get(index);
    }
}
