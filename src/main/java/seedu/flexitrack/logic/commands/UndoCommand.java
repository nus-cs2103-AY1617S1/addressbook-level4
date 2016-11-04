//@@author A0127686R
package seedu.flexitrack.logic.commands;

import java.util.Stack;
import java.util.logging.Logger;

import seedu.flexitrack.commons.core.LogsCenter;
import seedu.flexitrack.model.ModelManager;
import seedu.flexitrack.model.task.ReadOnlyTask;

/**
 * Clears the FlexiTrack.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_SHORTCUT = "ud"; // TODO: impiment ctrl + Z
    public static final String MESSAGE_USAGE = COMMAND_WORD + ", Shortcut [" + COMMAND_SHORTCUT + "]"
            + ": Clear the to do lists in FlexiTrack.\n" + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your last command has been undone!";
    public static final String MESSAGE_NOT_SUCCESS = "You have no command to undo!";

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    // Stores all the commands done from app launch
    static Stack<Command> doneCommandStack = new Stack<Command>();
    
    public UndoCommand() {
    }

    @Override
    public CommandResult execute() {
        Command undo = null;

        logger.info("----------------[UNDO COMMAND][ executing undo ]");
        
        if (doneCommandStack.size() == 0) {
            return new CommandResult(String.format(MESSAGE_NOT_SUCCESS));
        }

        
        undo = doneCommandStack.pop();
        if (undo instanceof AddCommand && undo.getNumOfOccurrrence() !=0 ){
            int numOfOccurrrence = undo.getNumOfOccurrrence();
            undo.setNumOfOccurrrence(0);
            for (int i = 1; i < numOfOccurrrence; i++) {
                undo.executeUndo();
                RedoCommand.undoneCommandStack.push(undo);
                undo = doneCommandStack.pop();
            }
            undo.setNumOfOccurrrence(numOfOccurrrence);
        }

        undo.executeUndo();
        RedoCommand.undoneCommandStack.push(undo);
        model.indicateFlexiTrackerChanged();
        return new CommandResult(MESSAGE_SUCCESS + "\n" + undo.getUndoMessage());
    }
   
}
