package seedu.cmdo.testutil;

import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;

//@@author A0139661Y
/**
 * Sorts an array according to task comparator
 */
public class ArraySorter {
	
	public static TestTask[] sortTestTasks(TestTask[] tt) {
		List<TestTask> list = Arrays.asList(tt);
		Collections.sort(list);
		return list.toArray(new TestTask[tt.length]);
	}
}
