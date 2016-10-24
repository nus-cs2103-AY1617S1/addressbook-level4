package seedu.todoList.testutil;


import seedu.todoList.commons.exceptions.IllegalValueException;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.attributes.*;
/**
 *
 */
public class EventBuilder {

    private TestEvent event;

    public EventBuilder() {
        this.event = new TestEvent();
    }

    public EventBuilder withEvent(Event e) throws IllegalValueException {
        this.event.setEvent(new Event(e));
        return this;
    }
    
    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }
    
    public EventBuilder withDate(String date) throws IllegalValueException {
        this.event.setDate(new StartDate(date));
        return this;
    }

    public EventBuilder withStartTime(String e) throws IllegalValueException {
        this.event.setStartTime(new StartTime(e));
        return this;
    }
    
    public EventBuilder withEndTime(String e) throws IllegalValueException {
        this.event.setEndTime(new EndTime(e));
        return this;
    }

    public TestEvent build() {
        return this.event;
    }

}
