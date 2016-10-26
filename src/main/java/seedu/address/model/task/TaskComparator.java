package seedu.address.model.task;

import java.time.LocalDateTime;
import java.util.Comparator;

//@@author A0141019U
public class TaskComparator implements Comparator<ReadOnlyTask> {
	
	public TaskComparator() {}
	
	// Sorts first by start date, 
	// then end date,
	// then status,
	// and last by lexicographical order of task name.
	// If start date does not exist for one, end date is compared against the other's start date.
	// If one doesn't have either start or end date, it is given the maximum date value
	@Override
	public int compare(ReadOnlyTask t1, ReadOnlyTask t2) {
		int statusCompare = t1.getStatus().compareTo(t2.getStatus());
		if (statusCompare != 0) {
			return statusCompare;
		}
		else {
			LocalDateTime t1Date = t1.getStartDate().orElse(t1.getEndDate().orElse(LocalDateTime.MAX));
			LocalDateTime t2Date = t2.getStartDate().orElse(t2.getEndDate().orElse(LocalDateTime.MAX));
			
			int dateCompare = t1Date.compareTo(t2Date);
			
			if (dateCompare != 0) {
				return dateCompare;
			}
			else {
				return t1.getName().compareTo(t2.getName());
			}
		}
	}
	
}
