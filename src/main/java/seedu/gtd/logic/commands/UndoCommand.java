//@@author A0146130W

package seedu.gtd.logic.commands;

/**
 * Deletes a task identified using it's last displayed index from the address book.
 */
public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Un-does the most recent modification of the task list.";
    public static final String MESSAGE_SUCCESS = "Undo change";
    public static final String MESSAGE_UNDO_LIMIT_REACHED = "Undo limit reached!";

    public UndoCommand() {}

    @Override
    public CommandResult execute() {
        if(model.undoAddressBookChange()) return new CommandResult(MESSAGE_SUCCESS);
        else return new CommandResult(MESSAGE_UNDO_LIMIT_REACHED);
    }

}