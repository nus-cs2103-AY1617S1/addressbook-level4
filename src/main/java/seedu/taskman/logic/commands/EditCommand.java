package seedu.taskman.logic.commands;

import seedu.taskman.commons.exceptions.IllegalValueException;
import seedu.taskman.model.event.Activity;

import java.util.Set;

/**
 * Edits an existing task
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    // kiv: let parameters be objects. we can easily generate the usage in that case
    // todo: update message
    // UG/DG
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits an existing task. "
            + "Parameters: INDEX [TITLE] [d/DEADLINE] [f/FREQUENCY] [s/SCHEDULE] [c/STATUS] [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " 1 CS2103T Tutorial d/fri 11.59pm f/1w s/mon 2200 to tue 0200 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Task updated: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "A task with the same name already exists in the task man";

    private Activity beforeEdit;
    private Activity afterEdit;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetIndex, String title, String deadline, String status, String frequency, String schedule, Set<String> tags)
            throws IllegalValueException {

    }

    @Override
    public CommandResult execute() {
        assert model != null;

        return null;
    }

}
