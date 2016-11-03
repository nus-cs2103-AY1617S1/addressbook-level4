package seedu.Tdoo.commons.events.ui;

import seedu.Tdoo.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
