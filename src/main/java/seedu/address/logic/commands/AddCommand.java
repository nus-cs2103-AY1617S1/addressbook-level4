package seedu.address.logic.commands;

import java.time.LocalDateTime;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParser;
import seedu.address.model.item.Description;
import seedu.address.model.item.Item;
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

	private final Item toAdd;

	
	/**
	 * Constructor using raw strings
	 * @param description: string containing description - required
	 * @param timeStr: the whole string containing start time and end time to be parsed. Not required
	 * @throws IllegalValueException
	 */
	public AddCommand(String descriptionStr, String timeStr) throws IllegalValueException {
		assert descriptionStr != null;
		Description descriptionObj = new Description(descriptionStr);
		DateTimeParser parser = new DateTimeParser(timeStr);
		LocalDateTime startTimeObj = parser.extractStartDate();
		LocalDateTime endTimeObj = parser.extractEndDate();
		this.toAdd = new Item(descriptionObj, startTimeObj, endTimeObj);
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
