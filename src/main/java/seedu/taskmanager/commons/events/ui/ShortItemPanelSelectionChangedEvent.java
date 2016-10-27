package seedu.taskmanager.commons.events.ui;

import seedu.taskmanager.commons.events.BaseEvent;
import seedu.taskmanager.model.item.ReadOnlyItem;

/**
 * Represents a selection change in the Item List Panel
 */
public class ShortItemPanelSelectionChangedEvent extends BaseEvent {

	//@@author A0065571A
    private final ReadOnlyItem newSelection;
    private final int newIdx;

    public ShortItemPanelSelectionChangedEvent(ReadOnlyItem newSelection, int newIdx){
        this.newSelection = newSelection;
        this.newIdx = newIdx;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyItem getNewSelection() {
        return newSelection;
    }
    
    public int getNewIdx() {
        return newIdx;
    }
}
