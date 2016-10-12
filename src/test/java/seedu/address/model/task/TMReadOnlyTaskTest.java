package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;


public class TMReadOnlyTaskTest {
	
	private TMReadOnlyTask someday;
	
    public TMReadOnlyTaskTest() throws Exception {
		someday = new TMTask(new Name("Read 50 shades of grey"), new Status(), new UniqueTagList());
    }
    
	@Test
	public void printAsString() {
		String expected = "Read 50 shades of grey Task type: Someday Status: Not done Tags: ";
		assertEquals(expected, someday.getAsText());
	}
	
	@Test
	public void getName() throws Exception {
		Name expected = new Name("Read 50 shades of grey");
		assertEquals(expected, someday.getName());
	}
	
	@Test
	public void getStatus() {
		Status expected = new Status();
		assertEquals(expected, someday.getStatus());
	}
}
