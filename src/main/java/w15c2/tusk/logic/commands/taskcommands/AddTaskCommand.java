package w15c2.tusk.logic.commands.taskcommands;

import java.util.Date;

import w15c2.tusk.commons.collections.UniqueItemCollection.DuplicateItemException;
import w15c2.tusk.commons.core.EventsCenter;
import w15c2.tusk.commons.events.ui.JumpToListRequestEvent;
import w15c2.tusk.commons.exceptions.IllegalValueException;
import w15c2.tusk.logic.commands.Command;
import w15c2.tusk.logic.commands.CommandResult;
import w15c2.tusk.model.task.DeadlineTask;
import w15c2.tusk.model.task.EventTask;
import w15c2.tusk.model.task.FloatingTask;
import w15c2.tusk.model.task.Task;

//@@author A0139817U
/**
 * Adds a Floating, Deadline or Event task to the Model.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String ALTERNATE_COMMAND_WORD = null;
    public static final String COMMAND_DESCRIPTION = "Add a Task\nAdd a Deadline\nAdd an Event";
    public static final String COMMAND_FORMAT = "add <description> \n" +
            "add <description> by <date> \n" +
            "add <description> from <startDate> to <endDate>";
    
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to TaskManager. \n"
            + "1) Parameters: DESCRIPTION \n"
            + "Example: " + COMMAND_WORD
            + " Finish V0.1 \n"
            + "2) Parameters: DESCRIPTION by DEADLINE \n"
            + "Example: " + COMMAND_WORD
            + " Finish V0.1 by Oct 31 \n"
            + "3) Parameters: DESCRIPTION from START_DATE to END_DATE \n"
            + "Example: " + COMMAND_WORD
            + " Software Demo from Oct 31 to Nov 1";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in TaskManager";
    public static final String MESSAGE_EMPTY_TASK = "There is no description.\n";
    private static final String MESSAGE_EMPTY_DATE = "There is no date.\n";

    private Task toAdd;

    /**
     * A FloatingTask has only one parameter, description.
     * This AddTaskCommand constructor takes in a description and adds a FloatingTask.
     *
     * @param description 				Description of the task to be added.
     * @throws IllegalValueException 	If description is invalid.
     */
    public AddTaskCommand(String description)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException(MESSAGE_EMPTY_TASK + MESSAGE_USAGE);
    	}
    	this.toAdd = new FloatingTask(description);
    }
    
    /**
     * A DeadlineTask has only two parameters, description and a deadline.
     * This AddTaskCommand constructor takes in a description and a deadline, and adds a DeadlineTask.
     *
     * @param description				Description of the task to be added.
     * @param deadline					Deadline of the task.
     * @throws IllegalValueException 	If description or deadline is invalid.
     */
    public AddTaskCommand(String description, Date deadline)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException(MESSAGE_EMPTY_TASK + MESSAGE_USAGE);
    	}
    	if (deadline == null) {
    		throw new IllegalValueException(MESSAGE_EMPTY_DATE + MESSAGE_USAGE);
    	}
    	this.toAdd = new DeadlineTask(description, deadline);
    }
    
    /**
     * An EventTask has only three parameter, description, startDate and endDate.
     * This AddTaskCommand constructor takes in a description, startDate and endDate, and adds an EventTask.
     *
     * @param description				Description of the task to be added.
     * @param startDate					Start date of the task.
     * @param endDate					End date of the task.
     * @throws IllegalValueException 	If description, start date or end date is invalid.
     */
    public AddTaskCommand(String description, Date startDate, Date endDate)
            throws IllegalValueException {
    	if (description == null || description.isEmpty()) {
    		throw new IllegalValueException(MESSAGE_EMPTY_TASK + MESSAGE_USAGE);
    	}
    	if (startDate == null || endDate == null) {
    		throw new IllegalValueException(MESSAGE_EMPTY_DATE + MESSAGE_USAGE);
    	}
    	this.toAdd = new EventTask(description, startDate, endDate);
    }
    
    /**
     * Retrieve the details of the task (with or without time information) for testing purposes
     */
    public String getTaskDetails(boolean withTime) {
    	return toAdd.getTaskDetails(withTime);
    }
    
    /**
     * Retrieve the task to add
     */
    public Task getTask() {
    	return toAdd;
    }

    /**
     * Adds the prepared task to the Model.
     * 
     * @return CommandResult Result of the execution of the add command.
     */
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.clearTasksFilter();
            
            int indexToScrollTo = model.getCurrentFilteredTasks().lastIndexOf(toAdd);
            raiseScrollTo(indexToScrollTo);
            
            closeHelpWindow();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateItemException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    //@@author A0138978E
    /**
     * Raises an event to scroll to a particular event in the main task list
     * 
     * @param index the index in the list to scroll to
     */
    private void raiseScrollTo(int index) {
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
    }

}
