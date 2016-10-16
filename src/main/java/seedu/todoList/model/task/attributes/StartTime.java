package seedu.todoList.model.task.attributes;

public class StartTime {

  public final String startTime;
    
    public StartTime(String startTime) {
        this.startTime = startTime;
    }
    
    @Override
    public String toString() {
        return startTime;
    }
}
