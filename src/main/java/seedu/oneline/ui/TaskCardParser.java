package seedu.oneline.ui;

import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.TaskTime;

public class TaskCardParser {

    ReadOnlyTask task;
    
    public TaskCardParser(ReadOnlyTask task) {
        assert task != null; 
        this.task = task;
    }
    
    public String getName() {
        return task.getName().toString();
    }

    //@@author A0142605N 
    public String getTime() {
        if (task.isFloating()) { 
            return ""; 
        } else if (task.hasDeadline()) { 
            return "Due " + task.getDeadline().toString();
        } else if (task.isEvent()) {
            return "From " + task.getStartTime().toString() + " to " + task.getEndTime().toString();
        } else {
            return "Error: undefined type";
        }
    }
    
    public String getTag() {
        return task.getTag().toString();
    }
    
    public String getRecurrence() {
        String recurrence = task.getRecurrence().toString(); 
        if (recurrence.equals("")) { 
            return "";
        } 
        return "every " + task.getRecurrence().toString();
    }
    
}
