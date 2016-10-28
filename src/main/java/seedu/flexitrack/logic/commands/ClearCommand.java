package seedu.flexitrack.logic.commands;

import java.util.Stack;

import seedu.flexitrack.commons.core.UnmodifiableObservableList;
import seedu.flexitrack.model.FlexiTrack;
import seedu.flexitrack.model.Model;
import seedu.flexitrack.model.ReadOnlyFlexiTrack;
import seedu.flexitrack.model.task.ReadOnlyTask;
import seedu.flexitrack.model.task.Task;
import seedu.flexitrack.model.task.UniqueTaskList.DuplicateTaskException;

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
    private ReadOnlyFlexiTrack storeDataChange;

    public ClearCommand() {
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        storeDataChange = new FlexiTrack(model.getFlexiTrack());
        model.resetData(FlexiTrack.getEmptyFlexiTrack());
        recordCommand(this); 
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
    public void executeUndo() {
        model.resetData(storeDataChange);
    }
}
