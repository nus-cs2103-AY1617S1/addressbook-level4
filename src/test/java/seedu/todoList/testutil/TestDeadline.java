package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
public class TestDeadline extends Deadline implements ReadOnlyTask {

    private Deadline deadline;
    private static Name name;
    private static StartDate date;
    private static EndTime endTime;
    private static String isDone;


    public TestDeadline() {
        super(name, date, endTime, isDone);
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndTime(EndTime et) {
        this.endTime = et;
    }
    
    public void setDate(StartDate date) {
        this.date = date;
    }

    //@Override
    public Deadline getDeadline() {
        return deadline;
    }
    
    //@Override
    public EndTime getEndTime() {
        return endTime;
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
        sb.append("e/" + this.getEndTime().endTime + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
