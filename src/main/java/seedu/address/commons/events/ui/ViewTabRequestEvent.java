package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author A0142184L
public class ViewTabRequestEvent extends BaseEvent {

    public final String tabName;

    public ViewTabRequestEvent(String tabName) {
        this.tabName = tabName;
    }
    
	@Override
	public String toString() {
        return "Tab in view: " + tabName;
	}

}
