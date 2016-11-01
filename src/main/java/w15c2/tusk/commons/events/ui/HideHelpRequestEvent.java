package w15c2.tusk.commons.events.ui;

import w15c2.tusk.commons.events.BaseEvent;
//@@author A0139708W
/**
 * An event requesting to hide the help overlay.
 */
public class HideHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
