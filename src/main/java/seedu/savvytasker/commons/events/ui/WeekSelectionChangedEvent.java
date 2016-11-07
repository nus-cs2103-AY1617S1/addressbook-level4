//@@author A0138431L

package seedu.savvytasker.commons.events.ui;

import seedu.savvytasker.commons.events.BaseEvent;

/** Indicates the SavvyTasker in the model has changed*/

public class WeekSelectionChangedEvent extends BaseEvent {

	 @Override
	    public String toString() {
	        return "Selected week has been changed";
	    }

}