package seedu.dailyplanner.logic.commands;

import java.util.Set;

/**
 *  Reverts the last change made.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Reverted last change.";
    
    Command commandToExecute; 


    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        commandToExecute = model.getReverseCommandFromHistory();
        commandToExecute.execute();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
        
   
    }
}
