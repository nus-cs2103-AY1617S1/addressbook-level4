package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the tag text field in filter panel.
 */
public class JumpToTagCommand extends Command {
    
    public static final String COMMAND_WORD = "t";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Enter tags:";
    
    public JumpToTagCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.TAG));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
