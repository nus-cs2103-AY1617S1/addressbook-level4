package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.alias.ReadOnlyAlias;

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
