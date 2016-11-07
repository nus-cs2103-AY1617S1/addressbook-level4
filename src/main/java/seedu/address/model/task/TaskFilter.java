package seedu.address.model.task;

import java.util.function.Predicate;

import java.time.LocalDateTime;

/**
 * Provides different filters for filtering the lists of tasks
 */
//@@author A0142184L
public class TaskFilter {

	public static Predicate<Task> isTodayTask() {
		return p -> p.getTaskType().isEventTask() &&
				    p.getStartDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate()) ||
				    p.getTaskType().isDeadlineTask() &&
				    p.getEndDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate());
	}
	
	public static Predicate<Task> isTomorrowTask() {
		return p -> p.getTaskType().isEventTask() &&
				    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate()) &&
				    p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate()) ||
				    p.getTaskType().isDeadlineTask() &&
				    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate()) &&
				    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate());
	}
	
	public static Predicate<Task> isIn7DaysTask() {
		return p -> p.getTaskType().isEventTask() &&
				    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
			        p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate()) ||
				    p.getTaskType().isDeadlineTask() &&
				    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
				    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate());
	}
	
	public static Predicate<Task> isIn30DaysTask() {
		return p -> p.getTaskType().isEventTask() &&
				    p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
				    p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate()) ||
				    p.getTaskType().isDeadlineTask() &&
				    p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate()) &&
				    p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate());
	}
	
	public static Predicate<Task> isSomedayTask() {
		return p -> p.getTaskType().isSomedayTask();
	}
	
	public static Predicate<Task> isDone() {
		return p -> p.getStatus().isDone();
	}
	
	public static Predicate<Task> isPending() {
		return p -> p.getStatus().isPending();
	}

	public static Predicate<Task> isOverdue() {
		return p -> p.getStatus().isOverdue();
	}
	
	public static Predicate<Task> isDeadlineTask() {
		return p -> p.getTaskType().isDeadlineTask();
	}
	
	public static Predicate<Task> isEventTask() {
		return p -> p.getTaskType().isEventTask();
	}
}
