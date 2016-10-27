package seedu.taskmanager.logic.commands;

//@@author A0135792X

/**
 * Lists all events in the task manager to the user.
 */

public class ListDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "listdeadline";
  
    //@@author A0140060A
    public static final String SHORT_COMMAND_WORD = "ld";
    //@@author 

  //@@author A0135792X
    
    public static final String MESSAGE_SUCCESS = "Listed all deadlines";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all deadlines.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD; 

    public ListDeadlineCommand() {}
   
    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        model.updateFilteredListToShowDeadline();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

