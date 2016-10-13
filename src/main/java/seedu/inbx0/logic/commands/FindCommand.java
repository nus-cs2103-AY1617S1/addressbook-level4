package seedu.inbx0.logic.commands;

import java.util.Set;

import seedu.inbx0.commons.exceptions.IllegalValueException;
import seedu.inbx0.model.task.Date;
import seedu.inbx0.model.task.Importance;

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

    private final Set<String> keywords;
    private final int type;
    
    public FindCommand(int type, Set<String> keywords) throws IllegalValueException {
        this.type = type;
        this.keywords = ValidateInputFormat(keywords);
    }

    private Set<String> ValidateInputFormat(Set<String> keywords) throws IllegalValueException {
        Set<String> regex = keywords;
        switch(type) {
        case 1: 
        case 2: for(String keyword: keywords) {
                    Date inputDate = new Date(keyword);
                    regex.remove(keyword);
                    regex.add(inputDate.value);
                }
                break;
        case 3: for(String keyword: keywords) {
                    Importance inputImportance = new Importance(keyword);
                    regex.remove(keyword);
    		        regex.add(inputImportance.value);
    		    }
                break;
        }
        return regex;	
    }
    
	@Override  
	public CommandResult execute() {
	    model.updateFilteredTaskList(type, keywords);
	    return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
