package seedu.malitio.model.history;

import seedu.malitio.model.task.Deadline;
import seedu.malitio.model.task.Event;
import seedu.malitio.model.task.FloatingTask;
import seedu.malitio.model.task.ReadOnlyDeadline;
import seedu.malitio.model.task.ReadOnlyEvent;
import seedu.malitio.model.task.ReadOnlyFloatingTask;
//@@author A0129595N
public class InputAddHistory extends InputHistory {
    
    private ReadOnlyFloatingTask floatingTask;
    private ReadOnlyDeadline deadline;
    private ReadOnlyEvent event;
    private String type;
    
    public InputAddHistory(Object target) {
        if (isFloatingTask(target)) {
            this.floatingTask = (FloatingTask)target;
            this.type = "floating task";
        } else if (isDeadline(target)) {
            this.deadline = (Deadline)target;
            this.type = "deadline";
        } else {
            this.event = (Event)target;
            this.type = "event";
        }
        this.commandForUndo = "delete";
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
    
    private boolean isFloatingTask(Object p) {
        return p instanceof FloatingTask;
    }

    private boolean isDeadline(Object p) {
        return p instanceof Deadline;
    }

}
