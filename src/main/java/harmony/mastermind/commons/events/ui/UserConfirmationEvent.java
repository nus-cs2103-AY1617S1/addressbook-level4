package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;


/**
 * @@author A0139194X
 *
 * Event to ask for User to confirm clear command
 * Currently not in used yet. Still implementing as enhancement for clear command
 */
public class UserConfirmationEvent extends BaseEvent {

    public String toString() {
        return this.getClass().getSimpleName();
    }
    
}
