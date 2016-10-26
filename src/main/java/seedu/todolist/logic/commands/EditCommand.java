package seedu.todolist.logic.commands;

import seedu.todolist.commons.core.Messages;
import seedu.todolist.commons.core.UnmodifiableObservableList;
import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.Interval;
import seedu.todolist.model.task.Location;
import seedu.todolist.model.task.Name;
import seedu.todolist.model.task.ReadOnlyTask;
import seedu.todolist.model.task.Remarks;
import seedu.todolist.model.task.Status;
import seedu.todolist.model.task.Task;
import seedu.todolist.model.task.TaskDate;
import seedu.todolist.model.task.TaskTime;
import seedu.todolist.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Edits the information of an existing task.
 */

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits a task in the list displayed. "
			+ "Parameters: [index] NAME [from DATETIME] [to DATETIME] [at LOCATION] [remarks REMARKS] \n"
			+ "Example: " + COMMAND_WORD
			+ " 1 dinner with mom from 13 oct 2016 7pm to 13 oct 2016 8pm at home remarks buy fruits";

	public static final String MESSAGE_SUCCESS = "Task edited: %1$s";

	private final int targetIndex;

	private String name;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String location;
	private String remarks;

	public EditCommand(int targetIndex, String name, String startDate, String startTime, String endDate, String endTime,
			String location, String remarks) throws IllegalValueException {
		this.targetIndex = targetIndex;

		this.name = name;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.location = location;
		this.remarks = remarks;
	}

	@Override
	public CommandResult execute() {

		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredAllTaskList();

		if (lastShownList.size() < targetIndex) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
		}

		ReadOnlyTask taskToEdit = lastShownList.get(targetIndex - 1);

		if(name==null) name = taskToEdit.getName().toString();

		Interval originalInterval = taskToEdit.getInterval();

		if (originalInterval != null) {
			TaskDate originalStartDate = originalInterval.getStartDate();
			if(originalStartDate!=null && startDate==null) startDate = originalStartDate.toString();
			TaskTime originalStartTime = originalInterval.getStartTime();
			if(originalStartTime!=null && startTime==null) startTime = originalStartTime.toString();
			TaskDate originalEndDate = originalInterval.getEndDate();
			if(originalEndDate!=null && endDate==null) endDate = originalEndDate.toString();
			TaskTime originalEndTime = originalInterval.getEndTime();
			if(originalEndTime!=null && endTime==null) endTime = originalEndTime.toString();
		}

		Location originalLocation = taskToEdit.getLocation();
		if(originalLocation != null && location==null) location = originalLocation.toString();

		Remarks originalRemarks = taskToEdit.getRemarks();
		if(originalRemarks != null && remarks==null) remarks = originalRemarks.toString();

		Status originalStatus = taskToEdit.getStatus();

		Task replacement;
		try {
			replacement = new Task(
					new Name(name),
					new Interval(startDate, startTime, endDate, endTime),
					new Location(location),
					new Remarks(remarks),
					new Status(originalStatus.toString())
					);
		} catch (IllegalValueException ive) {
			return new CommandResult(String.format(ive.getMessage()));
		}

		try {
			model.editTask(taskToEdit, replacement);
		} catch (TaskNotFoundException pnfe) {
			assert false : "The target task cannot be missing";
		}

		return new CommandResult(String.format(MESSAGE_SUCCESS, replacement));
	}
}
