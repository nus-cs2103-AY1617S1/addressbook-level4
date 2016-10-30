package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;
import harmony.mastermind.model.task.Task;


//@@author A0138862W
/*
 * This event is raise when a command request UI to highlight the action row in the tableview
 * 
 */
public class HighlightLastActionedRowRequestEvent extends BaseEvent {

    public Task task;
    
    public HighlightLastActionedRowRequestEvent(Task task){
        this.task = task;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
