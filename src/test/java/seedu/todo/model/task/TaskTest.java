package seedu.todo.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

public class TaskTest {
    private Task task;
    
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
        task = new Task("Test Task");
    }

    @Test
    public void testTaskString() {
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    public void testTaskImmutableTask() {
        MockTask original = new MockTask("Mock Task");
        assertAllPropertiesEqual(original, new Task(original));
        
        original = new MockTask("Mock Task"); 
        original.startTime = LocalDateTime.now();
        original.endTime = LocalDateTime.now().plusHours(2);
        assertAllPropertiesEqual(original, new Task(original));
        
        original = new MockTask("Mock Task"); 
        original.description = "A Test Description";
        original.location = "Test Location";
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
    public void testCompleted() {
        assertFalse(task.isCompleted());

        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testTags() {
        assertEquals(0, task.getTags().size());
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
        MockTask testTask = new MockTask("Mock Task");
        testTask.uuid = task.getUUID(); 
        assertEquals(task, testTask);
    }

}
