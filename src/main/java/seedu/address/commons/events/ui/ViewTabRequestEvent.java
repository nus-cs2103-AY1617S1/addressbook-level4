package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.commands.TabCommand;

/**
 * An event requesting to view the list of tasks in a specified tab panel of ui
 */
//@@author A0142184L
public class ViewTabRequestEvent extends BaseEvent {

    public final TabCommand.TabName tabName;

    public ViewTabRequestEvent(TabCommand.TabName tabName) {
        this.tabName = tabName;
    }
    
	@Override
	public String toString() {
        return "Tab in view: " + tabName.name().toLowerCase();
	}

}
