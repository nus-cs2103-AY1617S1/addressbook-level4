package seedu.gtd.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final String keywords;
    private final Set<String> keywordSet;

    public FindCommand(String keywords, Set<String> keywordSet) {
        this.keywords = keywords;
        this.keywordSet = keywordSet;
    }
    
    public String getMessageForTaskListShownSummaryIfExactPhraseNotFound() {
    	String MESSAGE_IF_EXACT_PHRASE_NOT_FOUND = "The exact phrase '" + keywords + "' was not found. Listing all tasks containing the keywords entered instead.";
    	return String.format(MESSAGE_IF_EXACT_PHRASE_NOT_FOUND);
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords, keywordSet);
        
        if(model.getFilteredTaskList().isEmpty()) {
        	model.updateFilteredTaskList(keywordSet);
        	if(!model.getFilteredTaskList().isEmpty()) return new CommandResult(getMessageForTaskListShownSummaryIfExactPhraseNotFound());
        }        
        
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
