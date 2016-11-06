package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;
import seedu.todo.model.tag.Tag;

//@@author A0142421X
public class TagPanelSelectionEvent extends BaseEvent {

    public Tag tag;
    
    public TagPanelSelectionEvent(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
