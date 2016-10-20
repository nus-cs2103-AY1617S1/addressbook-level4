package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Comparator;

//@@author A0141019U
public class TaskEndDateComparator implements Comparator<Task> {
	
	public TaskEndDateComparator() {}
	
	// Sorts first by end date then by alphabetical order of task name
	@Override
	public int compare(Task t1, Task t2) {
		LocalDateTime t1EndDate = t1.getEndDate().orElse(LocalDateTime.MAX);
		LocalDateTime t2EndDate = t2.getEndDate().orElse(LocalDateTime.MAX);
		
		int dateCompare = t1EndDate.compareTo(t2EndDate);
		
		if (dateCompare == 0) {
			return t1.getName().value.compareTo(t2.getName().value);
		}
		else {
			return dateCompare;
		}	
	}

}
