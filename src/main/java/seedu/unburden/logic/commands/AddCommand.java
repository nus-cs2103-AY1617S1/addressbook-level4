package seedu.unburden.logic.commands;

import java.util.ArrayList;
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
			+ "Parameters: NAME i/TASKDESCRIPTIONS d/DATE s/STARTTIME e/ENDTIME [t/TAG]...\n" + "Example: "
			+ COMMAND_WORD + " meeting with boss i/ prepare for minutes d/23-04-2003 s/1200 e/1300 t/important t/work";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the list of tasks";

	private Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */


	public AddCommand(String mode, ArrayList<String> details, Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}

		switch (mode) {
		case "event with everything":
			System.out.println("addcommand");
			System.out.println("name" + details.get(0));
			System.out.println("taskD" + details.get(1));
			System.out.println("date" + details.get(2));
			System.out.println("startTime" + details.get(3));
			System.out.println("endTime" + details.get(4));
			this.toAdd = new Task(new Name(details.get(0)), new TaskDescription(details.get(1)), new Date(details.get(2)),
					new Time(details.get(3)), new Time(details.get(4)), new UniqueTagList(tagSet));
			break;

		case "event without description":
			this.toAdd = new Task(new Name(details.get(0)), new Date(details.get(1)), new Time(details.get(2)),
					new Time(details.get(3)), new UniqueTagList(tagSet));
			break;

		case "deadline":
			this.toAdd = new Task(new Name(details.get(0)), new TaskDescription(details.get(1)), new Date(details.get(2)),
					new Time(details.get(3)), new UniqueTagList(tagSet));
			break;

		case "deadline without task description":
			this.toAdd = new Task(new Name(details.get(0)), new Date(details.get(1)), new Time(details.get(2)),
					new UniqueTagList(tagSet));
			break;

		case "deadline without task description and time":
			this.toAdd = new Task(new Name(details.get(0)), new Date(details.get(1)), new UniqueTagList(tagSet));
			break;

		case "deadline without task description and date":
			this.toAdd = new Task(new Name(details.get(0)), new Time(details.get(1)), new UniqueTagList(tagSet));
			break;

		case "deadline without date":
			this.toAdd = new Task(new Name(details.get(0)), new TaskDescription(details.get(1)),
					new Time(details.get(2)), new UniqueTagList(tagSet));
			break;

		case "deadline without time":
			this.toAdd = new Task(new Name(details.get(0)), new TaskDescription(details.get(1)),
					new Date(details.get(2)), new UniqueTagList(tagSet));
			break;

		case "floating task":
			this.toAdd = new Task(new Name(details.get(0)), new TaskDescription(details.get(1)),
					new UniqueTagList(tagSet));
			break;

		default:
			this.toAdd = new Task(new Name(details.get(0)), new UniqueTagList(tagSet));
		}
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			model.saveToPrevLists();
			model.addTask(toAdd);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}

	}

}
