package seedu.taskscheduler.model.task;

import seedu.taskscheduler.model.tag.UniqueTagList;

//@@author A0148145E
/**
 * Represents a Floating Task in the task scheduler.
 */
public class FloatingTask extends Task {

    public FloatingTask(Name name) {
        super(name, new TaskDateTime(), new TaskDateTime(), new Location(),
                TaskType.FLOATING, new UniqueTagList());
    }

    public FloatingTask(ReadOnlyTask source) {
        super(source);
    }

    @Override
    public Task copy() {
        return new FloatingTask(this);
    }
}
