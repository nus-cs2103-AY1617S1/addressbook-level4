package seedu.Tdoo.testutil;

import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
//@@author A0132157M
public class TestEvent implements ReadOnlyTask {

    //private Event event;
    private  Name name;
    private  StartDate startDate;
    private  String endDate;
    private  String startTime;
    private  String endTime;
    private  String done;


    public TestEvent() {
        super();
        //tags = new UniqueTagList();

    }


    public void setName(Name name) {
        this.name = name;
    }

    public void setStartDate(StartDate date) {
        this.startDate = date;
    }
    public void setEndDate(String date) {
        this.endDate = date;
    }
    public void setDone(String done) {
        this.done = done;
    }
    
    public void setStartTime(String st) {
        this.startTime = st;
    }
    
    public void setEndTime(String et) {
        this.endTime = et;
    }
    
    //@Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public StartDate getStartDate() {
        return startDate;
    } 
    public String getEndDate() {
        return endDate;
    }
    
    public String getEndTime() {
        return endTime;
    }
    public String getDone() {
        return done;
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("from/01-01-2017 ");// + this.getStartDate().toString() + " ");
        sb.append("to/" + this.getEndDate().toString() + " ");
        sb.append("at/"+ this.getStartTime().toString() + " ");
        sb.append("to/" + this.getEndTime().toString());
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
