package seedu.task.logic.commands;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.*;

/**
 * Adds a task to the task book.
 * @author kian ming
 */

//@@author A0127570H
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
	    
	    if (description.isEmpty() && deadline.isEmpty()) {
	        this.toAdd = new Task(new Name(name), null, null, DEFAULT_STATUS);
	    } else if (deadline.isEmpty()) {
	        this.toAdd = new Task(new Name(name), new Description(description), null, DEFAULT_STATUS);
	    } else if (description.isEmpty()) {
            this.toAdd = new Task(new Name(name), null, new Deadline(deadline), DEFAULT_STATUS);
        } else {
            this.toAdd = new Task(new Name(name), new Description(description), new Deadline(deadline), DEFAULT_STATUS);
        }
	}
	
	public AddTaskCommand(ReadOnlyTask t) {
		this.toAdd = new Task(t);
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
