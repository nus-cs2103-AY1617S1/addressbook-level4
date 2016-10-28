package seedu.emeraldo.logic.commands;


/**
 * Lists all tasks in Emeraldo to the user.
 */
//@@author A0139749L
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_ALL = "Listed all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all uncompleted tasks\n"
            + COMMAND_WORD + " [KEYWORD]: Lists all tasks with tags containing the specified keyword (case-sensitive)"
            + "and displays them as a list with index numbers.\n"
            + "Example: " + COMMAND_WORD + " or " + COMMAND_WORD + " homework";

    private String keyword;
    private String successMessage;
    
    public ListCommand(String keyword){
        this.keyword = keyword;
    }
    
    @Override
    public CommandResult execute() {
        if(keyword.isEmpty()){
            model.updateFilteredListToShowAll();
            this.successMessage = MESSAGE_LIST_ALL;
        }else{
            model.updateFilteredTaskList(keyword);
            this.successMessage = getMessageForTaskListShownSummary(model.getFilteredTaskList().size());
        }
        return new CommandResult(successMessage);
    }
}
