package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0135812L
public class MinimizeRequestEvent extends BaseEvent{

    public MinimizeRequestEvent() {}
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
//@@author