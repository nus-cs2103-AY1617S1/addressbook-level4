package seedu.Tdoo.testutil;


import seedu.Tdoo.commons.exceptions.IllegalValueException;
import seedu.Tdoo.model.task.*;
import seedu.Tdoo.model.task.attributes.*;
/**
 *
 */
//@@author A0132157M
public class EventBuilder {

    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }
    
    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }
    
    public EventBuilder withStartDate(String date) throws IllegalValueException {
        this.event.setStartDate(new StartDate(date));
        return this;
    }

    public EventBuilder withStartTime(String e) throws IllegalValueException {
        this.event.setStartTime(e);
        return this;
    }
    public EventBuilder withEndDate(String e) throws IllegalValueException {
        this.event.setEndDate(e);
        return this;
    }
    
    public EventBuilder withEndTime(String e) throws IllegalValueException {
        this.event.setEndTime(e);
        return this;
    }
    public EventBuilder withDone(String e) throws IllegalValueException {
        this.event.setDone(e);
        return this;
    }

    public TestEvent build() {
        return this.event;
    }

}
