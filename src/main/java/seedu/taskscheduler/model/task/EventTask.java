package seedu.taskscheduler.model.task;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.tag.UniqueTagList;

//@@author A0148145E
/**
 * Represents an Event Task in the task scheduler.
 */
public class EventTask extends Task{

    public EventTask(Name name, TaskDateTime startDateTime, TaskDateTime endDateTime, Location address) throws IllegalValueException {
        super(name, startDateTime, endDateTime, address, 
                TaskType.EVENT, new UniqueTagList());
    }
    
    public EventTask(ReadOnlyTask source) {
        super(source);
    }

    @Override
    public Task copy() {
        return new EventTask(this);
    }
}
