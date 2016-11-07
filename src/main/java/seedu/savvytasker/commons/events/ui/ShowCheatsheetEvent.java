//@@author A0138431L

package seedu.savvytasker.commons.events.ui;

import seedu.savvytasker.commons.events.BaseEvent;

/** Indicates cheatsheet display has been toggled */
public class ShowCheatsheetEvent extends BaseEvent {
	@Override
    public String toString() {
        return "Cheatsheet display has been toggled";
    }

}
