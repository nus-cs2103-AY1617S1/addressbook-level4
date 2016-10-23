package harmony.mastermind.logic.commands;

import java.util.EmptyStackException;

public class RedoCommand extends Command{
    
public static final String COMMAND_WORD = "redo";
    
    public static final String MESSAGE_SUCCESS = "Redo successfully.";
    public static final String MESSAGE_EMPTY_COMMAND_HISTORY = "There's no more action available to redo.";
    public static final String MESSAGE_COMMAND_NOT_UNDOABLE = "This command is not redoable";
    public static final String COMMAND_SUMMARY = "Redoing a command:"
            + "\n" + COMMAND_WORD;

    @Override
    //@@author A0138862W
    public CommandResult execute() {

        try{
            // All Command supports undo operation must implement Redoable interface
            
            // execute the redo implementation
            CommandResult redoResult = model.redo();
            
            // display successful message and the details of the undo operations
            return new CommandResult(COMMAND_WORD, 
                    MESSAGE_SUCCESS + "\n" +
                    "=====Redo Details=====\n" +
                    redoResult.feedbackToUser + "\n"+
                    "==================");
        }catch(EmptyStackException ex){
            return new CommandResult(COMMAND_WORD, MESSAGE_EMPTY_COMMAND_HISTORY);
        }
    }

}
