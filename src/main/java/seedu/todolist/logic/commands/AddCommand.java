package seedu.todolist.logic.commands;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.model.task.*;

/**
 * Adds a task to the to-do list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to Task!t. "
            + "Parameters: NAME [from DATETIME to DATETIME]/[by DATETIME] [at LOCATION] [remarks REMARKS]\n"
            + "Example 1: " + COMMAND_WORD
            + " dinner with mom from 12 oct 2016 7pm to 12 oct 2016 8pm at home remarks buy flowers \n"
            + "Example 2: " + COMMAND_WORD
            + " submit proposal by 25/11/2016 23:59";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in Task!t";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    //@@author A0138601M
    public AddCommand(String name, String startDate, String startTime, String endDate, String endTime,
            String location, String remarks)  throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new Interval(startDate, startTime, endDate, endTime),
                new Location(location),
                new Remarks(remarks),
                new Status(false)
        );
    }
    //@@author
    
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
