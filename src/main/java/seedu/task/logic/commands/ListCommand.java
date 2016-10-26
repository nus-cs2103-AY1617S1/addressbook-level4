package seedu.task.logic.commands;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    public ListCommand() {}

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author A0153411W
	/**
	 * Execute undo method for list command.
	 * Call execute method for list command.
	 */
	@Override
	public CommandResult executeUndo() {
		return this.execute();
	}


	@Override
	public boolean isReversible() {
		return true;
	}

}
