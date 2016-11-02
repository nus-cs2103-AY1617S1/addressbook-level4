package seedu.task.logic.commands;

import java.util.logging.Logger;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.util.ConfigUtil;

/**
 * @@author A0147944U
 * Lists all tasks in the task manager to the user.
 */
public class SortCommand extends Command {
    
    private static final Logger logger = LogsCenter.getLogger(ConfigUtil.class);

    public static final String COMMAND_WORD = "sort";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": asdasd"
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " deadline";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    private final String keyword;
    
    public SortCommand(String keyword) {
        logger.warning("Listing by: " + keyword);
        if (keyword.startsWith("d")) { //deadline
            this.keyword = "Deadline";
        } else if (keyword.startsWith("s")) { //start time
            this.keyword = "Start Time";
        } else if (keyword.startsWith("e")) { //end time
            this.keyword = "End Time";
        } else if (keyword.startsWith("c")) { //done status
            this.keyword = "Completed";
        } else if (keyword.startsWith("n")) { //name
            this.keyword = "Name";
        } else { //default sorting
            this.keyword = "Default";
        }
    }

    @Override
    public CommandResult execute(boolean isUndo) {
            model.sortFilteredTaskList(keyword);
            logger.warning("Listing by: " + keyword);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult execute(int index) {
        // TODO Auto-generated method stub
        return null;
    }
}
