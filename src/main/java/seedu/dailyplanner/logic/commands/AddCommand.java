package seedu.dailyplanner.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;
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

	private final Task toAdd;

	/**
     * Convenience constructor using raw values.
     * @param endDate 
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String taskName, DateTime start, DateTime end, Set<String> tags)
            throws IllegalValueException {
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
			model.getHistory().stackDeleteInstruction(toAdd);
			model.addPerson(toAdd);

			if (checkClash(toAdd) > -1)
				return new CommandResult(String.format(MESSAGE_WARNING_CLASH,
						model.getAddressBook().getPersonList().get(checkClash(toAdd))));

			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicatePersonException e) {
			return new CommandResult(MESSAGE_DUPLICATE_PERSON);
		}

	}

	public int checkClash(Task toCheck) {

		String toAddStartTiming = toCheck.getStart().toString().replaceAll(":", "");
		String toAddEndTiming = toCheck.getEnd().toString().replaceAll(":", "");

		for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
			if (!(toCheck == model.getAddressBook().getPersonList().get(i))) {
				if (toCheck.getStart().toString()
						.equals(model.getAddressBook().getPersonList().get(i).getStart().toString())) {
					String tasksEndTiming = model.getAddressBook().getPersonList().get(i).getStart().toString()
							.replaceAll(":", "");
					String tasksStartTiming = model.getAddressBook().getPersonList().get(i).getEnd().toString()
							.replaceAll(":", "");
					if ((Integer.parseInt(toAddStartTiming) < Integer.parseInt(tasksEndTiming))
							&& (Integer.parseInt(toAddStartTiming) > Integer.parseInt(tasksStartTiming))) {

						return i;
					}
				}
			}
		}
		return -1;
	}

}
