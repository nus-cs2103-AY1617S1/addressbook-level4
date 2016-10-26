package seedu.whatnow.commons.events.ui;
//@@author A0141021H
import seedu.whatnow.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
