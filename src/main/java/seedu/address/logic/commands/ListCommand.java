package seedu.address.logic.commands;


import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

//@@author A0139498J
/**
 * Lists tasks in the task manager to the user.
 * Supports the listing of all undone, or all done tasks.
 * Supports listing of tasks on certain dates through keywords.
 * Supported keywords are relative dates like today, tomorrow, monday,
 * or specific dates like 1 jan, 2 nov.
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
    public static final String MESSAGE_FAILURE = "Please enter a supported date format to list";

    private final String keyword;
    private final boolean isListDoneCommand;
    private final boolean isListUndoneCommand;

    public ListCommand(String listKeyword) {
        this.keyword = listKeyword;
        this.isListDoneCommand = listKeyword.equals(DONE_COMMAND_WORD);
        this.isListUndoneCommand = listKeyword.isEmpty();
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        
        prepareToListTasks();
        
        if (isListUndoneCommand) {
            return generateCommandResultForListingAllUndoneTasks();
        } else if (isListDoneCommand) {
            return generateCommandResultForListingAllDoneTasks();
        } else {
            try {
                return generateCommandResultForFilteringCurrentTaskView();
            } catch (IllegalValueException ive) {
                return generateCommandResultForUnsupportedKeywords();
            }
        }

    }

    /**
     * Prepare to list tasks by setting filtered lists to show all tasks.
     */
    private void prepareToListTasks() {
        logger.info("Updating lists, to show all tasks");
        model.updateFilteredListsToShowAll();
    }
    
    /**
     * Generates command result for listing all done tasks.
     * Sets current list view to be done list view.
     * 
     * @return CommandResult Indicating that the listing of all done tasks has succeeded.
     */
    private CommandResult generateCommandResultForListingAllDoneTasks() {
        logger.info("Showing all done tasks");
        model.setCurrentListToBeDoneList();
        return new CommandResult(DONE_MESSAGE_SUCCESS);
    }

    /**
     * Generates command result for listing all undone tasks.
     * Sets current list view to be undone list view.
     * 
     * @return CommandResult Indicating that the listing of all undone tasks has succeeded.
     */
    private CommandResult generateCommandResultForListingAllUndoneTasks() {
        logger.info("Showing all undone tasks");
        model.setCurrentListToBeUndoneList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Generates command result for filtering current list view by date.
     * 
     * @return CommandResult Indicating that the current list view is successfully filtered.
     * @throws IllegalValueException  If the keyword provided by user is not in a supported date format.
     */
    private CommandResult generateCommandResultForFilteringCurrentTaskView() throws IllegalValueException {
        logger.info("Filtering current list view");
        if (model.isCurrentListDoneList()) {
            logger.info("Current list view is done list");
            model.updateFilteredDoneTaskListDatePred(keyword);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredDoneTaskList().size()));
        } else {
            logger.info("Current list view is undone list");
            model.updateFilteredUndoneTaskListDatePred(keyword);
            return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredUndoneTaskList().size()));
        }
    }

    /**
     * Generates command result for failing to filter list with keyword.
     * 
     * @return CommandResult Indicating that the keyword provided by user is not in a supported date format.
     */
    private CommandResult generateCommandResultForUnsupportedKeywords() {
        logger.warning("Unsupported keywords entered");
        return new CommandResult(MESSAGE_FAILURE);
    }
    
}
