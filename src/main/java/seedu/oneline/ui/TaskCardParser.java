//@@author A0140156R

package seedu.oneline.ui;

import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;

public class TaskCardParser {

    ReadOnlyTask task;
    
    public TaskCardParser(ReadOnlyTask task) {
        this.task = task;
    }
    
    public String getName() {
        return task.getName().toString();
    }
    
    public String getLine1() {
        return task.getStartTime().toString() + " - " + task.getEndTime().toString();
    }
    
    public String getLine2() {
        return task.getDeadline().toString();
    }
    
    public String getLine3() {
        return task.getRecurrence().toString();
    }
    
    public String getTag() {
        return task.getTag().toString();
    }
    
}
