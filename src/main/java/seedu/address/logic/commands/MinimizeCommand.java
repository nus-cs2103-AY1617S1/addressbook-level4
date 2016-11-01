package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.MinimizeRequestEvent;

public class MinimizeCommand extends Command {

    public static final String COMMAND_WORD = "minimize";
    public static final String MINIMIZED_MESSAGE = "SmartyDo minimized.";
    
    public MinimizeCommand() {}
    
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new MinimizeRequestEvent());
        return new CommandResult(MINIMIZED_MESSAGE);
    }

}
