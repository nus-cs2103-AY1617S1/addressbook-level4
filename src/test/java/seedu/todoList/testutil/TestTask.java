package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0132157M reused
public class TestTask implements ReadOnlyTask {

    private   Name name;
    private   String priority;
    private   String startDate;
    private   String endDate;
    private   String done;


    public TestTask() {
    }


    public void setName(Name name) {
        this.name = name;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public void setStartDate(String sdate) {
        this.startDate = sdate;
    }
    public void setEndDate(String edate) {
        this.endDate = edate;
    }
    public void setDone(String done) {
        this.done = done;
    }

    
    //@Override
    public String getPriority() {
        return priority;
    }

    @Override
    public Name getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getDone() {
        return done;
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        //sb.append(this.getName().name + " ");
        //sb.append("from/" + this.getStartDate().date + " ");
        //sb.append("to/ " + this.getEndDate().endDate + " ");
        sb.append("p/ " + this.getPriority());
        //sb.append(this.getDone());
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
