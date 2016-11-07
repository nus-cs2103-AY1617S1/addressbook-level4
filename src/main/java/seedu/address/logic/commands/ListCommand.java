package seedu.address.logic.commands;

import seedu.address.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0144202Y
/**
 * Lists all tasks that is not completed in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all incomplete tasks";
    public static final String KEYWORD_TASKS_SUCCESS = "There are %1$d task(s) listed for ";
    
    private final String keyword;

    public ListCommand(String keyword) {
    	this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
    	
    	if(!keyword.equalsIgnoreCase("")) {
    		return updateListToShowKeywordTasks();
    	}
    	
        try {
			model.updateFilteredListToShowIncompleteTask();
		} catch (DuplicateTaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    /**
     * Filters list to show tasks associated with certain keywords.
     * @return
     */
    private CommandResult updateListToShowKeywordTasks() {
    	try {
    		model.updateFilteredListToShowUncompleteAndKeywordTasks(keyword);
    	} catch (DuplicateTaskException e) {
    		e.printStackTrace();
    	}
    	
    	return new CommandResult(String.format(KEYWORD_TASKS_SUCCESS, model.getFilteredTaskList().size()) + keyword + ".");
    }
}
