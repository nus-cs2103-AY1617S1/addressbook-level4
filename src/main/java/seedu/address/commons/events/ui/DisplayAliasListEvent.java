package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.alias.ReadOnlyAlias;

/**
 * An event requesting to view the list of aliases in the left panel of UI
 */
//@@author A0142184L
public class DisplayAliasListEvent extends BaseEvent {

	public final ObservableList<ReadOnlyAlias> list;

	public DisplayAliasListEvent (ObservableList<ReadOnlyAlias> aliasList) {
		this.list = aliasList;
	}
	
	@Override
	public String toString() {
		return list.toString();
	}

}
