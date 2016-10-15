package seedu.taskmanager.logic.commands;

/**
 * Lists all tasks in the task manager to the user.
 */

public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listtask";
    public static final String SHORT_COMMAND_WORD = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tasks.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD; 

    public ListTaskCommand() {}
   
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.updateFilteredListToShowTask();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

