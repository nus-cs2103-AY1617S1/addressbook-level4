package seedu.taskscheduler.commons.events.ui;

import seedu.taskscheduler.commons.events.BaseEvent;
import seedu.taskscheduler.model.tag.Tag;

/**
 * Represents a selection change in the tag List Panel
 */
public class TagPanelSelectionChangedEvent extends BaseEvent {


    private final Tag newSelection;

    public TagPanelSelectionChangedEvent(Tag newSelection){
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Tag getNewSelection() {
        return newSelection;
    }
}
