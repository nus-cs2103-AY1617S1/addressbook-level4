package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Comparator;

//@@author A0141019U
public class TaskDateComparator implements Comparator<ReadOnlyTask> {
	
	public TaskDateComparator() {}
	
	// Sorts first by start date, then end date, and last by alphabetical order of task name
	@Override
	public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
		LocalDateTime t1Date = t1.getStartDate().orElse(t1.getEndDate().orElse(LocalDateTime.MAX));
		LocalDateTime t2Date = t2.getStartDate().orElse(t2.getEndDate().orElse(LocalDateTime.MAX));
		
		int dateCompare = t1Date.compareTo(t2Date);
		
		if (dateCompare == 0) {
			return t1.getName().value.compareTo(t2.getName().value);
		}
		else {
			return dateCompare;
		}	
	}

}
