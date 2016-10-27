package seedu.unburden.logic.commands;

import java.text.ParseException;
import java.util.Date;
import java.util.Set;

import seedu.unburden.commons.exceptions.IllegalValueException;
import seedu.unburden.model.task.Task;

/**
 * Finds and lists all persons in address book whose name contains any of the
 * argument keywords. Keyword matching is case sensitive.
 * @@author A0139678J
 */
public class FindCommand extends Command {

	public static final String COMMAND_WORD = "find";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
			+ "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
			+ "Parameters: KEYWORD [MORE_KEYWORDS]...\n" + "Example: " + COMMAND_WORD + " alice bob charlie";

	private final Set<String> keywords;
	private final String date;
	private final String modeOfSearch;

	public FindCommand(Set<String> keywords, String modeOfSearch) {
		this.keywords = keywords;
		this.date = null;
		this.modeOfSearch = modeOfSearch;

	}

	public FindCommand(String date, String modeOfSearch) {
		this.keywords = null;
		this.date = date;
		this.modeOfSearch = modeOfSearch;
	}

	public java.util.function.Predicate<? super Task> getTasksWithSameNameOrTags(Set<String> args) {
		return t -> {
			try {
				return t.getName().contains(args) || t.getTags().contains(args);
			} catch (IllegalValueException e) {
				return false;
			}
		};
	}

	public java.util.function.Predicate<? super Task> getDates(String date) {
		return t -> {
			return t.getDate().fullDate.equals(date);
		};
	}

	@Override
	public CommandResult execute() {
		switch (modeOfSearch) {
		case "date":
			model.updateFilteredTaskList(getDates(date));
			break;
		case "name":
			model.updateFilteredTaskList(getTasksWithSameNameOrTags(keywords));
		}
		return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
	}
}
