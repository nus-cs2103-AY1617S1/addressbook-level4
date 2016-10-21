package seedu.malitio.testutil;

import seedu.malitio.commons.exceptions.IllegalValueException;
import seedu.malitio.model.tag.Tag;
import seedu.malitio.model.task.*;

/**
 *Builds an event
 */
public class EventBuilder {

    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }

    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }
    
    public EventBuilder start(String start) throws IllegalValueException {
        this.event.setStart(new DateTime(start));
        return this;
    }
    public EventBuilder end(String end) throws IllegalValueException {
        this.event.setEnd(new DateTime(end));
        return this;
    }
    
    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            event.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TestEvent build() {
        return this.event;
    }

}
