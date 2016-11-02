package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.task.Name;

/*
 * 
 * @@author A0143471L
 * 
 * Builds deadline tasks
 */

public class DeadlineTaskBuilder{
    
    private TestDeadlineTask task;

    public DeadlineTaskBuilder() {
        this.task = new TestDeadlineTask();
    }

    public DeadlineTaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public DeadlineTaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            this.task.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    public DeadlineTaskBuilder withPriority(String priority) throws IllegalValueException {
        this.task.setPriority(new Priority(priority));
        return this;
    }
    
    public DeadlineTaskBuilder withDeadline(String deadline) throws IllegalValueException {
        this.task.setDeadline(new DateTime(deadline));
        return this;
    }

    public TestDeadlineTask build() {
        return this.task;
    }
    
    
}
