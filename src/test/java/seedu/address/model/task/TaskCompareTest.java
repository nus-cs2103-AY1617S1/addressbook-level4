package seedu.address.model.task;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.TypicalTestTasks;


//@@author A0141019U
public class TaskCompareTest {
	
	private List<ReadOnlyTask> unsorted, sorted;
	
	public TaskCompareTest() throws IllegalArgumentException, IllegalValueException {
		TypicalTestTasks td = new TypicalTestTasks();
		unsorted = Arrays.asList(td.getUnsortedTypicalTasks());
		sorted = Arrays.asList(td.getTypicalTasks());
	}
	
	
	@Test
	public void testComparator() {
		assertFalse(unsorted.equals(sorted));
		
		Collections.sort(unsorted);	
		
		for (ReadOnlyTask t : sorted) {
			System.out.println("status: " + t.getStatus());
			System.out.println("start date: " + t.getStartDate().orElse(LocalDateTime.MAX));
			System.out.println("end date: " + t.getEndDate().orElse(LocalDateTime.MAX));
			System.out.println("name: " + t.getName());
			System.out.println();
		}
		System.out.println("==========================");
		for (ReadOnlyTask t : unsorted) {
			System.out.println("status: " + t.getStatus());
			System.out.println("start date: " + t.getStartDate().orElse(LocalDateTime.MAX));
			System.out.println("end date: " + t.getEndDate().orElse(LocalDateTime.MAX));
			System.out.println("name: " + t.getName());
			System.out.println();
		}
		
		assertEquals(sorted, unsorted);
	}
	
}
