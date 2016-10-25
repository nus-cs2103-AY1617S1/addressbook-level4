package seedu.taskmanager.logic.commands;

/**
 * Lists all events in the task manager to the user.
 */

public class ListEventCommand extends Command {

    public static final String COMMAND_WORD = "listevent";
    
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "le";
    //@@author 
    
    public static final String MESSAGE_SUCCESS = "Listed all events";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all events.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD; 

    public ListEventCommand() {}
   
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.updateFilteredListToShowEvent();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

