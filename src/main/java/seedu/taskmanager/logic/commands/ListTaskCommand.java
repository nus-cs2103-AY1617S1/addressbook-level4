package seedu.taskmanager.logic.commands;

//@@author A0135792X

/**
 * Lists all tasks in the task manager to the user.
 */

public class ListTaskCommand extends Command {

    public static final String COMMAND_WORD = "listtask";
  
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "lt";
    //@@author 
    
  //@@author A0135792X
    
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

