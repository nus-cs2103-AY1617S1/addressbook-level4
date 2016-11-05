package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;

//@@author A0138862W
public class TabChangedEvent extends BaseEvent {
    
    public final String fromTabId;
    public final String toTabId;
    
    public TabChangedEvent(String fromTabId, String toTabId){
        this.fromTabId = fromTabId;
        this.toTabId = toTabId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
