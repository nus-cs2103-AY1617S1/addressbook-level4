package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToFilterPanelEvent;
import seedu.address.commons.util.TypesUtil;

/**
 * Jumps to the priority choice box in filter panel
 */
public class JumpToPriorityCommand extends Command {
    
    public static final String COMMAND_WORD = "p";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Select priority:";
    
    public JumpToPriorityCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(TypesUtil.PRIORITY));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
