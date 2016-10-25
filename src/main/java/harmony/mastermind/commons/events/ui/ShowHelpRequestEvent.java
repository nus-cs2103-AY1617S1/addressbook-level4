package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;

/**@@author A0139194X
 * An event requesting to view the help page.
 */
public class ShowHelpRequestEvent extends BaseEvent {

    public final String message;
    
    public ShowHelpRequestEvent(String msg) {
        this.message = msg;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
