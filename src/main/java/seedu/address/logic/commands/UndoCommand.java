package seedu.address.logic.commands;

//@@author A0093960X
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the last reversible command, reversing the effect on the task manager.\n"
            + "Example: " + COMMAND_WORD;
    
    public static final String TOOL_TIP = "undo";
    

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        assert history != null;
        
        // if we are at the earliest state where there is no earlier reversible command to undo, return nothing to undo
        if (history.isEarliestCommand()){
            return new CommandResult("Nothing to undo.");
        }
        
        // Undo is now OCP friendly :-)
        UndoableCommand cmdToUndo = history.undoStep();
        return cmdToUndo.undo();

    }

}
