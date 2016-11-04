package seedu.cmdo.commons.events.ui;

import seedu.cmdo.commons.events.BaseEvent;

//@@author A0139661Y
/**
 * Indicates an attempt to execute an incorrect command
 */
public class UpDownCommandEvent extends BaseEvent {
	private String type;

    public UpDownCommandEvent(String type) {
    	this.type = type;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}