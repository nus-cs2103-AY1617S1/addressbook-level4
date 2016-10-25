package seedu.flexitrack.logic.commands;

import java.util.Stack;

import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.Model;

/**
 * Clears the FlexiTrack.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_SHORTCUT = "c";
    public static final String MESSAGE_USAGE = COMMAND_WORD  + ", Shortcut [" + COMMAND_SHORTCUT + "]" + ": Clear the to do lists in FlexiTrack.\n" + "Example: "
            + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "FlexiTrack has been cleared!";

    //TODO: i think only allowed one MODEL 
//    private static Model storeDataChanged;

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        assert model != null;
//        storeDataChanged = model; 
//        recordCommand("clear"); 
        model.resetData(FlexiTrack.getEmptyFlexiTrack());
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    //TODO: to be implemented 
    public void executeUndo() {
    }
}
