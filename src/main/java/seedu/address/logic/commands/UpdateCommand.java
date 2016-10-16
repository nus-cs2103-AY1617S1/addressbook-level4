package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;

import java.util.HashSet;
import java.util.Set;

/**
 * update the details of a task.
 */
public class UpdateCommand extends Command{

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": update the details of an existing task.\n"
            + "Parameters: INDEX (must be a positive integer) PROPERTY INFOMATION\n"
            + "Example: " + COMMAND_WORD + " 1" + " priority"+" high";

    public static final String MESSAGE_EDIT_SUCCESS = "Edit successfully: %1$s";
    public static final String MESSAGE_EDIT_FAIL = "Editing failed";
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should either be in 24H format or given as a Day of the Week\n"
          + "Eg. 9:11, 09:11, thursday, Thursday, THURSDAY, thu, Thur, THURS";

    public final int targetIndex;
    public final String property;
    public final String info;

    public UpdateCommand(int targetIndex, String property, String info) throws IllegalValueException {
        this.targetIndex = targetIndex;;
        this.property = property;
        this.info = info;
    }

	@Override
    public CommandResult execute() {
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUpdate = lastShownList.get(targetIndex - 1);

        String description;
        String priority;
        Time timeStart;
        Time timeEnd;
        UniqueTagList tags;

			switch(property){
				case "description":
					description = info;
					timeStart = taskToUpdate.getTimeStart();
					timeEnd = taskToUpdate.getTimeEnd();
					priority = taskToUpdate.getPriority().toString();
					tags =taskToUpdate.getTags();
					break;
				case "start":
					try {
						timeStart = new Time(info);
					} catch (IllegalValueException e1) {
						return new CommandResult(MESSAGE_TIME_CONSTRAINTS);
					}
					description = taskToUpdate.getDescription().toString();
					timeEnd = taskToUpdate.getTimeEnd();
					priority = taskToUpdate.getPriority().toString();
					tags =taskToUpdate.getTags();
					break;
				case "priority":
					priority = info;
					description = taskToUpdate.getDescription().toString();
					timeEnd = taskToUpdate.getTimeEnd();
					timeStart = taskToUpdate.getTimeStart();
					tags =taskToUpdate.getTags();
					break;
				case "end":
					try {
						timeEnd = new Time(info);
					} catch (IllegalValueException e1) {
						return new CommandResult(MESSAGE_TIME_CONSTRAINTS);
					}
					description = taskToUpdate.getDescription().toString();
					priority = taskToUpdate.getPriority().toString();
					timeStart = taskToUpdate.getTimeStart();
					tags =taskToUpdate.getTags();
					break;
				default: return new CommandResult(MESSAGE_EDIT_FAIL);
		}

		DeleteCommand delete = new DeleteCommand(targetIndex);
        delete.model = model;
		delete.execute();
		AddCommand add;
		try {
			add = new AddCommand(description, priority, timeStart, timeEnd, tags, targetIndex-1);
			add.model = model;
			add.insert();
		} catch (IllegalValueException e) {
			return new CommandResult(String.format(MESSAGE_EDIT_FAIL));
		}
		SelectCommand select = new SelectCommand(targetIndex);
		select.model = model;
		select.execute();
        return new CommandResult(String.format(MESSAGE_EDIT_SUCCESS, taskToUpdate));
	}
}
