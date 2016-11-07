package seedu.toDoList.logic.commands;

import static seedu.toDoList.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toDoList.commons.core.Messages;
import seedu.toDoList.commons.core.UnmodifiableObservableList;
import seedu.toDoList.commons.exceptions.IllegalValueException;
import seedu.toDoList.model.task.ReadOnlyTask;

//@@author A0138717X

/**
 * Edit a task identified using its last displayed index or name from the toDoList.
 */

public class EditCommand extends Command {

	public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + "Edit a field of a event/task in the toDoList.\n"
            + "Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL] or TASK_NAME [d/DEADLINE] [r/RECURRING_TASK] [p/PRIORITY_LEVEL]\n"
            + "Examples: " + COMMAND_WORD
            + " CS3230 Lecture s/14.10.2016-10 \n"
            + COMMAND_WORD
            + "CS3230 Lecture e/14.10.2016-12 \n";

    public static final String MESSAGE_TASK_NOT_IN_LIST = "This event/task is not found in the toDoList";
    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace
    public static final String MESSAGE_EDIT_SAME_NAME="Please select the item identified "
    		+ "by the index number.\n"+"Parameters: EVENT_NAME [s/START_DATE] [e/END_DATE] [r/RECURRING_EVENT] [p/PRIORITY_LEVEL] [i/INDEX(must be a positive integer)]\n"
    		+"Example: "+COMMAND_WORD + " CS3230 Lecture e/14.10.2016-12 i/1";
    public static final String MESSAGE_EVENT_SUCCESS = "This event has been edited: %1$s";
    public static final String MESSAGE_TASK_SUCCESS = "This task has been edited: %1$s";
    public static final String MESSAGE_IS_NOT_A_EVENT = "This is not a event in the toDoList";
    public static final String EDIT_TYPE_START_DATE = "startDate";
    public static final String EDIT_TYPE_END_DATE = "endDate";

    private String name;
    private String type;
    private String details;
    private int targetIndex;

    /**
     * construct EditCommand by name. Precondition: name is not null
     *
     * @param name, type, details.
     */
    public EditCommand(String name, String type, String details) {
    	assert name != null;
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = -1;
    }

    /**
     * construct EditCommand by index. Precondition: index is a valid non-negative integer.
     *
     * @param name, type, details, index.
     */
    public EditCommand(String name, String type, String details, int index) {
    	assert targetIndex >= 0;
    	this.name = name;
    	this.type = type;
    	this.details = details;
    	this.targetIndex = index;
    }

	@Override
	public CommandResult execute() {
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
            toEdit = prepareEditTaskByName(lastShownList);
            if(toEdit == null) {
            	return prepareEditTaskWithName();
            }
        }
        return editTask(toEdit);
	}

    /**
     * return the task to be edited found by targetIndex
     *
     * @param lastShownList
     * @return task to be edited
     */
    private ReadOnlyTask prepareEditTaskbyIndex(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
        return lastShownList.get(targetIndex - 1);
    }

    /**
     * return the task to be edited found by the exact name
     *
     * @param lastShownList
     * @return task to be edited
     */
    private ReadOnlyTask prepareEditTaskByName(UnmodifiableObservableList<ReadOnlyTask> lastShownList) {
    	final ArrayList<ReadOnlyTask> shownList = new ArrayList<ReadOnlyTask>();
    	for(ReadOnlyTask task : lastShownList) {
        	if(name.trim().equals(task.getName().taskName))
        		shownList.add(task);
        }
    	if(shownList.size() == 1) {
    		return shownList.get(0);
    	}
    	return null;
    }

    /**
     * shown all task names with one or more occurrences of the input parameters
     *
     * @return commandResult
     */
    private CommandResult prepareEditTaskWithName() {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(name.trim());
        if (!matcher.matches()) {
        	indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
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
        	if(type.equals(EDIT_TYPE_START_DATE) || type.equals(EDIT_TYPE_END_DATE)) {
        		if(toEdit.isEvent())
        		{
        			model.editTask(toEdit, type, details);
        		}
        		else
        		{
        			indicateAttemptToExecuteIncorrectCommand();
        			return new CommandResult(MESSAGE_IS_NOT_A_EVENT);
        		}
        	}
        	else
        		model.editTask(toEdit, type, details);
	        String message = String.format(getSuccessMessage(toEdit), toEdit);
	        model.saveState(message);
	        return new CommandResult(message);
		} catch (IllegalValueException e) {
			indicateAttemptToExecuteIncorrectCommand();
			return new CommandResult(MESSAGE_TASK_NOT_IN_LIST);
		}
    }

    /**
     * return the correct edit success message depending on the whether it is
     * task or event
     *
     * @param toEdit
     * @return String
     */
	public static String getSuccessMessage(ReadOnlyTask toEdit) {
        if (toEdit.isEvent()) {
            return MESSAGE_EVENT_SUCCESS;
        } else {
            return MESSAGE_TASK_SUCCESS;
        }
    }

}
