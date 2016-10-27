package harmony.mastermind.commons.events.ui;

import harmony.mastermind.commons.events.BaseEvent;
import harmony.mastermind.model.task.Task;

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
