package seedu.address.model.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Provides different filters for filtering the lists of read only tasks
 */
public class ReadOnlyTaskFilter {
	
	public static Predicate<ReadOnlyTask> isTodayTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE) 
				&& p.getEndDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate())));
	}
	
	public static Predicate<ReadOnlyTask> isTomorrowTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE) 
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate())));
	}
	
	public static Predicate<ReadOnlyTask> isIn7DaysTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE)
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate())));
	}
	
	public static Predicate<ReadOnlyTask> isIn30DaysTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE)
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate())));
	}
	
	public static Predicate<ReadOnlyTask> isSomedayTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.SOMEDAY);
	}
	
	public static Predicate<ReadOnlyTask> isDone() {
		return p -> p.getStatus().value.equals(Status.StatusType.DONE);
	}
	
	public static Predicate<ReadOnlyTask> isPending() {
		return p -> p.getStatus().value.equals(Status.StatusType.PENDING);
	}
	
	public static Predicate<ReadOnlyTask> isOverdue() {
		return p -> p.getStatus().value.equals(Status.StatusType.OVERDUE);
	}

	public static Predicate<ReadOnlyTask> isDeadlineTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.DEADLINE);
	}
	
	public static Predicate<ReadOnlyTask> isEventTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.EVENT);
	}
	
	//@@author A0139339W
	public static Predicate<ReadOnlyTask> isThisDate(LocalDate date) {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) &&
				p.getStartDate().get().toLocalDate().equals(date)) ||
				(!p.getTaskType().value.equals(TaskType.Type.SOMEDAY) &&
				p.getEndDate().get().toLocalDate().equals(date));
	}
	//@@author
}
