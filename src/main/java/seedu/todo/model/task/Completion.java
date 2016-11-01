//@@author A0093896H
package seedu.todo.model.task;

/**
 * Represents a Task's completion.
 */
public class Completion {

    private boolean isCompleted;
    
    public Completion() {
        this(false);
    }
    
    public Completion(boolean completed) {
        this.isCompleted = completed;
    }
    
    public boolean isCompleted() {
        return this.isCompleted;
    }
    
    public void setCompletion(boolean completed) {
        this.isCompleted = completed;
    }
    
    public void toggle() {
        this.isCompleted = !this.isCompleted;
    }
    
    @Override
    public String toString() {
        return isCompleted ? "true" : "false";
    }
    
}
