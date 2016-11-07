package jym.manager.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */

//@@author A0116137M
public class SortCommand extends Command {

	public static final String COMMAND_WORD = "sort";

	public static final String MESSAGE_SUCCESS = "srot all persons";

	public static final String MESSAGE_USAGE = COMMAND_WORD + " [ans|desc]";

	private String orderType;

	public SortCommand(String type) {
		this.orderType = type;
	}

	@Override
	public CommandResult execute() {
		model.updateFilteredListToShowAll(orderType);
		return new CommandResult(MESSAGE_SUCCESS);
	}
}
