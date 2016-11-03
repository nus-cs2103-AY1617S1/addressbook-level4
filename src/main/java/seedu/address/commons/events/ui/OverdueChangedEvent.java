package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author A0147890U
public class OverdueChangedEvent extends BaseEvent {
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
