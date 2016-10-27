package seedu.todoList.testutil;

//import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;

/**
 * A mutable task object. For testing only.
 */
public class TestEvent extends Event implements ReadOnlyTask {

    private Event event;
    private static Name name;
    private static StartDate date;
    private static EndDate endDate;
    private static StartTime startTime;
    private static EndTime endTime;
    private static String isDone;

    public TestEvent() {
        super(name, date, endDate, startTime, endTime, isDone);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setName(Name name) {
        TestEvent.name = name;
    }

    public void setDate(StartDate date) {
        TestEvent.date = date;
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

    public StartDate getDate() {
        return date;
    }
    
    public EndTime getEndTime() {
        return endTime;
    }
    

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().name + " ");
        sb.append("d/" + this.getDate().date + " ");
        sb.append("s/" + this.getStartTime().startTime + " ");
        sb.append("e/" + this.getEndTime().endTime + " ");
        //this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
