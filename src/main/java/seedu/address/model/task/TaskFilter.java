//@@author A0142184L
package seedu.address.model.task;

import java.util.function.Predicate;

import java.time.LocalDateTime;

public class TaskFilter {

	public static Predicate<Task> isTodayTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE) 
				&& p.getEndDate().get().toLocalDate().equals(LocalDateTime.now().toLocalDate())));
	}
	
	public static Predicate<Task> isTomorrowTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE) 
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(2).toLocalDate())));
	}
	
	public static Predicate<Task> isIn7DaysTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE)
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(7).toLocalDate())));
	}
	
	public static Predicate<Task> isIn30DaysTask() {
		return p -> (p.getTaskType().value.equals(TaskType.Type.EVENT) 
				&& p.getStartDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getStartDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate())
				|| (p.getTaskType().value.equals(TaskType.Type.DEADLINE)
				&& p.getEndDate().get().toLocalDate().isAfter(LocalDateTime.now().minusDays(1).toLocalDate())
				&& p.getEndDate().get().toLocalDate().isBefore(LocalDateTime.now().plusDays(30).toLocalDate())));
	}
	
	public static Predicate<Task> isSomedayTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.SOMEDAY);
	}
	
	public static Predicate<Task> isDone() {
		return p -> p.getStatus().value.equals(Status.DoneStatus.DONE);
	}
	
	public static Predicate<Task> isNotDone() {
		return p -> p.getStatus().value.equals(Status.DoneStatus.NOT_DONE);
	}

	public static Predicate<Task> isOverdue() {
		return p -> p.getStatus().value.equals(Status.DoneStatus.OVERDUE);
	}
	
	public static Predicate<Task> isDeadlineTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.DEADLINE);
	}
	
	public static Predicate<Task> isEventTask() {
		return p -> p.getTaskType().value.equals(TaskType.Type.EVENT);
	}
}
