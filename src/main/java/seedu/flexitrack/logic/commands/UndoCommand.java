//@@author A0127686R
package seedu.flexitrack.logic.commands;

import java.util.Stack;

/**
 * Clears the FlexiTrack.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_SHORTCUT = "ud"; // TODO: impiment ctrl + Z 
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Clear the to do lists in FlexiTrack.\n" + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your last command has been undone!";
    public static final String MESSAGE_NOT_SUCCESS = "You have no command to undo!";
    
    static Stack<Command> doneCommandStack = new Stack<Command>(); 

    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        Command undo = null; 
        if (doneCommandStack.size() == 0 ){ 
            return new CommandResult(String.format(MESSAGE_NOT_SUCCESS));
        }
        undo = doneCommandStack.pop();
        undo.executeUndo();
        RedoCommand.undoneCommandStack.push(undo);
        model.indicateFlexiTrackerChanged();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
   
}
