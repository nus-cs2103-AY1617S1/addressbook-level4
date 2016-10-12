package seedu.todo.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.MockStorage;

public class TodoTest {
    private class MockTodoList implements ImmutableTodoList {
        private ImmutableTask[] tasks; 
        
        public MockTodoList(ImmutableTask[] tasks) {
            this.tasks = tasks; 
        }
        
        @Override
        public List<ImmutableTask> getTasks() {
            return Arrays.asList(tasks);
        }
    }
    
    private TodoList todo;
    private UnmodifiableObservableList<ImmutableTask> observableList;
    private MockStorage storage;
    
    @Before
    public void setUp() {
        storage = new MockStorage();
        todo = new TodoList(storage);
        observableList = todo.getObserveableList();
    }
    
    @Test
    public void testRestoreFromStorage() {
        // Create a mock todo list, add it to mock storage and try to get 
        // TodoList to retrieve it
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");
        
        MockTodoList testList = new MockTodoList(new ImmutableTask[]{ task1, task2 });
        MockStorage nonEmptyStorage = new MockStorage(testList);
        TodoList todolist = new TodoList(nonEmptyStorage);
        
        assertEquals(2, todolist.getTasks().size());
        assertTrue(todolist.getTasks().contains(task1));
        assertTrue(todolist.getTasks().contains(task2));
    }
    
    @Test
    public void testAdd() {
        todo.add("Test Task 1");
        assertEquals(1, todo.getTasks().size());
        assertFalse(getTask(0).isPinned());
        assertFalse(getTask(0).isCompleted());
        storage.assertTodoListWasSaved();
        
        todo.add("Test Task 2");
        assertEquals(2, todo.getTasks().size());
        assertEquals("Test Task 1", getTask(0).getTitle());
        assertEquals("Test Task 2", getTask(1).getTitle());
        storage.assertTodoListWasSaved();
    }
    
    @Test
    public void testUpdateStringFields() throws Exception {
        String description = "Really long description blah blah blah";
        String title = "New title";
        
        todo.add("Old Title");
        ImmutableTask task = getTask(0);
        
        // Check that updating string fields work
        todo.update(task, t -> {
            t.setTitle(title);
            t.setDescription(description);
        });
        assertEquals(title, getTask(0).getTitle());
        assertEquals(description, getTask(0).getDescription().get());
        storage.assertTodoListWasSaved();
    }
    
    @Test
    public void testUpdateBooleanFields() throws Exception {
        todo.add("Test Task");
        ImmutableTask task = getTask(0);
        
        // Check that updating boolean fields work
        todo.update(task, t -> t.setPinned(true));
        assertTrue(getTask(0).isPinned());
        storage.assertTodoListWasSaved();

        todo.update(task, t -> t.setCompleted(true));
        assertTrue(getTask(0).isCompleted());
        storage.assertTodoListWasSaved();
    }
    
    @Test
    public void testUpdateDateFields() throws Exception {
        todo.add("Test Task");
        ImmutableTask task = getTask(0);
        
        todo.update(task, t -> {
            t.setEndTime(LocalDateTime.now());
            t.setEndTime(LocalDateTime.now().plusHours(4));
        });
        todo.update(task, t -> t.setEndTime(LocalDateTime.now()));
    }

    @Test
    public void testSorting() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        todo.add("Task 3", p -> p.setEndTime(now));
        todo.add("Task 2", p -> p.setEndTime(now.plusHours(2)));
        todo.add("Task 1", p -> p.setEndTime(now.plusHours(1)));
        
        // Check that the items are sorted in lexicographical order by title  
        todo.view(null, (a, b) -> a.getTitle().compareTo(b.getTitle()));
        assertEquals("Task 1", observableList.get(0).getTitle());
        assertEquals("Task 2", observableList.get(1).getTitle());
        assertEquals(3, observableList.size());
        
        // Insert an item that comes before all others lexicographically
        todo.add("Task 0", p -> p.setEndTime(now.plusHours(3)));
        ImmutableTask newTask = observableList.get(0);
        assertEquals("Task 0", observableList.get(0).getTitle());
        
        // Change its title and check that it has moved down
        todo.update(newTask, t -> t.setTitle("Task 4"));
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
        todo.delete(topTask);
        assertFalse(todo.getTasks().contains(topTask));
        assertEquals(3, todo.getTasks().size());
        storage.assertTodoListWasSaved();
        
        // Continue deleting the top task until the list is empty
        todo.delete(todo.getObserveableList().get(0));
        storage.assertTodoListWasSaved();
        
        todo.delete(todo.getObserveableList().get(0));
        storage.assertTodoListWasSaved();
        
        todo.delete(todo.getObserveableList().get(0));
        storage.assertTodoListWasSaved();
        
        assertTrue(todo.getTasks().isEmpty());
    }
    
    @Test(expected=IllegalValueException.class)
    public void testIllegalUpdate() throws Exception {
        todo.add("Foo Bar Test");
        todo.update(new Task("Hello world"), t -> t.setTitle("Test 2"));
    }
    
    @Test(expected=IllegalValueException.class)
    public void testIllegalDelete() throws Exception {
        todo.add("Foo Bar Test");
        todo.delete(new Task("Hello world"));
    }
    
    @Test
    public void testPinning() throws Exception {
        todo.add("Task 1");
        todo.add("Task 2");
        todo.add("Task 3");
        
        // Get the last item and pin it
        ImmutableTask lastTask = observableList.get(2);
        todo.update(lastTask, t -> t.setPinned(true));
        
        assertTrue(observableList.get(0).isPinned());
        assertEquals(lastTask.getTitle(), observableList.get(0).getTitle());
    }
    
    @Test
    public void testEditSetEmptyTitle() throws Exception {
        boolean exceptionThrown = false;
        todo.add("Task 1");
        ImmutableTask task = getTask(0);
        
        try {
            todo.update(task, t -> {
                t.setTitle("");
            });
        } catch (ValidationException e) {
            exceptionThrown = true;
        }
        
        assertTrue(exceptionThrown);
        
        // Check that the bad update didn't go through
        assertEquals("Task 1", getTask(0).getTitle());
    }

    @Test(expected = ValidationException.class)
    public void testEdit() throws Exception {
        boolean exceptionThrown = false;
        todo.add("Task 1");
        ImmutableTask task = getTask(0);

        try {
            todo.update(task, t -> {
                t.setStartTime(LocalDateTime.now());
                t.setTitle("Title change");
            });
        } catch (ValidationException e) {
            exceptionThrown = true;
        }

        assertTrue(exceptionThrown);
        assertEquals("Task 1", getTask(0).getTitle());
    }

    @Test(expected = ValidationException.class)
    public void testAddOnlyStartDate() throws Exception {
        todo.add("Task 1", t -> {
            t.setStartTime(LocalDateTime.now());
        });
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
