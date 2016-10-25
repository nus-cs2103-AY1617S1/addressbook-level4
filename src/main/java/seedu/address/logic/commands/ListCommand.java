package seedu.address.logic.commands;


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
        
    private Boolean isListDoneCommand;

    public ListCommand(Boolean isListDoneCommand) {
        this.isListDoneCommand = isListDoneCommand;
    }

    @Override
    public CommandResult execute() {
        logger.info("Updating lists, to show all tasks");
        model.updateFilteredListsToShowAll();
        if (isListDoneCommand) {
            logger.info("Showing all done tasks");
            model.setCurrentListToBeDoneList();
            return new CommandResult(DONE_MESSAGE_SUCCESS);
        } else {
            logger.info("Showing all undone tasks");
            model.setCurrentListToBeUndoneList();
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
    
}
