package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.FilePathChangedEvent;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlFileStorage;

public class SetPathCommand extends Command {

    public static final String COMMAND_WORD = "setpath";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set custom save path for Taskscheduler. "
            + "Parameters: <filename>\n"
            + "Example: " + COMMAND_WORD
            + " TaskSchedulerData\n";
    
    private String savedPathLink;
    
    public SetPathCommand(String arguments) {
        this.savedPathLink = arguments;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new FilePathChangedEvent(savedPathLink));
        return new CommandResult("File path changed");
    }

}
