package seedu.todo.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;

public class ValidationTaskTest {
    private ValidationTask task;
    
    private void assertAllPropertiesEqual(ImmutableTask a, ImmutableTask b) {
        assertEquals(a.getTitle(), b.getTitle());
        assertEquals(a.getDescription(), b.getDescription());
        assertEquals(a.getLocation(), b.getLocation());
        assertEquals(a.getStartTime(), b.getStartTime());
        assertEquals(a.getEndTime(), b.getEndTime());
        assertEquals(a.getTags(), b.getTags());
        assertEquals(a.getUUID(), b.getUUID());
    }

    @Before
    public void setUp() throws Exception {
        task = new ValidationTask("Test Task");
    }

    @Test
    public void testTaskString() {
        assertEquals("Test Task", task.getTitle());
    }
    
    @Test
    public void testIsValidTaskNoTime() {
        assertTrue(task.isValidTask());
    }
    
    @Test
    public void testIsValidTaskTime() {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 2);
        
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        
        assertTrue(task.isValidTask());
    }
    
    @Test
    public void testIsValidTaskStartTimeBeforeEnd() {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 2);
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 1);
        
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        
        assertFalse(task.isValidTask());
    }

    @Test
    public void testTaskImmutableTask() {
        ValidationTask original = new ValidationTask("Mock Task");
        assertAllPropertiesEqual(original, new ValidationTask(original));
        
        original = new ValidationTask("Mock Task"); 
        original.setStartTime(LocalDateTime.now());
        original.setEndTime(LocalDateTime.now().plusHours(2));
        assertAllPropertiesEqual(original, new Task(original));
        
        original = new ValidationTask("Mock Task"); 
        original.setDescription("A Test Description");
        original.setLocation("Test Location");
        assertAllPropertiesEqual(original, new ValidationTask(original));
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
        
        // TODO: Time definitely needs validation, for example task end time 
        // should come after start time. Issue #16 https://github.com/CS2103AUG2016-W10-C4/main/issues/16 
        // is blocking this though
    }

    @Test
    public void testPinned() {
        assertFalse(task.isPinned());

        task.setPinned(true);
        assertTrue(task.isPinned());
    }

    @Test
    public void testCompleted() {
        assertFalse(task.isCompleted());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testTags() throws IllegalValueException {
        assertEquals(0, task.getTags().size());
        
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("Hello"));
        tags.add(new Tag("World"));
        task.setTags(tags);
        
        assertEquals(2, task.getTags().size());
        // TODO: This should do more when we finalize how tags can be edited 
    }

    @Test
    public void testGetUUID() {
        assertNotNull(task.getUUID());
    }
}
