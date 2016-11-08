package seedu.todolist.logic.commands;

import java.util.List;

//@@author A0153736B
/**
 * Finds and lists all tasks in to-do list whose name contains any/all/phrase of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    
    public static final String MESSAGE_INVALID_FINDTYPE = "The findtype provided is invalid.";
    
    public static final String MESSAGE_KEYWORDS_NOT_PROVIDED = "Keywords are not provided.";
    
    public static final String FINDTYPE_EITHER = "either";
    public static final String FINDTYPE_ALL = "all";
    public static final String FINDTYPE_PHRASE = "phrase";
    public static final String VALID_FINDTYPE = FINDTYPE_EITHER + "|" + FINDTYPE_ALL + "|" + FINDTYPE_PHRASE;
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any/all/phrase of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: FINDTYPE KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " " + FINDTYPE_PHRASE + " alice bob charlie";

    private final List<String> keywords;
    private final String findType;
  
    public FindCommand(List<String> keywords, String findType) {
        this.keywords = keywords;
        this.findType = findType;
    }

    @Override
    public CommandResult execute() {
    	if (!findType.matches(VALID_FINDTYPE))
    		return new CommandResult(MESSAGE_INVALID_FINDTYPE);
    	if (keywords.isEmpty())
    		return new CommandResult(MESSAGE_KEYWORDS_NOT_PROVIDED);
    	
    	assert model != null;
        model.updateFilteredTaskList(keywords, findType);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredIncompleteTaskList().size(), 
        		model.getFilteredCompleteTaskList().size(), model.getFilteredOverdueTaskList().size()));
    }
}
