package seedu.unburden.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;
    private final String modeOfSearch;

    public FindCommand(Set<String> keywords, String modeOfSearch) {
        this.keywords = keywords;
        this.modeOfSearch = modeOfSearch;
    }

	@Override
    public CommandResult execute() {
		switch(modeOfSearch){
			case "date": model.updateFilteredTaskListForDate(keywords); System.out.println("DATE"); break;
			case "name": model.updateFilteredTaskList(keywords); System.out.println("NAME"); break;
		}
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
