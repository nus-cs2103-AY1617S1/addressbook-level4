package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String LIST_KEYWORD_ALL = "all";
    public static final String LIST_KEYWORD_OD = "od";
    public static final String LIST_KEYWORD_DONE = "done";
    
    public static final String LIST_KEYWORDS = LIST_KEYWORD_ALL + "/" + LIST_KEYWORD_OD + "/" + LIST_KEYWORD_DONE;
    
    public static final String MESSAGE_LIST_KEYWORDS = "Only " + LIST_KEYWORDS + " are allowed.";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists the tasks in the address book.\n"
            + "Parameters: " + LIST_KEYWORDS + "\n"
            + "Example: " + COMMAND_WORD
            + " done";

    public static final String MESSAGE_SUCCESS = "Listed tasks";

    private final String keyword;
    
    public ListCommand(String key) throws IllegalValueException{
        Set<String> keywordsList = new HashSet<>(Arrays.asList(LIST_KEYWORDS.split("/")));
        if (keywordsList.contains(key)) {
            this.keyword = key;
        }
        else {
            throw new IllegalValueException(MESSAGE_LIST_KEYWORDS);
        }
    }

    @Override
    public CommandResult execute() {
        
        switch (keyword) {
        case LIST_KEYWORD_ALL:
            model.updateFilteredListToShowAll();
            break;
            
        case LIST_KEYWORD_DONE:
            model.updateFilteredTaskList("DONE");
            break;
            
        case LIST_KEYWORD_OD:
            model.updateFilteredTaskList("OVERDUE");
            break;
        }
        
        return new CommandResult(MESSAGE_SUCCESS);
    }

	@Override
	public boolean isMutating() {
		return false;
	}

	@Override
	public void executeIfIsMutating() {
	}    
    
}
