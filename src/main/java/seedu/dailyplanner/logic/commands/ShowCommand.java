package seedu.dailyplanner.logic.commands;

import java.util.Set;

/**
 * Lists all persons in the address book to the user.
 */
//@@author A0146749N
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_SUCCESS = "Showing %1$s tasks";

    private final Set<String> keywords;

    public ShowCommand() {
	keywords = null;
    }

    public ShowCommand(Set<String> keywords) {
	this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
	if (keywords == null) {
	    model.updateFilteredListToShowAll();
	    return new CommandResult(String.format(MESSAGE_SUCCESS, "all"));
	} else {
	    model.updateFilteredPersonListByDate(keywords);
	    return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredPersonList().size()));
	}
    }
}
