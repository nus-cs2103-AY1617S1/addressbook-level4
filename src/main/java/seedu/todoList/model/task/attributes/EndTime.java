package seedu.todoList.model.task.attributes;

public class EndTime {

public final String endTime;
    
    public EndTime(String endTime) {
        this.endTime = endTime;
    }
    
    @Override
    public String toString() {
        return endTime;
    }

}
