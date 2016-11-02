package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.UniqueTagList;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event/ deadline/ task to be done someday to the task manager.\n"
            + "Event Parameters: event 'NAME' from hh:mm to hh:mm on dd-mm-yy\n"
            + "Event Example: " + COMMAND_WORD
            + " event 'dinner with wife' from 19:00 to 21:00 on 25-12-16\n"
            + "Deadline Parameters: deadline 'NAME' by hh:mm dd-mm-yy\n"
            + "Deadline Example: " + COMMAND_WORD
            + " deadline 'lab report' by 16:00 03-03-15\n"
            + "Task to Be Done Someday Parameters: someday 'NAME'\n"
            + "Task to Be Done Someday Example: " + COMMAND_WORD
            + " someday 'water the plants'";
    
    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    //@@author A0143756Y
    public static final String MESSAGE_START_DATE_TIME_AFTER_END_DATE_TIME = 
    		"Start of event is scheduled after end of event. Please re-enter correct start and end dates/times.\n";
    public static final String MESSAGE_START_DATE_TIME_EQUALS_END_DATE_TIME =
    		"Start of event equals end of event. Please re-enter correct start and end dates/times.\n";
    //@@author
    private final Task toAdd;
    
    //@@author A0141019U
    private TaskType inferTaskType(Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate) throws IllegalValueException {
    	if (startDate.isPresent() && endDate.isPresent()) {
    		return new TaskType("event");
    	}
    	else if (!startDate.isPresent() && endDate.isPresent()) {
    		return new TaskType("deadline");
    	}
    	else if (!startDate.isPresent() && !endDate.isPresent()) {
    		return new TaskType("someday");
    	}
    	else {
    		throw new IllegalValueException("If start date is present, end date must be present too.");
    	}
    }
    
    public AddCommand(String name, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate) throws IllegalValueException {
       	this.toAdd = new Task(
        		new Name(name),
        		inferTaskType(startDate, endDate),
        		new Status("pending"), 
        		startDate, 
        		endDate,
        		new UniqueTagList()
                );
    }
    //@@author
    
    /**
     * Convenience constructor for event task using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    // TODO allow tag list as params
    public AddCommand(String name, LocalDateTime startDate, LocalDateTime endDate) throws IllegalValueException {
       	this.toAdd = new Task(
        		new Name(name),
        		new TaskType("event"),
        		new Status("pending"), 
        		Optional.of(startDate), 
        		Optional.of(endDate),
        		new UniqueTagList()
                );
    }
    
    /**
     * Convenience constructor for deadline task using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, LocalDateTime endDate) throws IllegalValueException {
    	this.toAdd = new Task(
        		new Name(name),
        		new TaskType("deadline"),
        		new Status("pending"), 
        		Optional.empty(), 
        		Optional.of(endDate),
        		new UniqueTagList()
                );
    	System.out.println("deadline added: " + toAdd);
    }
    
    /**
     * Convenience constructor for someday task using raw values
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name) throws IllegalValueException {
    	this.toAdd = new Task(
        		new Name(name),
        		new TaskType("someday"),
        		new Status("pending"), 
        		Optional.empty(), 
        		Optional.empty(),
        		new UniqueTagList()
                );
    }
    
    /**
     * Copy constructor.
     */
    public AddCommand(ReadOnlyTask source) {
        this.toAdd = new Task(source);
    }


    @Override
    public CommandResult execute() {
    	assert model != null;
        try {
        	model.saveState();
            model.addTask(toAdd);
            model.checkForOverdueTasks();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
        	// If adding was unsuccessful, then the state should not be saved - no change was made.
        	// TODO avoid undo pushing state into redo stack
        	model.loadPreviousState();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
