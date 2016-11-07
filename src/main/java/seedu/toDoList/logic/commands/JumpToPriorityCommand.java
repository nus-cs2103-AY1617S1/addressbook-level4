package seedu.toDoList.logic.commands;

import seedu.toDoList.commons.core.EventsCenter;
import seedu.toDoList.commons.events.ui.JumpToFilterPanelEvent;
import seedu.toDoList.commons.util.Types;

//@@author A0146123R
/**
 * Jumps to the priority choice box in filter panel.
 */
public class JumpToPriorityCommand extends Command {
    
    public static final String COMMAND_WORD = "p";
    
    public static final String MESSAGE_JUMP_ACKNOWLEDGEMENT = "Select priority:";
    
    public JumpToPriorityCommand() {}

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToFilterPanelEvent(Types.PRIORITY_LEVEL));
        return new CommandResult(MESSAGE_JUMP_ACKNOWLEDGEMENT);
    }

}
