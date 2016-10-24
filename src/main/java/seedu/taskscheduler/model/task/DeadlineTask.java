package seedu.taskscheduler.model.task;

import seedu.taskscheduler.commons.exceptions.IllegalValueException;
import seedu.taskscheduler.model.tag.Tag;
import seedu.taskscheduler.model.tag.UniqueTagList;

//@@author A0148145E
/**
 * Represents a Deadline Task in the task scheduler.
 */
public class DeadlineTask extends Task {

    public DeadlineTask(Name name, TaskDateTime endDateTime) throws IllegalValueException {
        super(
            name, 
            new TaskDateTime(), 
            endDateTime, 
            new Location(), 
            new UniqueTagList(new Tag("Deadline")));
    }

    public DeadlineTask(ReadOnlyTask source) {
        super(source);
    }

    public Task copy() {
        return new DeadlineTask(this);
    }
}
