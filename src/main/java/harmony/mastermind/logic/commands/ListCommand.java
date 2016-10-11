package harmony.mastermind.logic.commands;

import static harmony.mastermind.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

/**
 * Lists all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LISTING_ARCHIVES = "archive";
    public static final String LISTING_TASKS = "task";
    public static final String MESSAGE_USAGE = "list archive";
    public static final String MESSAGE_SUCCESS_ARCHIVED = "Listed all archived tasks";
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String COMMAND_SUMMARY = "Listing all tasks:"
            + "\n" + COMMAND_WORD;

    private boolean archives;
    
    public ListCommand() {
        archives = false;
    }
    
    public ListCommand(Optional<String> args) {
        archives = true;
    }

    @Override
    public CommandResult execute() {
        if (!archives) {
            model.updateFilteredListToShowAll();
            
            return new CommandResult(MESSAGE_SUCCESS+"\n"+model.getTaskManager().getTaskList().toString());
        
        }else{
            return new CommandResult(MESSAGE_SUCCESS_ARCHIVED);
        }
    }
}
