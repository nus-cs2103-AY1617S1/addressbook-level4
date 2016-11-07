package seedu.taskitty.commons.events.ui;

import seedu.taskitty.commons.events.BaseEvent;
import seedu.taskitty.logic.ToolTip;

/**
 * An event requesting to view the help page.
 */
public class ShowToolTipEvent extends BaseEvent {
    ToolTip tooltip;
    
    public ShowToolTipEvent(String userInput) {
        tooltip = ToolTip.getInstance();
        tooltip.createToolTip(userInput);
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public String getToolTipMessage() {
        return tooltip.getMessage();
    }
    
    public String getToolTipDescription() {
        return tooltip.getDescription();
    }
    
    public boolean isUserInputValid() {
        return tooltip.isUserInputValid();
    }

}
