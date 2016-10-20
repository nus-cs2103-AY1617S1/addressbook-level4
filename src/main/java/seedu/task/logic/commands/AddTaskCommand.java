package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;

/**
 * Adds a task to the task book.
 * @author kian ming
 */
public class AddTaskCommand extends AddCommand {

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task book";

	private static final Boolean DEFAULT_STATUS = false;

	private final Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */

	public AddTaskCommand(String name, String description, String deadline) throws IllegalValueException {
		this.toAdd = new Task(new Name(name), new Description(description), new Deadline(deadline), DEFAULT_STATUS);
	}
	
	public AddTaskCommand(String name, String description) throws IllegalValueException {
        this.toAdd = new Task(new Name(name), new Description(description), DEFAULT_STATUS);
    }

	public AddTaskCommand(ReadOnlyTask t) {
		this.toAdd = new Task(t.getTask(), t.getDescription(), t.getDeadline().orElse(null), t.getTaskStatus());
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

	@Override
	public CommandResult undo() {
		DeleteTaskCommand reverseCommand = new DeleteTaskCommand(toAdd);
		reverseCommand.setData(model);
		
		return reverseCommand.execute();
	}
	
	@Override
	public String toString() {
		return COMMAND_WORD +" "+ this.toAdd.getAsText();
	}
	
	

}
