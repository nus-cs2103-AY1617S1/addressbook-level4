package seedu.address.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TestTask;
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
