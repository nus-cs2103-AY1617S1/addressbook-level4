package seedu.jimi.testutil;

import javax.swing.Spring;

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
    
    public EventBuilder withStart(String start) throws IllegalValueException {
        this.event.setStart(new DateTime(start));
        return this;
    }
    
    public EventBuilder withStartNow() {
        this.event.setStart(new DateTime());
        return this;
    }
    
    public EventBuilder withStartTmr() throws IllegalValueException {
        this.event.setStart(DateTime.getTomorrow());
        return this;
    }
        
    public EventBuilder withEnd(String end) throws IllegalValueException {
        this.event.setEnd(new DateTime(end));
        return this;
    }
    
    public EventBuilder withEndOneHourLater() throws IllegalValueException {
        this.event.setEnd(DateTime.getOneHourLater(this.event.getStart()));
        return this;
    }

    public TestEvent build() {
        return this.event;
    }
    
    
}

