package seedu.address.logic.commands;

/**
 * This class allows the program to group up and sort tasks/events by their tags
 * 
 * @@author A0144202Y
 */

public class ListtagCommand extends Command{
	
    public static final String COMMAND_WORD = "listtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks under the same tag "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS2103";

    private final String keywords;

    public ListtagCommand(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonGroup(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

}
