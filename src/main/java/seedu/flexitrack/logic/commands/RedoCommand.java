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
    
    //Stores the undone commands
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
        if (redo instanceof AddCommand && redo.getNumOfOccurrrence() !=0 ){
            int numOfOccurrrence = redo.getNumOfOccurrrence();
            redo.setNumOfOccurrrence(0);
            for (int i = 1; i < numOfOccurrrence; i++) {
                redo.execute();
                System.out.println("UNDO.doneCommandStack.size(): " + UndoCommand.doneCommandStack.size());
                redo = undoneCommandStack.pop();
                System.out.println("undoneCommandStack.size(): " + undoneCommandStack.size());
                System.out.println("UNDO.doneCommandStack.size(): " + UndoCommand.doneCommandStack.size());
                System.out.println("redo iterate");
            }
            redo.setNumOfOccurrrence(numOfOccurrrence);
            System.out.println(numOfOccurrrence);
        }

        redo.execute();
        model.indicateFlexiTrackerChanged();
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
   
}