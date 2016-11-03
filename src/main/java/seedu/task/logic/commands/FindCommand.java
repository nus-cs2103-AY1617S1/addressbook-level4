package seedu.task.logic.commands;

import java.util.Set;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 * @author xuchen
 */

//@@author A0144702N
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
    		+ "Finds all tasks and events whose names and descriptions contain any of "
            + "the similar keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Multiple keywords need to be seperated by / followed by a space."
            + "Power search is also supported if supplied the /power flag.\n "
            + "Parameters: KEYWORD [MORE_KEYWORDS]... [/power]\n"
            + "Example: " + COMMAND_WORD + " CS2103 Project / CS2103 Assign /power";

	private static final String MESSAGE_SUCCESS_FIND = "%1$s\n%2$s";

    private final Set<String> keywords;
    private final boolean isPowerSearch;
    
    public FindCommand(Set<String> keywordSet, boolean isPowerSearch) {
		this.keywords = keywordSet;
		this.isPowerSearch = isPowerSearch;
	}
    
	@Override
    public CommandResult execute() {
		
        model.showFoundTaskList(keywords, isPowerSearch);
        model.showFoundEventList(keywords, isPowerSearch);
        
        return new CommandResult(String.format(MESSAGE_SUCCESS_FIND, 
        		getMessageForTaskListShownSummary(model.getFilteredTaskList().size()),
        		getMessageForEventListShownSummary(model.getFilteredEventList().size())));
    }

}
