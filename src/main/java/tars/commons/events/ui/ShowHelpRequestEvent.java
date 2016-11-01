package tars.commons.events.ui;

import tars.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 * 
 * @@author A0140022H
 */
public class ShowHelpRequestEvent extends BaseEvent {

    private String args;
    
    public ShowHelpRequestEvent(String args) {
        this.args = args;
    }
    
    public String getHelpRequestEventArgs(){
        return args;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
