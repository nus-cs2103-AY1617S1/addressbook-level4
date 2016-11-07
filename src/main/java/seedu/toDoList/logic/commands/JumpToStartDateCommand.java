package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the start date text field in filter panel.
 */
public class JumpToStartDateCommand extends Command {
    
    public static final String COMMAND_WORD = "s";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter start date:";
    
    public JumpToStartDateCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.START_DATE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
