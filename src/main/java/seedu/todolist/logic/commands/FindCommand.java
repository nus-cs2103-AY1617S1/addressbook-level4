package seedu.todolist.logic.commands;

import java.util.Set;

/**
 * Finds and lists all tasks in to-do list whose name contains any or all of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks whose names contain any or all of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: FINDTYPE KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " phrase alice bob charlie";
    
    public static final String MESSAGE_INVALID_FINDTYPE = "The findtype provided is invalid.";
    
    public static final String MESSAGE_KEYWORDS_NOT_PROVIDED = "Keywords are not provided.";

    private final Set<String> keywords;
    private final String findType;
  
    //@@author A0153736B
    public FindCommand(Set<String> keywords, String findType) {
        this.keywords = keywords;
        this.findType = findType;
    }

    @Override
    public CommandResult execute() {
    	if (!("either".equals(findType) || "all".equals(findType) || "phrase".equals(findType)))
    		return new CommandResult(MESSAGE_INVALID_FINDTYPE);
    	if (keywords.isEmpty())
    		return new CommandResult(MESSAGE_KEYWORDS_NOT_PROVIDED);
        model.updateFilteredTaskList(keywords, findType);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredAllTaskList().size()));
    }

}
