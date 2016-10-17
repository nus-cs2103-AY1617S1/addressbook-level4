package seedu.todo.model;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;
import seedu.todo.model.task.Task;
import seedu.todo.storage.MoveableStorage;
import seedu.todo.testutil.TimeUtil;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class TodoModelTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @Mock private MoveableStorage<ImmutableTodoList> storage;
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

        // Check that the items are sorted in lexicographical order by title
        model.view(null, (a, b) -> a.getTitle().compareTo(b.getTitle()));
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
        model.view(null, (a, b) -> a.getEndTime().get().compareTo(b.getEndTime().get()));
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
        model.view(t -> t.getTitle().contains("Foo"), null);
        assertEquals(2, observableList.size());
    }

    @Test
    public void testSave() throws Exception {
        model.save("new location");
        verify(storage).save(todolist, "new location");
    }

    @Test
    public void testLoad() throws Exception {
        when(storageData.getTasks())
            .thenReturn(ImmutableList.of(new Task("Hello world")));
        when(storage.read("new location")).thenReturn(storageData);

        model.load("new location");
        assertEquals("Hello world", getTask(0).getTitle());
    }

    @Test
    public void testGetSaveLocation() throws Exception {
        when(storage.getLocation()).thenReturn("test location");
        assertEquals("test location", model.getStorageLocation());
    }

    private ImmutableTask getTask(int index) {
        return todolist.getTasks().get(index);
    }

}
