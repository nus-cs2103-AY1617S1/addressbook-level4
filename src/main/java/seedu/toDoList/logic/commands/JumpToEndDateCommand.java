package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the end date text field in filter panel.
 */
public class JumpToEndDateCommand extends Command {
    
    public static final String COMMAND_WORD = "e";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter end date:";
    
    public JumpToEndDateCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.END_DATE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
