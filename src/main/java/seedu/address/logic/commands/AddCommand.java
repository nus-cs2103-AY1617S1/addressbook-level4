package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.Name;
import seedu.address.model.item.Priority;
import seedu.address.model.item.Task;
import seedu.address.model.person.UniqueFloatingTaskList.DuplicateFloatingTaskException;

import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item to To-Do List. "
            + "Parameters: {name} rank {priority value}\n"
            + "Example: " + COMMAND_WORD
            + " read Harry Potter and the Akshay rank 1";

    //TODO: Prevent code from breaking later on.
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_SUCCESS = "New item added: %1$s";

    private final FloatingTask toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priorityValue) throws IllegalValueException {
        this.toAdd = new FloatingTask(new Name(name), new Priority(priorityValue));
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        //TODO: Prevent code from breaking    
        try {
            model.addFloatingTask(toAdd);
        } catch (DuplicateFloatingTaskException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

}
