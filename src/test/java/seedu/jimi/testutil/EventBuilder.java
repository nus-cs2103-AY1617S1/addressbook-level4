package seedu.jimi.testutil;

import seedu.jimi.commons.exceptions.IllegalValueException;
import seedu.jimi.model.datetime.DateTime;
import seedu.jimi.model.tag.Priority;
import seedu.jimi.model.tag.Tag;
import seedu.jimi.model.task.Name;

/**
 * 
 * @@author A0143471L
 * 
 * Builds Events
 */

public class EventBuilder{
    
    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }

    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }

    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            this.event.getTags().add(new Tag(tag));
        }
        return this;
    }
    
    public EventBuilder withPriority(String priority) throws IllegalValueException {
        this.event.setPriority(new Priority(priority));
        return this;
    }
    
    public EventBuilder withStart(String deadline) throws IllegalValueException {
        this.event.setStart(new DateTime(deadline));
        return this;
    }
    
    public EventBuilder withEnd(String deadline) throws IllegalValueException {
        this.event.setEnd(new DateTime(deadline));
        return this;
    }

    public TestEvent build() {
        return this.event;
    }
    
    
}

