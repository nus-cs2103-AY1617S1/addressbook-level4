package seedu.address.model.task;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TypicalTestTasks;


//@@author A0141019U
public class TaskDateComparatorTest {
	
	private TaskDateComparator comparator = new TaskDateComparator();
	private List<ReadOnlyTask> unsorted, sorted;
	
	public TaskDateComparatorTest() throws IllegalArgumentException, IllegalValueException {
		TypicalTestTasks td = new TypicalTestTasks();
		unsorted = Arrays.asList(td.getTypicalTasks());
		sorted = Arrays.asList(td.getSortedTypicalTasks());
	}
	
	
	@Test
	public void testComparator() {
		assertFalse(unsorted.equals(sorted));
		Collections.sort(unsorted, comparator);	
		assertEquals(sorted, unsorted);
	}
	
}
