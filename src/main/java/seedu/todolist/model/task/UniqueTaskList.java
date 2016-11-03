package seedu.todolist.model.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.paint.Paint;
import seedu.todolist.commons.exceptions.DuplicateDataException;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.CollectionUtil;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.time.LocalDateTime;
import java.util.*;

import com.sun.prism.impl.paint.PaintUtil;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

	/**
	 * Signals that an operation would have violated the 'no duplicates' property of the list.
	 */
	public static class DuplicateTaskException extends DuplicateDataException {
		protected DuplicateTaskException() {
			super("Operation would result in duplicate tasks");
		}
	}

	/**
	 * Signals that an operation targeting a specified task in the list would fail because
	 * there is no such matching task in the list.
	 */
	public static class TaskNotFoundException extends Exception {}

	private final ObservableList<Task> internalList = FXCollections.observableArrayList();

	/**
	 * Constructs empty TaskList.
	 */
	public UniqueTaskList() {}

	/**
	 * Returns true if the list contains an equivalent task as the given argument.
	 */
	public boolean contains(ReadOnlyTask toCheck) {
		assert toCheck != null;
		return internalList.contains(toCheck);
	}

	/**
	 * Adds a task to the list.
	 *
	 * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
	 */
	public void add(Task toAdd) throws DuplicateTaskException {
		assert toAdd != null;
		if (contains(toAdd)) {
			throw new DuplicateTaskException();
		}
		internalList.add(toAdd);
		Collections.sort(internalList);
	}

	/**
	 * Removes the equivalent task(s) from the list.
	 *
	 * @throws TaskNotFoundException if no such task could be found in the list.
	 */
	public boolean remove(ReadOnlyTask... toRemove) throws TaskNotFoundException {
		assert toRemove != null;
		boolean taskFoundAndDeleted = false;
		for (ReadOnlyTask task : toRemove) {
			taskFoundAndDeleted = internalList.remove(task);
			if (!taskFoundAndDeleted) {
				throw new TaskNotFoundException();
			}       
		}      
		return taskFoundAndDeleted;
	}

	//@@author A0138601M
	/**
	 * Marks the equivalent task(s) in the list.
	 *
	 * @throws TaskNotFoundException if no such task could be found in the list.
	 */
	public boolean mark(ReadOnlyTask... toMark) throws TaskNotFoundException {
		assert toMark != null;
		boolean taskFound = false;
		for (ReadOnlyTask task : toMark) {
			taskFound = (internalList.indexOf(task) != -1);
			if (!taskFound) {
				throw new TaskNotFoundException();
			}
			Task taskMarked = new Task(task.getName(), task.getInterval(), task.getLocation(), task.getRemarks(), new Status(true));
			internalList.set(internalList.indexOf(task), taskMarked);
		}
		Collections.sort(internalList);
		return taskFound;
	}
	//@@author

	//@@author A0146682X
	/**
	 * Sends out notifications for tasks
	 * @throws IllegalValueException 
	 */
	public void sendNotifications() throws IllegalValueException {
		Interval temp = null;
		TaskDate actualDate = null;
		TaskTime actualTime = null;
		LocalDateTime timeToNotify = null;
		Task currentTask;
		for (int i=0; i <internalList.size(); i++) {
			currentTask = internalList.get(i);
			if (currentTask.getNotification()!=null) {
				temp = currentTask.getInterval();

				if (temp!=null) {
					if (temp.getStartDate()!=null) {
						actualDate = temp.getStartDate();
						if (temp.getStartTime()!=null) {
							actualTime = temp.getStartTime();
						}
						else actualTime = new TaskTime("00:00");
					}
					else if (temp.getEndDate()!=null){
						actualDate = temp.getEndDate();
						if (temp.getEndTime()!=null) {
							actualTime = temp.getEndTime();
						}
						else actualTime = new TaskTime("23:59");
					}
					//sends out notification if the time is right
					if (actualDate!=null && actualTime!=null) {
						timeToNotify = LocalDateTime.of(actualDate.getDate(), actualTime.getTime()).minusHours(currentTask.getNotification().getBufferTime());
					}
					if (timeToNotify!=null && timeToNotify.isBefore(LocalDateTime.now())) {
						String title = currentTask.getName().toString();
						String message = String.format("is happening %1$s", currentTask.getInterval().toString());
						NotificationType notificationType = NotificationType.NOTICE;
						TrayNotification tray = new TrayNotification(title, message, notificationType);
						tray.setRectangleFill(Paint.valueOf("#F3A473"));
						tray.setAnimationType(AnimationType.POPUP);
						tray.showAndWait();
						currentTask.dismissNotification();
					}
				}
			}
		}
	}

	/**
	 * Sets notification for the equivalent task in the list.
	 */
	public boolean setNotification(ReadOnlyTask toNotify, int bufferTime) throws TaskNotFoundException {
		assert toNotify != null;
		final boolean taskFound = (internalList.indexOf(toNotify) != -1);
		internalList.get(internalList.indexOf(toNotify)).setNotification(bufferTime);
		if (!taskFound) {
			throw new TaskNotFoundException();
		}
		return taskFound;
	}

	/**
	 * Edits the equivalent task in the list.
	 *
	 * @throws TaskNotFoundException if no such task could be found in the list.
	 */
	public boolean edit(ReadOnlyTask toEdit, Task replacement) throws TaskNotFoundException {
		assert toEdit != null;
		final boolean taskFound = (internalList.indexOf(toEdit) != -1);
		internalList.set(internalList.indexOf(toEdit), replacement);
		if (!taskFound) {
			throw new TaskNotFoundException();
		}
		return taskFound;
	}

	public ObservableList<Task> getInternalList() {
		return internalList;
	}

	public FilteredList<Task> getFilteredTaskList(String filter) {
		return internalList.filtered(p -> p.getStatus().toString().equals(filter));
	}

	@Override
	public Iterator<Task> iterator() {
		return internalList.iterator();
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof UniqueTaskList // instanceof handles nulls
						&& this.internalList.equals(
								((UniqueTaskList) other).internalList));
	}

	@Override
	public int hashCode() {
		return internalList.hashCode();
	}
}
