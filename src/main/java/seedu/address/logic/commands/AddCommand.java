package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.model.task.*;
import seedu.address.storage.Storage;
import seedu.address.ui.TaskListPanel;

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
    
    private final Task toAdd;
//    private Storage storage;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String date, String start, String end, String recur)
            throws IllegalValueException {
        
        Time startTime = new Time(start);
        Time endTime = new Time(end);
        
        if(!Time.checkOrderOfDates(startTime, endTime)) {
        	throw new IllegalValueException(MESSAGE_INVALID_START_AND_END_TIME);
        }
        
        if(!Time.taskTimeisAfterCurrentTime(start) || !Time.taskTimeisAfterCurrentTime(end)) {
        	throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
        
        this.toAdd = new Task(
                new Name(name),
                new Done(false),
                startTime,
                endTime,
                new Recurrence(recur)
        );
    }

	@Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.saveToHistory();
            model.addTask(toAdd);
            model.updateFilteredTaskListToShow(isNotDone());
//            System.out.println("Size: " + new LogicManager(model, storage).getFilteredTaskList().size());
//            TaskListPanel panel = new TaskListPanel();
//            panel.scrollTo(new LogicManager(model, storage).getFilteredTaskList().size());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
	}
    
    
    public static Predicate<Task> isNotDone() {
    	return t -> t.getDone().value == false;
    }
}
