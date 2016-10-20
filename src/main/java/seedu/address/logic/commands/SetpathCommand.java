package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FilePathChangedEvent;

public class SetpathCommand extends Command {

    public static final String COMMAND_WORD = "setpath";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set custom save path for Taskscheduler. "
            + "Parameters: <filename>\n"
            + "Example: " + COMMAND_WORD
            + " TaskSchedulerData\n";

    public static final String MESSAGE_SUCCESS = "File path changed";
    
    private String savedPathLink;
    
    public SetpathCommand(String arguments) {
        this.savedPathLink = arguments;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new FilePathChangedEvent(savedPathLink));
        return new CommandResult("File path changed");
    }

}