package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;
import seedu.task.model.item.ReadOnlyEvent;

/**
 * Indicates a request to jump to the list of items
 */
public class JumpToEventListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final ReadOnlyEvent targetEvent;
    //@@author A0144702N
    
    public JumpToEventListRequestEvent(ReadOnlyEvent event, int targetIndex) {
        this.targetIndex = targetIndex;
        this.targetEvent = event;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
