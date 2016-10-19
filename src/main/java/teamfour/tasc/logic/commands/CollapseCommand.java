package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.CollapseChangeEvent;
import teamfour.tasc.commons.events.ui.JumpToListRequestEvent;

public class CollapseCommand extends Command {

    public static final String COMMAND_WORD = "collapse";
    public static final String MESSAGE_SUCCESS = "Task view collapsed";
    public static final String MESSSAGE_FAILURE = "Already in collapsed view";

    public CollapseCommand(){

    }

    public CommandResult execute(){
        EventsCenter.getInstance().post(new CollapseChangeEvent(true));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public CommandResult executeUndo() {
        return null;
    }
}
