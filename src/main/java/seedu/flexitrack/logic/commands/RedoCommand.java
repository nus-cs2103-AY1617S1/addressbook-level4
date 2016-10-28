//@@author A0127855W
package seedu.flexitrack.logic.commands;

import java.util.Stack;

/**
 * Clears the FlexiTrack.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String COMMAND_SHORTCUT = "rd"; // TODO: impiment ctrl + Y 
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Clear the to do lists in FlexiTrack.\n" + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your last command has been redone!";
    public static final String MESSAGE_NOT_SUCCESS = "You have no command to redo!";
    
    static Stack<Command> undoneCommandStack = new Stack<Command>(); 

    public RedoCommand() {
    }

    @Override
    public CommandResult execute() {
        Command redo = null; 
        if (undoneCommandStack.size() == 0 ){ 
            return new CommandResult(String.format(MESSAGE_NOT_SUCCESS));
        }
        redo = undoneCommandStack.pop();
        redo.execute();
        model.indicateFlexiTrackerChanged();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
   
}