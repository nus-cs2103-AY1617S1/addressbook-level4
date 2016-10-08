package seedu.address.model.task;

import seedu.address.model.tag.UniqueTagList;

public class NonFloatingTask extends Task implements ReadOnlyNonFloatingTask {

    private DateAndTime start, end;
    
    public NonFloatingTask(Name name, UniqueTagList tags, DateAndTime start, DateAndTime end) {
        super(name, tags);
        this.start = start;
        this.end = end;
    }
    
    public DateAndTime getStartDateAndTime() {
        return start;
    }
    
    public DateAndTime getEndDateAndTime() {
        return end;
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
