package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


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

    private final Task toAdd;
    
    //@@author A0141019U    
    public AddCommand(String name, String taskType, Optional<LocalDateTime> startDate, Optional<LocalDateTime> endDate, Set<String> tags) throws IllegalValueException {
    	final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
    	
    	this.toAdd = new Task(
        		new Name(name),
        		new TaskType(taskType),
        		new Status("pending"), 
        		startDate, 
        		endDate,
        		new UniqueTagList(tagSet)
         );
    	
    	System.out.println(tagSet);
    }
    
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
        	model.undoSaveState();
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
