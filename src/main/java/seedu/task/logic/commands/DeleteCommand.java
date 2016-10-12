package seedu.task.logic.commands;

/**
 * Deletes an item identified using it's last displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "-t : Deletes an existing task in the TaskBook.\n"
            + COMMAND_WORD + "-e : Deletes an existing event in the TaskBook.\n"
            + "Parameters: DELETE_TYPE + INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " -e" + " 1";

    public int targetIndex;


    @Override
    public abstract CommandResult execute();

}
