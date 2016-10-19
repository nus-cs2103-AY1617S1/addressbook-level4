package seedu.address.model.activity.event;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.activity.task.DueDate;
import seedu.address.model.activity.task.Priority;
import seedu.address.model.tag.UniqueTagList;

public class Event extends Activity {

    private StartTime startTime;
    private EndTime endTime;
    
    public Event(ReadOnlyActivity source) {
        super(source);
    }

    public Event(Name name, StartTime start, EndTime end, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);
        
        assert !CollectionUtil.isAnyNull(start, end);
        this.startTime = start;
        this.endTime = end;
    }
    
}
