package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the end date text field in filter panel
 */
public class JumpToEndDateCommand extends Command {
    
    public static final String COMMAND_WORD = "e";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter end date:";
    
    public JumpToEndDateCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.END_DATE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
