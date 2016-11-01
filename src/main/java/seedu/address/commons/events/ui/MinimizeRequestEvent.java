package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class MinimizeRequestEvent extends BaseEvent{

    public MinimizeRequestEvent() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
