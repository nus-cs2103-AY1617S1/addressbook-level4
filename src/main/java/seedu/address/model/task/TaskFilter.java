package seedu.address.model.task;

import java.util.function.Predicate;

public class TaskFilter {

	public static Predicate<ReadOnlyTask> isTodayTask() {
		// check today date
	}
	
	public static Predicate<ReadOnlyTask> isTomorrowTask() {
		// check tomorrow date
	}
	
	public static Predicate<ReadOnlyTask> isIn7DaysTask() {
		// check the next 7 days
	}
	
	public static Predicate<ReadOnlyTask> isIn30DaysTask() {
		// check the next 30 days
	}
	
	public static Predicate<ReadOnlyTask> isSomedayTask() {
		return p -> p.getTaskType().equals(TaskType.Type.SOMEDAY);
	}
	
	
}
