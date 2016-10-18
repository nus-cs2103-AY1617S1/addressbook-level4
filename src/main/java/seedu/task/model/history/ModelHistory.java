//@@author A0141052Y
package seedu.address.model;

import seedu.address.model.task.ReadOnlyTask;

/**
 * Represents the preceding state of the current Model
 */
public class ModelHistory {
    
    private ReadOnlyTask previousTask;
    
    /**
     * Initializes a ModelHistory with an empty history
     */
    public ModelHistory() {
        previousTask = null;
        
    }
    
    public ReadOnlyTask getPreviousTask() {
        return previousTask;
    }
    
    public void setPreviousTask(ReadOnlyTask task) {
        previousTask = task;
    }
}
