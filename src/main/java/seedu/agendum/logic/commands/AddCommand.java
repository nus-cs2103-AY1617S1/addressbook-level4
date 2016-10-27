package seedu.agendum.logic.commands;

import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Adds a task to the to do list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String COMMAND_FORMAT = "add <name> \nadd <name> by <deadline> \nadd <name> from <start-time> to <end-time>";
    public static final String COMMAND_DESCRIPTION = "adds a task to Agendum";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task with no time and date. \n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD
            + " Watch Star Wars t/movies";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";

    private Task toAdd = null;

	//@@author A0003878Y
    /**
     * Convenience constructor using name
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name)
        );
    }

    /**
     * Convenience constructor using name, end datetime
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<LocalDateTime> deadlineDate)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                deadlineDate
        );
    }

    /**
     * Convenience constructor using name, start datetime, end datetime
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<LocalDateTime> startDateTime, Optional<LocalDateTime> endDateTime)
            throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                startDateTime,
                endDateTime
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

    public static String getName() {
        return COMMAND_WORD;
    }

    public static String getFormat() {
        return COMMAND_FORMAT;
    }

    public static String getDescription() {
        return COMMAND_DESCRIPTION;
    }

}

