package seedu.todo.model;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.model.property.TaskViewFilter;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.MovableStorage;
import seedu.todo.testutil.TaskFactory;
import seedu.todo.testutil.TimeUtil;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//@@author A0135817B
public class TodoModelTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Mock private MovableStorage<ImmutableTodoList> storage;
    @Mock private ImmutableTodoList storageData;
    
    private TodoList todolist;
    private TodoModel model;
    private UnmodifiableObservableList<ImmutableTask> observableList;

    @Before
    public void setUp() throws Exception {
        when(storage.read()).thenReturn(storageData);
        when(storageData.getTasks()).thenReturn(Collections.emptyList());

        todolist = new TodoList(storage);
        model = new TodoModel(todolist, storage);
        observableList = model.getObservableList();
    }
    
    @Test
    public void testUpdateDateFields() throws Exception {
        model.add("Test Task");

        assertNotNull(model.update(1, t -> {
            t.setStartTime(TimeUtil.now);
            t.setEndTime(TimeUtil.now.plusHours(4));
        }));

        ImmutableTask task = observableList.get(0);
        assertEquals(TimeUtil.now, task.getStartTime().get());
        assertEquals(TimeUtil.now.plusHours(4), task.getEndTime().get());

        model.update(1, t -> t.setEndTime(TimeUtil.now));
        task = observableList.get(0);
        assertEquals(TimeUtil.now, task.getStartTime().get());
        assertEquals(TimeUtil.now, task.getEndTime().get());
    }

    @Test
    public void testPinning() throws Exception {
        model.add("Task 1");
        model.add("Task 2");
        model.add("Task 3");

        // Get the last item and pin it
        ImmutableTask lastTask = observableList.get(2);
        model.update(3, t -> t.setPinned(true));

        assertTrue(observableList.get(0).isPinned());
        assertEquals(lastTask.getTitle(), observableList.get(0).getTitle());
    }


    @Test(expected = ValidationException.class)
    public void testIllegalUpdate() throws Exception {
        model.add("Foo Bar Test");
        model.update(2, t -> t.setTitle("Test 2"));
    }

    @Test(expected = ValidationException.class)
    public void testIllegalDelete() throws Exception {
        model.add("Foo Bar Test");
        model.delete(2);
    }

    @Test
    public void testSorting() throws Exception {
        model.add("Task 3", p -> p.setEndTime(TimeUtil.now));
        model.add("Task 2", p -> p.setEndTime(TimeUtil.now.plusHours(2)));
        model.add("Task 1", p -> p.setEndTime(TimeUtil.now.plusHours(1)));

        TaskViewFilter lexi = new TaskViewFilter("lexi", 
            null, (a, b) -> a.getTitle().compareTo(b.getTitle()));
        TaskViewFilter chrono = new TaskViewFilter("chrono", 
            null, (a, b) -> a.getEndTime().get().compareTo(b.getEndTime().get()));

        // Check that the items are sorted in lexicographical order by title
        model.view(lexi);
        assertEquals("Task 1", observableList.get(0).getTitle());
        assertEquals("Task 2", observableList.get(1).getTitle());
        assertEquals(3, observableList.size());

        // Insert an item that comes before all others lexicographically
        model.add("Task 0", p -> p.setEndTime(TimeUtil.now.plusHours(3)));
        assertEquals("Task 0", observableList.get(0).getTitle());

        // Change its title and check that it has moved down
        model.update(1, t -> t.setTitle("Task 4"));
        assertEquals("Task 1", observableList.get(0).getTitle());
        assertEquals("Task 4", observableList.get(3).getTitle());

        // Check that sorting by time works
        // Chronological ordering would give us Task 3, 1, 2, 4
        model.view(chrono);
        assertEquals("Task 3", observableList.get(0).getTitle());
        assertEquals("Task 1", observableList.get(1).getTitle());
        assertEquals("Task 2", observableList.get(2).getTitle());
        assertEquals("Task 4", observableList.get(3).getTitle());
    }

    @Test
    public void testFiltering() throws Exception {
        model.add("Foo");
        model.add("FooBar");
        model.add("Bar");
        model.add("Bar Bar");

        // Give us only tasks with "Foo" in the title
        model.find(t -> t.getTitle().contains("Foo"));
        assertEquals(2, observableList.size());
    }

    @Test
    public void testSave() throws Exception {
        model.save("new location");
        verify(storage).save(todolist, "new location");
    }

    @Test
    public void testLoad() throws Exception {
        model.add("Some task");
        
        when(storageData.getTasks())
            .thenReturn(ImmutableList.of(new Task("Hello world")));
        when(storage.read("new location")).thenReturn(storageData);

        model.load("new location");
        // Check that the task list has been replaced 
        assertEquals(1, todolist.getTasks().size());
        assertEquals("Hello world", getTask(0).getTitle());
    }
    
    @Test
    public void testUndo() throws Exception {
        model.add("Test task 1");
        model.add("Test task 2");
        
        // Undo add 
        model.undo();
        assertEquals(1, todolist.getTasks().size());
        assertEquals("Test task 1", getTask(0).getTitle());
        
        // Undo edit 
        model.update(1, t -> t.setTitle("New title"));
        model.undo();
        assertEquals("Test task 1", getTask(0).getTitle());
        
        // Undo delete
        model.delete(1);
        model.undo();
        assertEquals(1, todolist.getTasks().size());
    }
    
    @Test
    public void testPersistAfterUndo() throws Exception {
        model.add("Test task 1");
        model.undo();
        verify(storage, times(2)).save(todolist);
    }
    
    @Test
    public void testUndoStackSize() throws Exception {
        final int STACK_SIZE = 10;
        
        // Add 11 items (which will add 11 to the undo stack) 
        for (int i = 0; i < STACK_SIZE + 1; i++) {
            model.add(TaskFactory.taskTitle());
        }
        
        // Undo until the undo stack is empty 
        for (int i = 0; i < STACK_SIZE; i++) {
            model.undo();
        }
        
        // Make sure the last undo throws an exception, even though 11 add 
        // events were recorded 
        exception.expect(ValidationException.class);
        model.undo();
    }
    
    @Test(expected = ValidationException.class)
    public void testOnlyUndoDataChanges() throws Exception {
        // Actions that does not cause underlying data to change should not cause 
        // undo stack to increase
        model.find(ImmutableTask::isCompleted);
        model.undo();
    }
    
    @Test(expected = ValidationException.class)
    public void testEmptyUndoStack() throws Exception {
        model.undo();
    }
    
    @Test
    public void testRedo() throws Exception {
        model.add("Test task 1");
        model.add("Test task 2");
        model.add("Test task 3");

        // Undo then redo add 
        model.undo();
        model.undo();
        assertEquals(1, todolist.getTasks().size());
        
        model.redo();
        assertEquals(2, todolist.getTasks().size());
        assertEquals("Test task 2", getTask(1).getTitle());
        
        model.redo();
        assertEquals(3, todolist.getTasks().size());
        assertEquals("Test task 3", getTask(2).getTitle());
    }

    @Test
    public void testPersistAfterRedo() throws Exception {
        model.add("Test task 1");
        model.undo();
        model.redo();
        verify(storage, times(3)).save(todolist);
    }
    
    @Test
    public void testActionAfterRedoClearsStack() throws Exception {
        model.add("Test task 1");
        model.add("Test task 2");
        model.add("Test task 3");

        // Undo then redo add 
        model.undo();
        model.undo();
        assertEquals(1, todolist.getTasks().size());

        model.redo();
        assertEquals(2, todolist.getTasks().size());
        assertEquals("Test task 2", getTask(1).getTitle());
        
        // There should be at least one more undo in this, but by performing 
        // an action we should invalidate the undo stack
        model.add("Test task 4");
        exception.expect(ValidationException.class);
        model.redo();
    }
    
    @Test(expected = ValidationException.class)
    public void testEmptyRedoStack() throws Exception {
        model.redo();
    }

    @Test
    public void testGetSaveLocation() throws Exception {
        when(storage.getLocation()).thenReturn("test location");
        assertEquals("test location", model.getStorageLocation());
    }

    /**
     * Get the task at the zero-index position in memory (reverse insertion order)
     */
    private ImmutableTask getTask(int index) {
        return todolist.getTasks().get(index);
    }

}
