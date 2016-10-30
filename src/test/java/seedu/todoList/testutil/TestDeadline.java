package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0132157M
public class TestDeadline implements ReadOnlyTask {

    private  Name name;
    private  String startdate;
    private  String endTime;
    private  String done;


    public TestDeadline() {
        super();
        //tags = new UniqueTagList();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setEndTime(String et) {
        this.endTime = et;
    }
    
    public void setDate(String date) {
        this.startdate = date;
    }
    public void setDone(String dd) {
        this.done = dd;
    }

    
    //@Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public Name getName() {
        return name;
    }

    public String getStartDate() {
        return startdate;
    }
    public String getDone() {
        return done;
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        //sb.append(this.getName().name + " ");
        sb.append("on/" + this.getStartDate() + " ");
        sb.append("at/" + this.getEndTime());
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
