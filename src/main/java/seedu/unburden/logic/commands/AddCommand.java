package seedu.unburden.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.tag.Tag;
import seedu.unburden.model.tag.UniqueTagList;
import seedu.unburden.model.task.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the list of tasks. "
			+ "Parameters: NAME i/TASKDESCRIPTIONS d/DATE s/STARTTIME e/ENDTIME [t/TAG]...\n" + "Example: " + COMMAND_WORD
			+ " meeting with boss i/ prepare for minutes d/23-04-2003 s/1200 e/1300 t/important t/work";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list of tasks";

	private final Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public AddCommand(String name, String date, String startTime, String endTime, Set<String> tags)
			throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.toAdd = new Task(new Name(name), new Date(date), new Time(startTime), new Time(endTime),
				new UniqueTagList(tagSet));
	}

	public AddCommand(String name, String date, Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.toAdd = new Task(new Name(name), new Date(date), new UniqueTagList(tagSet));
	}

	public AddCommand(String name, Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.toAdd = new Task(new Name(name), new UniqueTagList(tagSet));
	}

	public AddCommand(String name, String date, String endTime, Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.toAdd = new Task(new Name(name), new Date(date), new Time(endTime), new UniqueTagList(tagSet));
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
