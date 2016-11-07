package seedu.taskmaster.commons.events.ui;

import seedu.taskmaster.commons.events.BaseEvent;

//@@author A0147967J
/**
 * Represents a selection change in the Navigation Bar Panel
 */
public class NavigationSelectionChangedEvent extends BaseEvent {

    private final String newSelection;

    public NavigationSelectionChangedEvent(String newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getNewSelection() {
        return newSelection;
    }
}
