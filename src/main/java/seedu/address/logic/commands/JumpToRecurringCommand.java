package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the recurring text field in filter panel
 */
public class JumpToRecurringCommand extends Command {
    
    public static final String COMMAND_WORD = "r";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter recurring frequency:";

    public JumpToRecurringCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.RECURRING));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
