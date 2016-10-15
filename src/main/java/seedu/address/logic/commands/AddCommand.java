package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateFormatter;
import seedu.address.model.Undo;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the scheduler. "
            + "Parameters: TASK_NAME s/START_DATE e/END_DATE at LOCATION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut\n"
            + "Example: " + COMMAND_WORD
            + " Do CS2103 Pretut by 011016\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Tutorial s/011016 e/011016 at NUS COM1-B103\n";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";

    private final Task toAdd;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            CommandHistory.addMutateCmd(new Undo(COMMAND_WORD, 0, toAdd));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }
    }

}
