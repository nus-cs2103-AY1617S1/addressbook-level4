package seedu.savvytasker.commons.events.ui;

import seedu.savvytasker.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class ChangeListRequestEvent extends BaseEvent {
    
    public enum DisplayedList {
        Task,
        Alias
    }

    public final DisplayedList displayedList;

    public ChangeListRequestEvent(DisplayedList displayedList) {
        this.displayedList = displayedList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
