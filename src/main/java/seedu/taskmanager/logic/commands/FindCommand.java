package seedu.taskmanager.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all items whose names contain any of "
                                               + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
                                               + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                                               + "Example: " + COMMAND_WORD + " CS2103";

    //@@author 
    
    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredItemList(keywords);
        return new CommandResult(getMessageForItemListShownSummary(model.getFilteredItemList().size()));
    }

}
