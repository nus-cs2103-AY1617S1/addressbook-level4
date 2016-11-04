package seedu.simply.commons.events.ui;

import seedu.simply.commons.events.BaseEvent;

//@@author A0147890U
public class OverdueChangedEvent extends BaseEvent {
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
