package seedu.todo.model;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import com.google.common.collect.ImmutableList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import org.junit.rules.ExpectedException;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.MovableStorage;
import seedu.todo.testutil.TaskBuilder;
import seedu.todo.testutil.TimeUtil;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

public class TodoTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock private MovableStorage<ImmutableTodoList> storage;
    @Mock private ImmutableTodoList storageData;
    private TodoList todo;

    @Before
    public void setUp() throws Exception {
        when(storage.read()).thenReturn(storageData);
        todo = new TodoList(storage);
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
    public void testCompletedEvent() throws Exception {
        LocalDateTime start = LocalDateTime.now().minusHours(4);
        
        when(storageData.getTasks()).thenReturn(ImmutableList.of(
            TaskBuilder.name("Test task")
                .event(start, start.plusHours(1))
                .build()
        ));
        todo = new TodoList(storage);
        
        Thread.sleep(5);
        assertTrue(todo.getTasks().get(0).isCompleted());
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
    public void testCreatedAt() throws Exception {
        LocalDateTime initialTime = LocalDateTime.now().minusSeconds(1);

        LocalDateTime firstCreatedAt = todo.add("Test Task 1").getCreatedAt();
        assertTrue(firstCreatedAt.isAfter(initialTime));

        // Delay for a bit
        Thread.sleep(1);
        LocalDateTime secondCreatedAt = todo.add("Test Task 2").getCreatedAt();
        assertTrue(secondCreatedAt.isAfter(firstCreatedAt));
    }

    @Test
    public void testUpdate() throws Exception {
        final String DESCRIPTION = "Really long description blah blah blah";
        final String TITLE = "New title";
        
        todo.add("Old Title");

        // Check that updating string fields work
        assertNotNull(todo.update(0, t -> {
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
        assertNotNull(todo.update(0, t -> t.setPinned(true)));
        assertTrue(getTask(0).isPinned());
        verify(storage, times(2)).save(todo);

        assertNotNull(todo.update(0, t -> t.setCompleted(true)));
        assertTrue(getTask(0).isCompleted());
        verify(storage, times(3)).save(todo);
    }

    @Test
    public void testDeleting() throws Exception {
        todo.add("Foo");
        todo.add("FooBar");
        todo.add("Bar");
        todo.add("Bar Bar");

        // Delete the first task, then check that it has been deleted
        ImmutableTask topTask = todo.getObservableList().get(0);
        todo.delete(0);
        assertFalse(todo.getTasks().contains(topTask));
        assertEquals(3, todo.getTasks().size());
        verify(storage, times(5)).save(todo);

        // Continue deleting the top task until the list is empty
        todo.delete(0);
        verify(storage, times(6)).save(todo);

        todo.delete(0);
        verify(storage, times(7)).save(todo);

        todo.delete(0);
        verify(storage, times(8)).save(todo);

        assertTrue(todo.getTasks().isEmpty());
    }

    @Test
    public void testEditSetEmptyTitle() throws Exception {
        todo.add("Task 1");

        try {
            todo.update(0, t -> t.setTitle(""));
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
            todo.update(0, t -> {
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

    private ImmutableTask getTask(int index) {
        return todo.getTasks().get(index);
    }
}
