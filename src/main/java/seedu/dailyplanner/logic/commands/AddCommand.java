package seedu.dailyplanner.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
import seedu.dailyplanner.commons.util.DateUtil;
import seedu.dailyplanner.model.tag.Tag;
import seedu.dailyplanner.model.tag.UniqueTagList;
import seedu.dailyplanner.model.task.*;
import seedu.dailyplanner.history.*;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the daily planner. "
			+ "Parameters: NAME d/DATE s/STARTTIME e/ENDTIME [c/CATEGORY]...\n" + "Example: " + COMMAND_WORD
			+ " CS2103 Assignment d/11/11/2016 s/10pm e/11pm c/urgent c/important";

	public static final String MESSAGE_SUCCESS = "New task added: %1$s";
	public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the daily planner";
	public static final String MESSAGE_WARNING_CLASH = "Warning! Current timeslot clashes with one the tasks: %1$s";
	private List<ReadOnlyTask> personList;
	private final Task toAdd;

	/**
	 * Convenience constructor using raw values.
	 * 
	 * @param endDate
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */

	public AddCommand(String taskName, DateTime start, DateTime end, Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}
		this.toAdd = new Task(taskName, start, end, false, false, new UniqueTagList(tagSet));
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			personList = model.getAddressBook().getPersonList();
			model.getHistory().stackDeleteInstruction(toAdd);
			model.addPerson(toAdd);

			if (checkClash(toAdd) > -1)
				return new CommandResult(String.format(MESSAGE_WARNING_CLASH, personList.get(checkClash(toAdd))));

			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicatePersonException e) {
			return new CommandResult(MESSAGE_DUPLICATE_PERSON);
		}

	}

	public int checkClash(Task toCheck) {

		if (!(DateUtil.hasStartandEndTime(toCheck))) {
			return -1;
		}
		Time toAddStartTiming = toCheck.getStart().getTime();
		Time toAddEndTiming = toCheck.getEnd().getTime();

		for (int i = 0; i < personList.size(); i++) {
			ReadOnlyTask storedTask = personList.get(i);
			if (DateUtil.hasStartandEndTime(storedTask)) {
				if (!(toCheck == storedTask)) {
					if (toCheck.getStart().getDate().compareTo(storedTask.getStart().getDate()) == 0) {
						Time tasksStartTiming = storedTask.getStart().getTime();
						Time tasksEndTiming = storedTask.getEnd().getTime();

						if ((toAddStartTiming.compareTo(tasksEndTiming) < 0)
								&& (toAddStartTiming.compareTo(tasksStartTiming) > 0)) {
							return i;
						}
						if ((toAddEndTiming.compareTo(tasksStartTiming) > 0)
								&& (toAddEndTiming.compareTo(tasksEndTiming) < 0)) {
							return i;
						}
					}
				}

			}
		}
		return -1;
	}

}
