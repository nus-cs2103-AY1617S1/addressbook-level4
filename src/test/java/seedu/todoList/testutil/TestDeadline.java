package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0132157M
public class TestDeadline extends Deadline implements ReadOnlyTask {

    private Deadline deadline;
    private static Name name;
    private static StartDate startdate;
    private static EndTime endTime;

    private static String done;


    public TestDeadline() {
        super(name, startdate, endTime, done);
        //tags = new UniqueTagList();
    }



    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public void setName(Name name) {
        TestDeadline.name = name;
    }

    public void setEndTime(EndTime et) {
        TestDeadline.endTime = et;
    }
    
    public void setDate(StartDate date) {
        TestDeadline.startdate = date;
    }
    public void setDone(String dd) {
        TestDeadline.done = dd;
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

    public StartDate getStartDate() {
        return startdate;
    }
    public String getDone() {
        return done;
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        //sb.append(this.getName().name + " ");
        sb.append("on" + this.getDate().date + " ");
        sb.append("at" + this.getEndTime().endTime + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
