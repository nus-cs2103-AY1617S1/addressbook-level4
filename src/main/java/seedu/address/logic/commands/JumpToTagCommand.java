package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the tag text field in filter panel
 */
public class JumpToTagCommand extends Command {
    
    public static final String COMMAND_WORD = "t";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter tags:";
    
    public JumpToTagCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.TAG));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
