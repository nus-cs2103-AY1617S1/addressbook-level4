package seedu.address.logic.commands;


//@@author A0093960X
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverses the last undo command, reversing the effect on the task manager.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "redo";
    

    public RedoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;
        
        // if we are at the latest state where there is no later 'undo commands' to redo, return nothing to redo
        if (history.isLatestCommand()){
            return new CommandResult("Nothing to redo.");
        }
        
        // redo is now OCP friendly :-)
        UndoableCommand cmdToRedo = history.redoStep();
        return cmdToRedo.execute();
        
    }
    
   
}
