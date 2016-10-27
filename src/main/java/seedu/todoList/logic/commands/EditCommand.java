package seedu.todoList.logic.commands;

import seedu.todoList.commons.core.Messages;
import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.todoList.model.task.attributes.*;

/**
 * Edit a task identified using it's last displayed index from the TodoList.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits information of the task in the task-list.\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER name/TASK_NAME from/DATE to/ENDDATE(Optional) p/PRIORITY\n"
            + "Example: " + COMMAND_WORD + " todo 1 name/Assignment 1 from/25-12-2016 p/2\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER name/TASK_NAME from/DATE to/ENDDATE at/START_TIME to/END_TIME\n"
            + "Example: " + COMMAND_WORD + " event 1 name/Time's birthday party from/25-12-2016 to/26-12-2016 at/1200 to/1600\n"
            + "Parameters: TASK_TYPE INDEX_NUMBER name/TASK_NAME on/DATE at/END_TIME\n"
            + "Example: " + COMMAND_WORD + " deadline 1 name/CS2103 v0.2 on/25-12-2016 at/1400";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited task: %1$s";
    public static final String INVALID_VALUE = "Invalid value";
    public static final String MISSING_TASK = "The target task cannot be missing";
    public static final String MESSAGE_EDIT_DUPLICATE_TASK = "This task already exists in the Task-list";
    
    public final String dataType;
    public final int targetIndex;
    private final Task toEdit;
    ReadOnlyTask taskToEdit = null;
    
    /**
     * Edit Todo
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String name, String date, String endDate, String priority, int targetIndex, String dataType)
            throws IllegalValueException {
    	this.targetIndex = targetIndex;
    	this.dataType = dataType;
        this.toEdit = new Todo(
                new Name(name),
                new StartDate(date),
                new EndDate(endDate),
                new Priority(priority),
                "false"
        );
    }
    
    /**
     * Edit Event
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String name, String date, String endDate, String startTime, String endTime, int targetIndex, String dataType)
            throws IllegalValueException {
    	this.targetIndex = targetIndex;
    	this.dataType = dataType;
        this.toEdit = new Event(
                new Name(name),
                new StartDate(date),
                new EndDate(endDate),
                new StartTime(startTime),
                new EndTime(endTime),
                "false"
        );
    }
    
    /**
     * Edit Deadline
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(String name, String date, String endTime, int targetIndex, String dataType)
            throws IllegalValueException {
    	this.targetIndex = targetIndex;
    	this.dataType = dataType;
        this.toEdit = new Deadline(
                new Name(name),
                new StartDate(date),
                new EndTime(endTime),
                "false"
        );
    }
    
    /**
     * Constructor for undo
     */
    public EditCommand(ReadOnlyTask original, String dataType, ReadOnlyTask toEdit) {
    	this.taskToEdit = original;
    	this.toEdit = (Task) toEdit;
    	this.targetIndex = -1;
    	this.dataType = dataType;
    }

    @Override
    public CommandResult execute() {
    	if(this.taskToEdit == null && this.targetIndex != -1) {
	    	UnmodifiableObservableList<ReadOnlyTask> lastShownList = null;
	    	switch (dataType) {
	    		case "todo":
	    			lastShownList = model.getFilteredTodoList();
	    			break;
	    		case "event":
	    			lastShownList = model.getFilteredEventList();
	    			break;
	    		case "deadline":
	    			lastShownList = model.getFilteredDeadlineList();
	    	}
	        if (lastShownList.size() < targetIndex) {
	            indicateAttemptToExecuteIncorrectCommand();
	            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
	        }
	        
	        taskToEdit = lastShownList.get(targetIndex - 1);
    	}
        
        assert model != null;
        try {
            model.editTask(taskToEdit, dataType, toEdit);
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, toEdit));
        } catch (IllegalValueException ive) {
        	return new CommandResult(INVALID_VALUE);
        }catch (TaskNotFoundException pnfe) {
            return new CommandResult(MISSING_TASK);
        }
    }
}
