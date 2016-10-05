package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.task.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task book. "
			+ "Parameters: TASK_NAME /desc DESCRIPTION /by DEADLINE" + "Example: " + COMMAND_WORD
			+ " CS2103 Lab 6 /desc hand in through codecrunch /by 30-12-16";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

	private final Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 * TODO: 
	 * 	1. allow tasks with deadline
	 *  
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public AddCommand(String name, String description) throws IllegalValueException {
		this.toAdd = new Task(new Name(name), new Description(description)); //TODO: more flexible of tasks type
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

}
