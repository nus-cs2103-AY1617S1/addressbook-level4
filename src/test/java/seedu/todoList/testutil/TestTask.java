package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
public class TestTask extends Todo implements ReadOnlyTask {

    //private Name name;
    private Todo Todo;
    private static Name name;
    private static Priority priority;
    private static StartDate date;


    public TestTask() {
        super(name, date, priority);
        //tags = new UniqueTagList();
    }

    public void setTodo(Todo Todo) {
        this.Todo = Todo;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setDate(StartDate date) {
        this.date = date;
    }

    //@Override
    public Todo getTodo() {
        return Todo;
    }
    
    //@Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    public StartDate getDate() {
        return date;
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        //sb.append(this.getName().name + " ");
        sb.append("d/" + this.getDate().date + " ");
        sb.append("p/" + this.getPriority().priority + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
