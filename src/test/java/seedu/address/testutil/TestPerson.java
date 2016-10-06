package seedu.address.testutil;

import seedu.address.model.item.*;
import seedu.address.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestPerson implements ReadOnlyToDo {

    private Type type;
    private TodoDate startDate;
    private TodoTime startTime;
    private TodoDate endDate;
    private TodoTime endTime;
    private Name name;
    private UniqueTagList tags;

    public TestPerson() {
        tags = new UniqueTagList();
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setName(Name name) {
        this.name = name;
    }
    
    public void setStartDate(TodoDate date) {
        this.startDate = date;
    }
    
    public void setStartTime(TodoTime time) {
        this.startTime = time;
    }
    
    public void setEndDate(TodoDate date) {
        this.endDate = date;
    }
    
    public void setEndTime(TodoTime time) {
        this.endTime = time;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Name getName() {
        return name;
    }
    
    @Override
    public TodoDate getStartDate() {
        return startDate;
    }
    
    @Override
    public TodoTime getStartTime() {
        return startTime;
    }
    
    @Override
    public TodoDate getEndDate() {
        return endDate;
    }
    
    @Override
    public TodoTime getEndTime() {
        return endTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getType().value + " ");
        sb.append("n/" + this.getName().value + " ");
        if (this.getType().value.equals(Type.DEADLINE_WORD)) {
            sb.append("d/" + this.getEndDate().value + " ");
            sb.append("t/" + this.getEndTime().value + " ");
        } else if (this.getType().value.equals(Type.EVENT_WORD)) {
            sb.append("sd/" + this.getStartDate().value + " ");
            sb.append("st/" + this.getStartTime().value + " ");
            sb.append("ed/" + this.getEndDate().value + " ");
            sb.append("et/" + this.getEndTime().value + " ");
        }
        this.getTags().getInternalList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
