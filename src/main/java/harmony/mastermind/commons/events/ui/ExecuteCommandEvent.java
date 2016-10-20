package harmony.mastermind.commons.events.ui;

import java.util.Date;

import harmony.mastermind.commons.events.BaseEvent;

//this class is to support event for displaying user action in action history ui
//@@author A0138862W
public class ExecuteCommandEvent extends BaseEvent{

    public Date dateExecuted;
    public String title;
    public String description;
    
    //@@author A0138862W
    public ExecuteCommandEvent(String title, String description){
        dateExecuted = new Date();
        
        this.title = title;
        this.description = description;
    }

    //@@author A0138862W
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    

}
