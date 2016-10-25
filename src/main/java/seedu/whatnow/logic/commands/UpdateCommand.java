package seedu.whatnow.logic.commands;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.Name;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.TaskDate;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Update a task with new description/date/time/tag using it's last displayed index from WhatNow.
 */

public class UpdateCommand extends UndoAndRedo {


	public static final String COMMAND_WORD = "update";

	public static final String MESSAGE_USAGE = COMMAND_WORD 
			+ ": Updates the description/date/time/tag of the task identified by the index number used in the last task listing.\n"
			+ "Parameters: todo/schedule INDEX (must be a positive integer) description/date/time/tag DESCRIPTION/DATE/TIME/TAG\n"
			+ "Example: " + COMMAND_WORD + " todo 1 tag priority low";

	public static final String MESSAGE_UPDATE_TASK_SUCCESS = "Updated Task: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in WhatNow";

	private static final String ARG_TYPE_DESCRIPTION = "description";
	private static final String ARG_TYPE_DATE = "date";
	private static final String ARG_TYPE_TAG = "tag";
	private static final String DELIMITER_BLANK_SPACE = " ";
	private static final String TASK_TYPE_TODO = "todo";
	private static final String TASK_TYPE_FLOATING = "floating";
	private static final String TASK_TYPE_NOT_FLOATING = "not_floating";
	private static final String REMOVE = "none";

	public final int targetIndex;
	public final String taskType;
	public final String arg_type;
	public final String arg;
	private Task toUpdate;

	public UpdateCommand(String taskType, int targetIndex, String arg_type, String arg) throws IllegalValueException {
		this.taskType = taskType;
		this.targetIndex = targetIndex;
		this.arg_type = arg_type;
		this.arg = arg;
		processArg();
	}

	/**
	 * Processes the arguments in the update command
	 *
	 * @throws IllegalValueException if any of the raw values are invalid
	 */
	private void processArg() throws IllegalValueException {
		String newName = "a";
		String date = null;
		final Set<Tag> tagSet = new HashSet<>();
		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == 0) {
			if (arg.toUpperCase().compareToIgnoreCase(REMOVE) != 0) {
				Set<String> tags = processTag();
				for (String tagName : tags) {
					tagSet.add(new Tag(tagName));
				}
			}   
		}

		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == 0) {
			date = (arg.toUpperCase().compareToIgnoreCase(REMOVE) == 0) ? null : arg;
		}

		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == 0) {
			newName = arg;
		}

		toUpdate = new Task(new Name(newName), date, null, null, null, null, null, new UniqueTagList(tagSet), null, null);
		//toUpdate = (date == null) ? new Task(new Name(newName), new UniqueTagList(tagSet), null) : new Task(new Name(newName), new TaskDate(date), new UniqueTagList(tagSet), null);
		if (date == null)
			toUpdate.setTaskType(TASK_TYPE_FLOATING);
		else
			toUpdate.setTaskType(TASK_TYPE_NOT_FLOATING);
	}

	/**
	 * Processes the tags in the update command
	 */
	private Set<String> processTag() {
		if (arg.isEmpty()) {
			return Collections.emptySet();
		}
		final Collection<String> tagStrings = Arrays.asList(arg.split(DELIMITER_BLANK_SPACE));
		return new HashSet<>(tagStrings);
	}

	private void updateTheCorrectField(ReadOnlyTask taskToUpdate) {
		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_TAG) == 0) {
			toUpdate.setName(taskToUpdate.getName());
			toUpdate.setTaskDate(taskToUpdate.getTaskDate());
			toUpdate.setStatus(taskToUpdate.getStatus());
			toUpdate.setTaskType(taskToUpdate.getTaskType());
		}
		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DESCRIPTION) == 0) {
			toUpdate.setTags(taskToUpdate.getTags());
			toUpdate.setTaskDate(taskToUpdate.getTaskDate());
			toUpdate.setStatus(taskToUpdate.getStatus());
			toUpdate.setTaskType(taskToUpdate.getTaskType());
		}
		if (arg_type.toUpperCase().compareToIgnoreCase(ARG_TYPE_DATE) == 0) {
			toUpdate.setName(taskToUpdate.getName());
			toUpdate.setTags(taskToUpdate.getTags());
			toUpdate.setStatus(taskToUpdate.getStatus());
		}
		toUpdate.setStatus(taskToUpdate.getStatus());
	}

	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<ReadOnlyTask> lastShownList;

		if (taskType.equals(TASK_TYPE_TODO) || taskType.equalsIgnoreCase(TASK_TYPE_FLOATING)) {
			lastShownList = model.getCurrentFilteredTaskList();
		} else {
			lastShownList = model.getCurrentFilteredScheduleList();
		}

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);
		updateTheCorrectField(taskToUpdate);

		try {
			model.updateTask(taskToUpdate, toUpdate);
			model.getOldTask().push(taskToUpdate);
			model.getNewTask().push(toUpdate);
		} catch (TaskNotFoundException tnfe) {
			assert false : "The target task cannot be missing";
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}
		model.getUndoStack().push(this);
		return new CommandResult(String.format(MESSAGE_UPDATE_TASK_SUCCESS, "\nFrom: " + taskToUpdate + " \nTo: " + toUpdate));
	}

	//@@author A0139128A
	@Override
	public CommandResult undo() throws TaskNotFoundException {
		assert model != null;
		if(model.getOldTask().isEmpty() && model.getNewTask().isEmpty()) {
			return new CommandResult(String.format(UndoCommand.MESSAGE_FAIL));
		}
		else {
			ReadOnlyTask originalTask = model.getOldTask().pop();
			ReadOnlyTask unwantedTask = model.getNewTask().pop();
			model.getOldTask().push(unwantedTask);
			model.getNewTask().push(originalTask);
			try {
				model.updateTask(unwantedTask, (Task) originalTask);
			} catch(UniqueTaskList.DuplicateTaskException e) {
				return new CommandResult(UndoCommand.MESSAGE_FAIL);
			}
			return new CommandResult(UndoCommand.MESSAGE_SUCCESS); 
		}
	}

	//@@author A0139128A
	@Override
	public CommandResult redo() throws TaskNotFoundException {
		assert model != null;
		if(model.getOldTask().isEmpty() && model.getNewTask().isEmpty()) {
			return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
		}
		else {
			ReadOnlyTask originalTask = model.getNewTask().pop();
			ReadOnlyTask wantedTask = model.getOldTask().pop();
			model.getOldTask().push(originalTask);
			model.getNewTask().push(wantedTask);
			try {
				model.updateTask(originalTask, (Task) wantedTask);
			} catch (UniqueTaskList.DuplicateTaskException e) {
				return new CommandResult(RedoCommand.MESSAGE_FAIL);
			}
			return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
		}
	}
}