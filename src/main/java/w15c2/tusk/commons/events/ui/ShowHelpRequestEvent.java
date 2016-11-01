package w15c2.tusk.commons.events.ui;

import w15c2.tusk.commons.events.BaseEvent;

/**
 * An event requesting to view the help overlay.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
