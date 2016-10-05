package seedu.task.commons.events.ui;

import seedu.task.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
