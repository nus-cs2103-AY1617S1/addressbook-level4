package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class HideHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
