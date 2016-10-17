package seedu.todo.commons.events.ui;

import seedu.todo.commons.events.BaseEvent;

/**
 * An event requesting the help panel to be hidden away.
 */
public class HideHelpEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
