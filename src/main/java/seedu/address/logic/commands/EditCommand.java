//@@author A0147995H
package seedu.address.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;
import seedu.address.model.task.RecurringType;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskOcurrence;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.task.UniqueTaskList.TimeslotOverlapException;

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";
	
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": edit a specific task (specified by its index) "
            + "Parameters: TASK_INDEX [NEW_TASK_NAME]"
			+ "[from DATE to DATE | by DEADLINE]"
            + "[t/TAGS]\n"
            + "Example: " + COMMAND_WORD + " 1 a task by today 9pm";
	
	public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edit Task: %1$s";
	public static final String MESSAGE_TIMESLOT_OCCUPIED = "This timeslot is already blocked or overlapped with existing tasks.";
	public static final String MESSAGE_ILLEGAL_TIME_SLOT = "End time must be later than Start time.";
	
	private final Name taskName;
	private final UniqueTagList tags;
	private final TaskDate startDate;
	private final TaskDate endDate;
	private final int targetIndex;
	private final RecurringType recurringType;
	
	private Name constructName(String taskName) throws IllegalValueException {
		if (taskName.isEmpty())
			return null;
		return new Name(taskName);
	}
	
	private UniqueTagList constructTagList(Set<String> tags) throws IllegalValueException {
		if(tags.size() > 0) {
			final Set<Tag> tagSet = new HashSet<>();
	        for (String tagName : tags) {
				tagSet.add(new Tag(tagName));
	        }
	        return new UniqueTagList(tagSet);
		}
		return null;	
	}
	
	private TaskDate constructTaskDate(Date date) {
		if(date != null) {
			return new TaskDate(date.toString());
		}
		return null;
	}
	
	public EditCommand(int targetIndex, String taskName, Set<String> tags, Date startDate, Date endDate, RecurringType recurringType) throws IllegalValueException {
		this.targetIndex = targetIndex;
		this.taskName = constructName(taskName);
		this.tags = constructTagList(tags);
		this.startDate = constructTaskDate(startDate);
		this.endDate = constructTaskDate(endDate);
		this.recurringType = recurringType;
	}


	@Override
	public CommandResult execute() {
		UnmodifiableObservableList<TaskOcurrence> lastShownList = model.getFilteredTaskComponentList();

		if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            urManager.popFromUndoQueue();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
		
		if (startDate != null && endDate != null && startDate.getDateInLong() > endDate.getDateInLong()) {
			return new CommandResult(MESSAGE_ILLEGAL_TIME_SLOT);
		}
		
		TaskOcurrence taskToEdit = lastShownList.get(targetIndex - 1);
		Task targetTask = (Task) taskToEdit.getTaskReference();
		try {
			model.editTask(targetTask, taskName, tags, startDate, endDate, recurringType);
			CommandResult result = new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, targetTask));
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
            return result;
		} catch (TaskNotFoundException e) {
			assert false : "The target task cannot be missing";
			return null;
		} catch (TimeslotOverlapException e) {
			indicateAttemptToExecuteFailedCommand();
			urManager.popFromUndoQueue();
			return new CommandResult(MESSAGE_TIMESLOT_OCCUPIED);
		}
	}
}
//@@author
