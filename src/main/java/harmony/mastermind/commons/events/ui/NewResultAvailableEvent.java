package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String title;
    
    public final String message;

    public NewResultAvailableEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}