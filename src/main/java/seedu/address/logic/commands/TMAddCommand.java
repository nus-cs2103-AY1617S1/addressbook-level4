package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a task to the address book.
 */
public class TMAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE_ALL = COMMAND_WORD + ": Adds a task to the task manager.\n"
            + "add event: adds an event task to the task manager. "
    		+ "Format: add event 'NAME from hh:mm to hh:mm on dd-mm-yy\n"
            + "Example: add event 'dinner with wife' on 25-12-16 from 7:00pm to 9:00pm\n"
    		+ "add deadline: adds a deadline task to the task manager. "
            + "Format: add deadline 'NAME' by hh:mm dd-mm-yy\n"
    		+ "Example: add deadline 'Lab Report' by 16:00 on 03-Mar-15\n"
    		+ "add someday: adds a someday task to the task manager. "
            + "Format: add someday 'NAME'\n"
    		+ "Example: add someday 'talk to Bill Gates'\n";
    
    public static final String MESSAGE_USAGE_EVENT = "add event: adds an event task to the task manager. "
    		+ "Format: add event 'NAME from hh:mm to hh:mm on dd-mm-yy\n"
            + "Example: add event 'dinner with wife' on 25-12-16 from 7:00pm to 9:00pm\n";
    
    public static final String MESSAGE_USAGE_DEADLINE = "add deadline: adds a deadline task to the task manager. "
            + "Format: add deadline 'NAME' by hh:mm dd-mm-yy\n"
    		+ "Example: add deadline 'Lab Report' by 16:00 on 03-Mar-15\n";
    
    public static final String MESSAGE_USAGE_SOMEDAY = "add someday: adds a someday task to the task manager. "
            + "Format: add someday 'NAME'\n"
    		+ "Example: add someday 'talk to Bill Gates'\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final TMTask toAdd;

    /**
     * constructors an event task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    // TODO allow tag list as params
    public TMAddCommand(String name, String startDate, String endDate)
            throws IllegalValueException {
        this.toAdd = new TMTask(
        		new Name(name), 
        		new Status(), 
        		new Date(startDate), 
        		new Date(endDate),
        		new UniqueTagList()
                );
    }
    
    /**
     * constructors a deadline task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public TMAddCommand(String name, String endDate) throws IllegalValueException {
        this.toAdd = new TMTask(
        		new Name(name), 
        		new Status(), 
        		new Date(endDate),
        		new UniqueTagList()
                );
    }
    
    /**
     * constructors a someday task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public TMAddCommand(String name) throws IllegalValueException {
        this.toAdd = new TMTask(
                new Name(name),
                new Status(),
                new UniqueTagList()
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
