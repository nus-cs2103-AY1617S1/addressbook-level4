package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class SwapTaskListEvent extends BaseEvent {
    
    private boolean isDoneList;
    
    public SwapTaskListEvent(boolean isDoneList) {
        this.isDoneList = isDoneList;
    }
    
    public boolean getIsDoneList() {
        return isDoneList;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
