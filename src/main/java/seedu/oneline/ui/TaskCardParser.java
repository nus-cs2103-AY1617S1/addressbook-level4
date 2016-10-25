package seedu.oneline.ui;

import seedu.oneline.model.task.ReadOnlyTask;

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
        String deadline = task.getDeadline().toString(); 
        String startTime = task.getStartTime().toString(); 
        
        if (deadline.equals("") && startTime.equals("")) { 
            return null; 
        } else if (!deadline.equals("") && startTime.equals("")) { 
            return "Due " + deadline; 
        } else {
            return "From " + task.getStartTime().toString() + " to " + task.getEndTime().toString();
        }
    }
    
    public String getRecurrence() {
        String recurrence = task.getRecurrence().toString(); 
        if (recurrence.equals("")) { 
            return null; 
        } 
        return "every " + task.getRecurrence().toString();
    }
    
}
