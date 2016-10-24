package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.CollapseChangeEvent;
import teamfour.tasc.model.keyword.ExpandCommandKeyword;


public class ExpandCommand extends Command {

    public static final String COMMAND_WORD = ExpandCommandKeyword.keyword;
    public static final String MESSAGE_SUCCESS = "Task view expanded";
    public static final String MESSAGE_FAILURE = "Already in expanded view, type \"collapse\" to go into collapsed view";

    public ExpandCommand(){

    }

    public CommandResult execute(){

        if(CollapseChangeEvent.getCollapsed()){
            EventsCenter.getInstance().post(new CollapseChangeEvent(false));
            model.updateFilteredTaskListByFilter(); //refresh the list view
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_FAILURE);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
