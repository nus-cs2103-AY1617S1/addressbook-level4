package seedu.todo.model.task;

import org.junit.Before;
import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seedu.todo.testutil.TestUtil.assertAllPropertiesEqual;

public class TaskTest {
    private Task task;

    @Before
    public void setUp() throws Exception {
        task = new Task("Test Task");
    }

    @Test
    public void testTaskString() {
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    public void testTaskImmutableTask() {
        Task original = new Task("Mock Task");
        assertAllPropertiesEqual(original, new Task(original));

        original = new Task("Mock Task");
        original.setStartTime(LocalDateTime.now());
        original.setEndTime(LocalDateTime.now().plusHours(2));
        assertAllPropertiesEqual(original, new Task(original));

        original = new Task("Mock Task");
        original.setDescription("A Test Description");
        original.setLocation("Test Location");
        assertAllPropertiesEqual(original, new Task(original));
    }

    @Test
    public void testTitle() {
        task.setTitle("New Title");
        assertEquals("New Title", task.getTitle());
    }

    @Test
    public void testDescription() {
        assertFalse(task.getDescription().isPresent());

        task.setDescription("A short description");
        assertEquals("A short description", task.getDescription().get());
    }

    @Test
    public void testLocation() {
        assertFalse(task.getLocation().isPresent());

        task.setLocation("Some Test Location");
        assertEquals("Some Test Location", task.getLocation().get());
    }

    @Test
    public void testTime() {
        assertFalse(task.getStartTime().isPresent());
        assertFalse(task.getEndTime().isPresent());
    }

    @Test
    public void testPinned() {
        assertFalse(task.isPinned());

        task.setPinned(true);
        assertTrue(task.isPinned());
    }

    @Test
    public void testLastUpdated() throws Exception {
        assertNotNull(task.getCreatedAt());

        LocalDateTime anotherTime = LocalDateTime.now().minusDays(1);

        task.setCreatedAt(anotherTime);
        assertEquals(anotherTime, task.getCreatedAt());
    }
    
    @Test
    public void testLastUpdatedNull() throws Exception {
        task.setCreatedAt(null);
        assertNotNull(task.getCreatedAt());
    }

    @Test
    public void testCompleted() {
        assertFalse(task.isCompleted());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }


    @Test
    public void testTags() throws ValidationException {
        assertEquals(0, task.getTags().size());

        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Hello"));
        tags.add(new Tag("World"));
        task.setTags(tags);

        assertEquals(2, task.getTags().size());
    }
    
    @Test
    public void testIsEvent() throws Exception {
        assertFalse(task.isEvent());
        
        task.setEndTime(LocalDateTime.now());
        assertFalse(task.isEvent());
        
        task.setStartTime(LocalDateTime.now().minusHours(4));
        assertTrue(task.isEvent());
    }

    @Test
    public void testGetObservableProperties() {
        assertEquals(8, task.getObservableProperties().length);
    }

    @Test
    public void testGetUUID() {
        assertNotNull(task.getUUID());
    }

    @Test
    public void testEqualsObject() {
        ImmutableTask stubTask = mock(ImmutableTask.class);
        when(stubTask.getUUID()).thenReturn(task.getUUID());
        assertEquals(task, stubTask);
        assertFalse(task.equals(12));
    }

}
