package seedu.emeraldo.logic.commands;

import java.util.EmptyStackException;

import seedu.emeraldo.model.UndoException;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_UNSUCCESSFUL =  "Cannot undo further";

    public UndoCommand() {}
    
    @Override
    public CommandResult execute() {
    	assert model != null;
    	try{
	        model.undoChanges();
	        return new CommandResult(MESSAGE_SUCCESS);
    	} catch(EmptyStackException e) {
    		return new CommandResult(MESSAGE_UNSUCCESSFUL);
    	}catch(UndoException e){
    	    return new CommandResult(MESSAGE_UNSUCCESSFUL);
    	}
    	
    }

}
