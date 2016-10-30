package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the help overlay.
 */
public class FilterLabelChangeEvent extends BaseEvent {
    
    private COMMANDTYPE commandType;
    
    public enum COMMANDTYPE {
        List, ListComplete, Find
    }
    
    public FilterLabelChangeEvent(COMMANDTYPE commandInput) {
        this.commandType = commandInput;
    }
    
    public COMMANDTYPE getCommandType() {
        return commandType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
