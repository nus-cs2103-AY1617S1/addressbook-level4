package seedu.address.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Provides different filters for filtering the lists of read only tasks
 */
public class ReadOnlyTaskFilter {
	
	public static Predicate<ReadOnlyTask> isTodayTask() {
		return p -> p.getTaskType().isEventTask() &&
			    p.getStartDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate()) ||
			    p.getTaskType().isDeadlineTask() &&
			    p.getEndDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate());
	}
	
	public static Predicate<ReadOnlyTask> isTomorrowTask() {		
		return p -> p.getTaskType().isEventTask() &&
		    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate()) &&
		    p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate()) ||
		    p.getTaskType().isDeadlineTask() &&
		    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate()) &&
		    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate());
	}
	
	public static Predicate<ReadOnlyTask> isIn7DaysTask() {
		return p -> p.getTaskType().isEventTask() &&
			    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
		        p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate()) ||
			    p.getTaskType().isDeadlineTask() &&
			    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
			    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate());
	}
	
	public static Predicate<ReadOnlyTask> isIn30DaysTask() {
		return p -> p.getTaskType().isEventTask() &&
			    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
			    p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate()) ||
			    p.getTaskType().isDeadlineTask() &&
			    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
			    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate());
	}
	
	public static Predicate<ReadOnlyTask> isSomedayTask() {
		return p -> p.getTaskType().isSomedayTask();
	}
	
	public static Predicate<ReadOnlyTask> isDone() {
		return p -> p.getStatus().isDone();
	}
	
	public static Predicate<ReadOnlyTask> isPending() {
		return p -> p.getStatus().isPending();
	}
	
	public static Predicate<ReadOnlyTask> isOverdue() {
		return p -> p.getStatus().isOverdue();
	}

	public static Predicate<ReadOnlyTask> isDeadlineTask() {
		return p -> p.getTaskType().isDeadlineTask();
	}
	
	public static Predicate<ReadOnlyTask> isEventTask() {
		return p -> p.getTaskType().isEventTask();
	}
	
	//@@author A0139339W
	public static Predicate<ReadOnlyTask> isThisDate(LocalDate date) {
		return p -> p.getTaskType().isEventTask() &&
				    p.getStartDate().get().toLocalDate().equals(date) ||
				    !p.getTaskType().isSomedayTask() &&
				    p.getEndDate().get().toLocalDate().equals(date);
	}
	//@@author
}
