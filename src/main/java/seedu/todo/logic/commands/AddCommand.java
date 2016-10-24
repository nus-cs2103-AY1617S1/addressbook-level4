package seedu.todo.logic.commands;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.task.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to do list. "
            + "Parameters: TASKNAME on STARTDATE by ENDDATE ; DETAILS...\n"
            + "Example: " + COMMAND_WORD
            + " get groceries on 10/10/2016 by 11/10/2016 ; bread, fruits, cinnamon powder, red pepper";

    public static final String MESSAGE_SUCCESS = "New task added! Name : %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to do list";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String detail, String fromDate, String tillDate)
            throws IllegalValueException {
        
        this.toAdd = new Task(
                new Name(name),
                new Detail(detail),
                new TaskDate(fromDate),
                new TaskDate(tillDate)
        );
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }

}
