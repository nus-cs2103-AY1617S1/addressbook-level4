package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the recurring text field in filter panel.
 */
public class JumpToRecurringCommand extends Command {
    
    public static final String COMMAND_WORD = "r";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter recurring frequency:";

    public JumpToRecurringCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.RECURRING));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
