package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
//@@ Author A0132157M
public class TestEvent extends Event implements ReadOnlyTask {

    //private Name name;
    private Event event;
    private static Name name;
    private static StartDate startDate;
    private static StartTime startTime;
    private static EndDate endDate;
    private static EndTime endTime;
    private static Done done;


    public TestEvent() {
        super(name, startDate, endDate, startTime, endTime, done);
        //tags = new UniqueTagList();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setName(Name name) {
        TestEvent.name = name;
    }

    public void setStartDate(StartDate date) {
        TestEvent.startDate = date;
    }
    public void setEndDate(EndDate date) {
        TestEvent.endDate = date;
    }
    public void setDone(Done done) {
        TestEvent.done = done;
    }
    
    public void setStartTime(StartTime st) {
        TestEvent.startTime = st;
    }
    
    public void setEndTime(EndTime et) {
        TestEvent.endTime = et;
    }

    //@Override
    public Event getEvent() {
        return event;
    }
    
    //@Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public Name getName() {
        return name;
    }

    public StartDate getStartDate() {
        return startDate;
    }
    public EndDate getEndDate() {
        return endDate;
    }
    
    public EndTime getEndTime() {
        return endTime;
    }
    public Done getDone() {
        return done;
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("from" + this.getStartDate().date + " ");
        sb.append("to" + this.getEndDate().endDate + " ");
        sb.append("at" + this.getStartTime().startTime + " ");
        sb.append("to" + this.getEndTime().endTime + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
