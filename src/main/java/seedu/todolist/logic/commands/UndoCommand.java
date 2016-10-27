package seedu.todolist.logic.commands;

import java.util.EmptyStackException;

/**
 * Undoes the most recent operation done by the user.
 */
public class UndoCommand extends Command {
	
    public static final String COMMAND_WORD = "undo";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
    		+ ": Undoes the most recent operation done by the user\n"
    		+ "Example: " + COMMAND_WORD;
    
    public static final String MESSAGE_SUCCESS = "The most recent operation has been undone!";
    
    public static final String MESSAGE_WITHOUT_PREVIOUS_OPERATION = "There is no last operation.";

    public UndoCommand() {}

    //@@author A0153736B
    @Override
    public CommandResult execute() {
        assert model != null; 
        try {
            model.undoToDoList();
            return new CommandResult(MESSAGE_SUCCESS);	
        } catch (EmptyStackException ese) {
            return new CommandResult(MESSAGE_WITHOUT_PREVIOUS_OPERATION);
        }
    }
}
