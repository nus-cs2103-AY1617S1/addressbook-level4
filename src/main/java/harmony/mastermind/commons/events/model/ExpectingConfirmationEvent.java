package harmony.mastermind.commons.events.model;

import harmony.mastermind.commons.events.BaseEvent;

//@@author A0139194X
//Currently not in used yet. Will be used for enhancement to clear command
public class ExpectingConfirmationEvent extends BaseEvent {

    public ExpectingConfirmationEvent() {}
    
    @Override
    public String toString() {
        return "Expecting user confirmation next.";
    }
    
}
