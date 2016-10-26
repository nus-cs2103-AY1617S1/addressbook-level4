package seedu.todoList.logic.commands;

import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * Adds a task to the TodoList.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TodoList.\n"
            + "Parameters: TASK_NAME from/DATE to/ENDDATE(Optional) p/PRIORITY\n"
            + "Example: " + COMMAND_WORD + " Assignment 3 from/25-12-2016 to/26-12-2016 p/1\n"
            + "Parameters: TASK_NAME from/DATE to/ENDDATE(Optional) at/START_TIME to/END_TIME\n"
            + "Example: " + COMMAND_WORD + " Time's birthday party from/25-12-2016 to/26-12-2016 at/14:00 to/16:00\n"
            + "Parameters: TASK_NAME on/DATE at/END_TIME\n"
            + "Example: " + COMMAND_WORD + " CS2103 v0.2 on/25-12-2016 at/16:00\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String INVALID_VALUE = "Invalid value";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Task-list";
        
    private final Task toAdd;

    /**
     * Add Todo
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String endDate ,String priority)
            throws IllegalValueException {      
        this.toAdd = new Todo(
                new Name(name),
                new StartDate(date),
                new EndDate(endDate),
                new Priority(priority)
        );
    }
    
    /**
     * Add Event
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String endDate, String startTime, String endTime)
            throws IllegalValueException {
        this.toAdd = new Event(
                new Name(name),
                new StartDate(date),
                new EndDate(endDate),
                new StartTime(startTime),
                new EndTime(endTime)
        );
    }
    
    /**
     * Add Deadline
     * Convenience constructor using raw values.
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String endTime)
            throws IllegalValueException {
        this.toAdd = new Deadline(
                new Name(name),
                new StartDate(date),
                new EndTime(endTime)
        );
    }
    
    

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicatetaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        } catch (IllegalValueException ive) {
        	return new CommandResult(INVALID_VALUE);
        }

    }

}