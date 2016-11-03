package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the deadline text field in filter panel
 */
public class JumpToDeadlineCommand extends Command {
    
    public static final String COMMAND_WORD = "d";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter deadline:";
    
    public JumpToDeadlineCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.DEADLINE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
