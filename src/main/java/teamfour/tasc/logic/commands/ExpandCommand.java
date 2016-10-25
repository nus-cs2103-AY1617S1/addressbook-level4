//@@author A0127014W
package teamfour.tasc.logic.commands;

import teamfour.tasc.commons.core.EventsCenter;
import teamfour.tasc.commons.events.ui.CollapseChangeEvent;
import teamfour.tasc.model.keyword.ExpandCommandKeyword;

/**
 * Expands the task list panel view
 * In expanded view, each task card takes up more space in the panel and shows more detail
 */
public class ExpandCommand extends Command {

    public static final String COMMAND_WORD = ExpandCommandKeyword.keyword;
    public static final String MESSAGE_SUCCESS = "Task view expanded";
    public static final String MESSAGE_FAILURE_ALREADY_EXPANDED = "Already in expanded view, type \"collapse\" to go into collapsed view";

    public ExpandCommand(){

    }

    public CommandResult execute(){
        assert model!= null;
        if(CollapseChangeEvent.getCollapsed()){
            EventsCenter.getInstance().post(new CollapseChangeEvent(false));
            model.updateFilteredTaskListByFilter(); //refresh the list view
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(MESSAGE_FAILURE_ALREADY_EXPANDED);
    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
