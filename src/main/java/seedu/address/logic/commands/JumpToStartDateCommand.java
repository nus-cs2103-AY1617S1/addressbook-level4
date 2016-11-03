package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the start date text field in filter panel
 */
public class JumpToStartDateCommand extends Command {
    
    public static final String COMMAND_WORD = "s";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter start date:";
    
    public JumpToStartDateCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.START_DATE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
