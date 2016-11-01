package seedu.gtd.logic.commands;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Un-does the most recent modification of the task list.";
    public static final String MESSAGE_SUCCESS = "Undo change";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        model.undoAddressBookChange();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}