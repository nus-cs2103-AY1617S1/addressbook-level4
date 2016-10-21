package seedu.todo.model.task;

public class Completion {

    private boolean completed;
    
    public Completion() {
        this(false);
    }
    
    public Completion(boolean completed) {
        this.completed = completed;
    }
    
    public boolean isCompleted() {
        return this.completed;
    }
    
    public void setCompletion(boolean completed) {
        this.completed = completed;
    }
    
    public void toggle() {
        this.completed = !this.completed;
    }
    
    @Override
    public String toString() {
        return completed ? "true" : "false";
    }
    
}
