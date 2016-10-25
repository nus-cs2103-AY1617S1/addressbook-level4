package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.events.storage.RequestTaskListSwitchEvent;
import teamfour.tasc.commons.core.EventsCenter;

/**
 * Switches to a new tasklist.
 */
public class SwitchlistCommand extends Command {

    public static final String COMMAND_WORD = "switchlist";

    public static final String MESSAGE_USAGE = 
            COMMAND_WORD + ": Switches to another tasklist. If it does not exist, creates a new file. \n"
            + "Parameters: FILENAME\n"
            + "Example: " + COMMAND_WORD
            + " work";

    
    public static final String MESSAGE_SUCCESS = 
            "Switched to tasklist: %1$s ";
    public static final String MESSAGE_FILE_OPERATION_FAILURE = 
            "Error occured while operating data. ";
    
    private final String filename;

    /**
     * Switchlist command for switching the current list to a new one.
     */
    public SwitchlistCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        EventsCenter.getInstance().post(new RequestTaskListSwitchEvent(this.filename));
        return new CommandResult(String.format(MESSAGE_SUCCESS, filename));
    }

    @Override
    public boolean canUndo() {
        return false;
    }

}
