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

public class TaskEndDateComparatorTest {
	
	private TaskEndDateComparator comparator = new TaskEndDateComparator();
	private List<Task> unsorted1, unsorted2;
	private List<Task> sorted;
	
	public TaskEndDateComparatorTest() throws IllegalArgumentException, IllegalValueException {
		Task deadline1 = new Task(new Name("1 deadline 4pm 25-12-2015"), new TaskType("deadline"), new Status("not done"), 
				Optional.empty(), Optional.of(LocalDateTime.of(2015, 12, 25, 16, 0)), new UniqueTagList());
		
		Task event2 = new Task(new Name("2 event 5pm 25-12-2015"), new TaskType("event"), new Status("not done"), 
				Optional.of(LocalDateTime.of(2015, 12, 24, 17, 0)), Optional.of(LocalDateTime.of(2015, 12, 25, 17, 0)), 
				new UniqueTagList());
		
		Task event3 = new Task(new Name("3 event 1am 01-01-2016"), new TaskType("event"), new Status("done"), 
				Optional.of(LocalDateTime.of(2016, 1, 1, 0, 30)), Optional.of(LocalDateTime.of(2016, 1, 1, 1, 0)), 
				new UniqueTagList());
		
		Task deadline4 = new Task(new Name("4 deadline 4pm 25-12-2016"), new TaskType("deadline"), new Status("done"), 
				Optional.empty(), Optional.of(LocalDateTime.of(2016, 12, 25, 16, 0)), new UniqueTagList());
		
		Task someday5 = new Task(new Name("5 someday"), new TaskType("someday"), new Status("done"), 
				Optional.empty(), Optional.empty(), new UniqueTagList());
		
		Task someday6 = new Task(new Name("6 someday"), new TaskType("someday"), new Status("done"), 
				Optional.empty(), Optional.empty(), new UniqueTagList());
		
		Task[] sortedArr = new Task[] {
				deadline1, event2, event3, deadline4, someday5, someday6
		};
		Task[] unsortedArr1 = new Task[] {
				someday5, event2, event3, deadline1, deadline4, someday6
		};
		Task[] unsortedArr2 = new Task[] {
				deadline4, event3, someday6, deadline1, event2, someday5
		};
		
		sorted = Arrays.asList(sortedArr);
		unsorted1 = Arrays.asList(unsortedArr1);
		unsorted2 = Arrays.asList(unsortedArr2);
	}
	
	
	@Test
	public void testComparator() {
		assertFalse(unsorted1.equals(sorted));
		assertFalse(unsorted2.equals(sorted));
		
		Collections.sort(unsorted1, comparator);
		Collections.sort(unsorted2, comparator);
		
		System.out.println(unsorted1);
		System.out.println(unsorted2);
		
		assertEquals(sorted, unsorted1);
		assertEquals(sorted, unsorted2);
	}
	
}
