package seedu.address.model.task;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.TMReadOnlyTask.TaskType;


public class TMReadOnlyTaskTest {
	
	private TMReadOnlyTask someday;
	private TMReadOnlyTask deadline;
	
    public TMReadOnlyTaskTest() throws Exception {
		someday = new TMTask(new Name("Read 50 shades of grey"), new Status(), new UniqueTagList());
		deadline = new TMTask(new Name("Read 50 shades of grey"), new Status(), new Date(0), new UniqueTagList());
    }
    
    
    /*
     * Tests for someday tasks
     */
	@Test
	public void someday_printAsString() {
		String expected = "Read 50 shades of grey Task type: Someday Status: Not done Tags: ";
		assertEquals(expected, someday.getAsText());
	}
	
	@Test
	public void someday_getName() throws Exception {
		Name expected = new Name("Read 50 shades of grey");
		assertEquals(expected, someday.getName());
	}
	
	@Test
	public void someday_getStatus() {
		Status expected = new Status();
		assertEquals(expected, someday.getStatus());
	}
	
	@Test
	public void someday_getTaskType() {
		TaskType expected = TaskType.SOMEDAY;
		assertEquals(expected, someday.getTaskType());
	}
	
	@Test
	public void someday_getStartDate() {
		Optional<Date> expected = Optional.empty();
		assertEquals(expected, someday.getStartDate());
	}
	
	@Test
	public void someday_getEndDate() {
		Optional<Date> expected = Optional.empty();
		assertEquals(expected, someday.getEndDate());
	}
	
	/*
	 * Tests for deadline tasks
	 */
	@Test
	public void deadline_getTaskType() {
		TaskType expected = TaskType.DEADLINE;
		assertEquals(expected, deadline.getTaskType());
	}
	
	@Test
	public void deadline_getStartDate() {
		Optional<Date> expected = Optional.empty();
		assertEquals(expected, deadline.getStartDate());
	}
	
	@Test
	public void deadline_getEndDate() {
		Optional<Date> expected = Optional.of(new Date(0));
		assertEquals(expected, deadline.getEndDate());
	}
}
