package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_START_AND_END_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TIME;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;



/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the ForgetMeNot. "
            + "Parameters: TASKNAME DATE"
            + "Example: " + COMMAND_WORD
            + " Homework by tomorrow 6pm";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in ForgetMeNot";
    private static final String DEFAULT_DATE = "Thu Jan 01 07:30:00 SGT 1970";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String start, String end)
            throws IllegalValueException {
        
        Time startTime = new Time(start);
        Time endTime = new Time(end);
        
        if(!Time.checkOrderOfDates(start, end)) {
        	throw new IllegalValueException(MESSAGE_INVALID_START_AND_END_TIME);
        }
        
        if(!Time.taskTimeisAfterCurrentTime(start) || !Time.taskTimeisAfterCurrentTime(end)) {
        	throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
        
        this.toAdd = new Task(
                new Name(name),
                new Done(false),
                startTime,
                endTime
        );
    }

	@Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.saveToHistory();
            model.addTask(toAdd);
            model.updateFilteredTaskListToShow(isNotDone());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}
    
    
    public static Predicate<Task> isNotDone() {
    	return t -> t.getDone().value == false;
    }
}
