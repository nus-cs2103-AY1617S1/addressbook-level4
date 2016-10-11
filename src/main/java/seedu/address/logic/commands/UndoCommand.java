package seedu.address.logic.commands;

import java.util.Stack;

import seedu.address.model.task.Task;

/** 
 * Undo previous add, delete and edit commands.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Undo Command: %1$s";

    private static Stack<Command> PreviousCommands = new Stack<Command>();
    
    
    @Override
    public CommandResult execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
