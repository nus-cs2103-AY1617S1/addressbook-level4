package seedu.address.logic.commands.taskcommands;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.collections.UniqueItemCollection;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideHelpRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.task.Task;
import seedu.address.model.task.Description;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;

/**
 * Adds a task to TaskManager.
 */
public class AddTaskCommand extends TaskCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = "Add a task/event/deadline: \t" + "add <description> <time> <date>";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskManager";
    public static final String MESSAGE_EMPTY_TASK = "Description to AddTaskCommand constructor is empty.\n";

    private Task toAdd;

    /**
     * A FloatingTask has only one parameter, description.
     * This AddTaskCommand constructor takes in a description and adds a FloatingTask.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddTaskCommand(String description)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException(MESSAGE_EMPTY_TASK + MESSAGE_USAGE);
    	}
    	this.toAdd = new FloatingTask(new Description(description));
    }
    
    /**
     * A DeadlineTask has only two parameters, description and a deadline.
     * This AddTaskCommand constructor takes in a description and a deadline, and adds a DeadlineTask.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddTaskCommand(String description, Date deadline)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException("Description to AddTaskCommand constructor is empty.");
    	}
    	this.toAdd = new DeadlineTask(description, deadline);
    }
    
    /**
     * An EventTask has only three parameter, description, startDate and endDate.
     * This AddTaskCommand constructor takes in a description, startDate and endDate, and adds an EventTask.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddTaskCommand(String description, Date startDate, Date endDate)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException("Description to AddTaskCommand constructor is empty.");
    	}
    	this.toAdd = new EventTask(description, startDate, endDate);
    }
    
    /**
     * Retrieve the details of the task for testing purposes
     */
    public String getTaskDetails() {
    	return toAdd.toString();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.clearTasksFilter();
            EventsCenter.getInstance().post(new HideHelpRequestEvent());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueItemCollection.DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
