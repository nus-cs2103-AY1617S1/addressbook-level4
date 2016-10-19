package seedu.taskmanager.commons.events.ui;

import javafx.collections.transformation.FilteredList;
import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.item.Item;

/**
 * Indicates a request to jump to the list of persons
 */
public class FilterEvent extends BaseEvent {

	private final FilteredList<Item> filteredItems;

    public FilterEvent(FilteredList<Item> filteredItems) {
        this.filteredItems = filteredItems;
    }
    
    public FilteredList<Item> getFilteredItems() {
        return filteredItems;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
