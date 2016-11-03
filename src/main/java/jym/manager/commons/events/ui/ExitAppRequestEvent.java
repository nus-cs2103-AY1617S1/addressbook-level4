package jym.manager.commons.events.ui;

import jym.manager.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
