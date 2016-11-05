package seedu.taskmanager.logic.commands;

import java.util.Set;

//@@author A0140060A

/**
 * Finds and lists all persons in task manager whose name contains all of the argument keywords 
 * Keyword matching is not case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
   
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
