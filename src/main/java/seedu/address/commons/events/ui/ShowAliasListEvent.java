package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author A0139708W
/**
 * An event requesting to view the alias list.
 */
public class ShowAliasListEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
