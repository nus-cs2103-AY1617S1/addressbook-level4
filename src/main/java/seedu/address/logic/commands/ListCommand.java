package seedu.address.logic.commands;


import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

//@@author A0139498J
/**
 * Lists all tasks in the task manager to the user.
 * Supports the listing of all undone, or all done tasks.
 */
public class ListCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);
    
    public static final String COMMAND_WORD = "list";
    public static final String DONE_COMMAND_WORD = "done";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Displays all tasks in the task as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + "\n"
            + "Use " + COMMAND_WORD + " done to display all done tasks as a list with index numbers.\n\t"
            + "Example: " + COMMAND_WORD + " done" + "\n\t";
    
    public static final String TOOL_TIP = "list [done]";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String DONE_MESSAGE_SUCCESS = "Listed all done tasks";
        
    private final Set<String> keywords;
    private final boolean isListDoneCommand;
    private final boolean isListUndoneCommand;

    public ListCommand(Set<String> listKeywords) {
        this.keywords = listKeywords;
        this.isListDoneCommand = listKeywords.contains(ListCommand.DONE_COMMAND_WORD);
        this.isListUndoneCommand = listKeywords.isEmpty();    
    }

    @Override
    public CommandResult execute() {
        assert model != null;

        logger.info("Updating lists, to show all tasks");
        model.updateFilteredListsToShowAll();
        
        //TODO: prevent user from entering retarded list commands
        if (isListUndoneCommand) {
            logger.info("Showing all undone tasks");
            model.setCurrentListToBeUndoneList();
            return new CommandResult(MESSAGE_SUCCESS);
        }
        
        if (isListDoneCommand) {
            logger.info("Showing all done tasks");
            model.setCurrentListToBeDoneList();
            if (keywords.size() > 1) {
                model.updateFilteredDoneTaskListDatePred(keywords);
            }
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredDoneTaskList().size()));
        }
        
        if (model.isCurrentListDoneList()) {
            model.updateFilteredDoneTaskListDatePred(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredDoneTaskList().size()));
        } else {
            model.updateFilteredUndoneTaskListDatePred(keywords);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredUndoneTaskList().size()));
        }
    }
    
}
