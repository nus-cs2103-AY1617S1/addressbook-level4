package seedu.address.model.task;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.model.tag.UniqueTagList;


public class TMReadOnlyTaskTest {
	
	private TMReadOnlyTask someday;
	private ReadOnlyTask readonly;
	
    public TMReadOnlyTaskTest() throws Exception {
		someday = new TMTask(new Name("Read 50 shades of grey"), new Status(), new UniqueTagList());
		readonly = new Task(new Name("john doe"), new Phone("99999999"), new Email("b@g.com"), new Address("26, good st, 333333"), new UniqueTagList());
    }
    
	@Test
	public void printAsString() {
		System.out.println(readonly.getAsText());
		System.out.println("----------------");
		System.out.println(someday.getAsText());
	}
}
