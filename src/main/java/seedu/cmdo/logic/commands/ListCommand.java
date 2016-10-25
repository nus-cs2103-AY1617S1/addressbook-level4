package seedu.cmdo.logic.commands;


/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_SHORT_ALL = "la";
    public static final String COMMAND_WORD_SHORT_DONE = "ld";
    public static final String COMMAND_WORD_SHORT_BLOCK = "lb";
    public static final String COMMAND_WORD_ALL = "list all";
    public static final String COMMAND_WORD_DONE = "list done";
    public static final String COMMAND_WORD_BLOCK = "list block";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_SHORT_ALL + "/" 
    		+ COMMAND_WORD_SHORT_DONE + "/" + COMMAND_WORD_SHORT_BLOCK + "/" + COMMAND_WORD_ALL + "/" 
    		+ COMMAND_WORD_DONE + "/" + COMMAND_WORD_BLOCK + ": lists wanted list\n" 
    		+ "Example: list all";
    
    private final int type; // 0 for done false, 1 for done true, 2 for blocked and done false

    public ListCommand(int type) {
    	this.type = type;
    }

    @Override
    public CommandResult execute() {
    	if (type == 0)
    		model.updateFilteredListToShowAll(false);
    	else if (type == 1) {
        	model.updateFilteredListToShowAll(true);
    		return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (type == 2)
        	model.updateFilteredListToShowBlocked();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
