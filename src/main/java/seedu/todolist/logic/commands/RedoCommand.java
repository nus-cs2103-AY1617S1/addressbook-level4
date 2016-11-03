package seedu.todolist.logic.commands;

import java.util.EmptyStackException;

//@@author A0153736B
/**
 * Redoes the most recent undo operation done by the user.
 * If the undo operation is overwritten by other operations, the to-do-list can't be redone.
 */
public class RedoCommand extends Command {
	
    public static final String COMMAND_WORD = "redo";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Redoes the previous undo operation by the user\n"
    		+ "Example: " + COMMAND_WORD;
    
    public static final String MESSAGE_SUCCESS = "The previous undo operation has been redone!";
    
    public static final String MESSAGE_WITHOUT_PREVIOUS_OPERATION = "There is no previous undo operation.";

    public RedoCommand() {}


    @Override
    public CommandResult execute() {
        assert model != null; 
        try {
            model.redoToDoList();
            return new CommandResult(MESSAGE_SUCCESS);	
        } catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        }
    }
}