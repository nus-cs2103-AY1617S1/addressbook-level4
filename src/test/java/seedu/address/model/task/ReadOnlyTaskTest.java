package seedu.address.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;

//@@author A0141019U
public class ReadOnlyTaskTest {
	
    private ReadOnlyTask someday;
    private ReadOnlyTask deadline;

    public ReadOnlyTaskTest() throws Exception {
        someday = new Task(new Name("Read 50 shades of grey"), new TaskType("someday"), new Status("pending"), Optional.empty(),  Optional.empty(), new UniqueTagList());
        deadline = new Task(new Name("Read 50 shades of grey"), new TaskType("deadline"), new Status("pending"), Optional.empty(), Optional.of(LocalDateTime.parse("2016-12-25T12:13:14")), new UniqueTagList());
    }
    
    /*
     * Tests for someday tasks
     */
    @Test
    public void someday_printAsString() {
        String expected = "Read 50 shades of grey Task type: Someday Status: Pending Tags: ";
        assertEquals(expected, someday.getAsText());
    }

    @Test
    public void someday_getName() throws Exception {
        Name expected = new Name("Read 50 shades of grey");
        assertEquals(expected, someday.getName());
    }

    @Test
    public void someday_getStatus() {
        Status expected = new Status("pending");
        assertEquals(expected, someday.getStatus());
    }

    @Test
    public void someday_getTaskType() {
        TaskType expected = new TaskType("someday");
        assertEquals(expected, someday.getTaskType());
    }

    @Test
    public void someday_getStartDate() {
        Optional<LocalDateTime> expected = Optional.empty();
        assertEquals(expected, someday.getStartDate());
    }

    @Test
    public void someday_getEndDate() {
        Optional<LocalDateTime> expected = Optional.empty();
        assertEquals(expected, someday.getEndDate());
    }

    /*
     * Tests for deadline tasks
     */
    @Test
    public void deadline_getTaskType() {
        TaskType expected = new TaskType("deadline");
        assertEquals(expected, deadline.getTaskType());
    }

    @Test
    public void deadline_getStartDate() {
        Optional<LocalDateTime> expected = Optional.empty();
        assertEquals(expected, deadline.getStartDate());
    }

    @Test
    public void deadline_getEndDate() {
        Optional<LocalDateTime> expected = Optional.of(LocalDateTime.parse("2016-12-25T12:13:14"));
        assertEquals(expected, deadline.getEndDate());
    }
}