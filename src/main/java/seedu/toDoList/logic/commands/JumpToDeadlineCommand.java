package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the deadline text field in filter panel.
 */
public class JumpToDeadlineCommand extends Command {
    
    public static final String COMMAND_WORD = "d";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter deadline:";
    
    public JumpToDeadlineCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.DEADLINE));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
