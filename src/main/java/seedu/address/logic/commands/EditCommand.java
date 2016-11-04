package seedu.address.logic.commands;
//@@author A0138717X
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + "Edit a field of a event/task in the task manager.\n"
            + "Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL] or TASK_NAME [d/DEADLINE] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]\n"
            + "Examples: " + COMMAND_WORD
            + " CS3230 Lecture s/14.10.2016-10 \n"
            + COMMAND_WORD
            + "CS3230 Lecture e/14.10.2016-12 \n";

    public static final String MESSAGE_DUPLICATE_TASK = "This event/task already exists in the task manager";
    public static final String MESSAGE_TASK_NOT_IN_LIST = "This event/task is not found in the task manager";
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    public static final String MESSAGE_EDIT_SAME_NAME="Please select the Task identified "
    		+ "by the index number.\n"+"Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL] [i/INDEX(must be a positive integer)]\n"
    		+"Example: "+COMMAND_WORD + " CS3230 Lecture e/14.10.2016-12 i/1";
    public static final String MESSAGE_EVENT_SUCCESS = "This event has been edited: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "This task has been edited: %1$s";

    private String name;
    private String type;
    private String details;
    private int targetIndex;

    public EditCommand(String name, String type, String details) {
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = -1;
    }

    public EditCommand(String name, String type, String details, int index) {
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = index;
    }

	@Override
	public CommandResult execute() {
		ReadOnlyTask target = null;
		//@@author A0142325R-reused
		ReadOnlyTask toEdit = null;
		UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        if (targetIndex != -1) {
            if (lastShownList.size() < targetIndex) {
                indicateAttemptToExecuteIncorrectCommand();
                return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }
            toEdit = prepareEditTaskbyIndex(lastShownList);
        } else {
            assert this.name != null;
            return prepareEditTaskWithName();
        } //end if statment to find the target task

        return editTask(toEdit);
	}


    /**
     * return taskToBeEdit found by targetIndex
     *
     * @param lastShownList
     * @return task to be deleted
     */

    private ReadOnlyTask prepareEditTaskbyIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(targetIndex - 1);
    }

    /**
     * shown all task names with one or more occurrences of the input parameters
     *
     * @return commandResult
     */

    private CommandResult prepareEditTaskWithName() {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
        if (!matcher.matches()) {
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        model.updateFilteredTaskList(keywordSet);
        if (model.getFilteredTaskList().size() == 0) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
        } else {
            return new CommandResult(MESSAGE_EDIT_SAME_NAME);
        }
    }

    /**
     * edit the task specified
     *
     * @param toEdit
     * @return CommandResult
     */

    private CommandResult editTask(ReadOnlyTask toEdit) {
        try {
			model.editTask(toEdit, type, details);
	        String message = String.format(getSuccessMessage(toEdit), toEdit);
	        model.saveState(message);
	        return new CommandResult(message);
		} catch (IllegalValueException e) {
			return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
		}
    }

	public static String getSuccessMessage(ReadOnlyTask toEdit) {
        if (toEdit.isEvent()) {
            return MESSAGE_EVENT_SUCCESS;
        } else {
            return MESSAGE_TASK_SUCCESS;
        }
    }

}
