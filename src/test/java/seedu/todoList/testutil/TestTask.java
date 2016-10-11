package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask implements ReadOnlyTask {

    //private Name name;
    private Todo Todo;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;
    //private Email email;
    //private Phone phone;
    //private UniqueTagList tags;

    public TestTask() {
        //tags = new UniqueTagList();
    }

    public void setTodo(Todo Todo) {
        this.Todo = Todo;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }
    
    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Todo getTodo() {
        return Todo;
    }
    
    @Override
    public Priority getPriority() {
        return priority;
    }
    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add_task " + this.getTodo().todo + " ");
        sb.append("p/" + this.getPriority().priority + " ");
        sb.append("s/" + this.getStartTime().startTime + " ");
        sb.append("e/" + this.getEndTime().endTime + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
