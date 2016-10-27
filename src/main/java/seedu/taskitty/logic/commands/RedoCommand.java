package seedu.taskitty.logic.commands;

//@@author A0139052L
/**
* Redoes previous command given
*/
public class RedoCommand extends Command {
    
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_PARAMETER = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "This command redos your previous undo action, Meow!";

    public static final String MESSAGE_REDO_SUCCESS = "Undoed action undone: ";
    public static final String MESSAGE_NO_RECENT_UNDOED_COMMANDS = "There is no recent undoed command in this session.";
    
    @Override
    public CommandResult execute() {
        return null;
    }

}
