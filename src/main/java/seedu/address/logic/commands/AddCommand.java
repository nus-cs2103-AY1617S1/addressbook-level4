package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Description;
import seedu.address.model.item.FloatingTask;
import seedu.address.model.item.UniqueItemList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to-do list. "
			+ "Parameters: \"EVENT_NAME\" from START_TIME to END_TIME on DATE" + "Example: " + COMMAND_WORD
			+ "\"Be awesome\" from 1300 to 2359 on 07/10/2016";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_ITEM = "This task already exists in the to-do list";

	private final FloatingTask toAdd;

	/**
	 * Convenience constructor using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public AddCommand(String description) throws IllegalValueException {
		this.toAdd = new FloatingTask(new Description(description));
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			model.addItem(toAdd);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueItemList.DuplicateItemException e) {
			return new CommandResult(MESSAGE_DUPLICATE_ITEM);
		}

	}

}
