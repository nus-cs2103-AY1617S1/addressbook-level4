package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;

//@@author A0138862W
/**
 * 
 * This event is raised when the ActionHistoryCommand request to toggle the UI.
 *
 */
public class ToggleActionHistoryEvent extends BaseEvent{

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
