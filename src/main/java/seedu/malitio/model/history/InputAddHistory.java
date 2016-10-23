package seedu.malitio.model.history;

import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;

public class InputAddHistory extends InputHistory {
    
    private ReadOnlyFloatingTask floatingTask;
    private ReadOnlyDeadline deadline;
    private ReadOnlyEvent event;
    private String type;
    
    public InputAddHistory(FloatingTask target) {
        this.commandForUndo = "delete";
        this.floatingTask = target;
        this.type = "floating task";
    }
    
    public InputAddHistory(Deadline target) {
        this.commandForUndo = "delete";
        this.deadline = target;
        this.type = "deadline";
    }
    
    public InputAddHistory(Event target) {
        this.commandForUndo = "delete";
        this.event = target;
        this.type = "event";
        
    }
    
    public String getType() {
        return type;
    }
    
    public ReadOnlyFloatingTask getFloatingTask() {
        return floatingTask;
    }
    
    public ReadOnlyDeadline getDeadline() {
        return deadline;
    }
    
    public ReadOnlyEvent getEvent() {
        return event;
    }

}
