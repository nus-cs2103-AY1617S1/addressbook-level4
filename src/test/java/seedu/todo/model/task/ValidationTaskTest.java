package seedu.todo.model.task;

import org.junit.Before;
import org.junit.Test;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.tag.Tag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static seedu.todo.testutil.TestUtil.assertAllPropertiesEqual;

public class ValidationTaskTest {
    private ValidationTask task;

    @Before
    public void setUp() throws Exception {
        task = new ValidationTask("Test Task");
    }

    @Test
    public void testTaskString() {
        assertEquals("Test Task", task.getTitle());
    }

    @Test
    public void testValidateTaskNoTime() throws ValidationException {
        task.validate();
    }

    @Test(expected = ValidationException.class)
    public void testValidateEmptyStringTitle() throws ValidationException {
        task.setTitle("");
        task.validate();
    }

    @Test
    public void testValidateTitle() throws ValidationException {
        String testTitle = "test";
        task.setTitle(testTitle);
        task.validate();
        assertEquals(task.getTitle(), testTitle);
    }

    @Test
    public void testValidateTaskTime() throws ValidationException {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 2);

        task.setStartTime(startTime);
        task.setEndTime(endTime);

        task.validate();
    }

    @Test(expected = ValidationException.class)
    public void testValidateTaskOnlyStartTime() throws ValidationException {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 1);
        task.setStartTime(startTime);
        task.validate();
    }

    @Test
    public void testValidateTaskOnlyEndTime() throws ValidationException {
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 1);
        task.setEndTime(endTime);
        task.validate();
    }

    @Test(expected = ValidationException.class)
    public void testValidateTaskStartTimeBeforeEnd() throws ValidationException {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 2);
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 1);

        task.setStartTime(startTime);
        task.setEndTime(endTime);

        task.validate();
    }

    @Test
    public void testConvertToTask() throws ValidationException {
        LocalDateTime startTime = LocalDateTime.of(1, 1, 1, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(1, 1, 1, 1, 2);

        task.setStartTime(startTime);
        task.setEndTime(endTime);

        assertAllPropertiesEqual(task, task.convertToTask());
    }

    @Test(expected = AssertionError.class)
    public void testConvertDifferentTask() throws ValidationException {
        Task convertedTask = task.convertToTask();
        task.setPinned(true);
        // task.setDescription("test");
        assertAllPropertiesEqual(task, convertedTask);
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
    public void testLastUpdated() {
        assertNotNull(task.getCreatedAt());
        task.setCreatedAt();
        assertEquals(LocalDateTime.now(), task.getCreatedAt());
    }

    @Test
    public void testTags() throws ValidationException {
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
