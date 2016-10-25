package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.item.Description;
import seedu.task.model.item.EventDuration;
import seedu.task.model.item.Name;

public class EventBuilder {
	private TestEvent event;
	
	public EventBuilder() {
		this.event = new TestEvent();
	}
	
	public EventBuilder withName(String name) throws IllegalValueException {
		this.event.setName(new Name(name));
		return this;
	}
	
	public EventBuilder withDuration(String duration) throws IllegalValueException {
		this.event.setEventDuration(new EventDuration(duration,""));
		return this;
	}
	
	public EventBuilder withDescription(String desc) throws IllegalValueException {
	    this.event.setDescription(new Description(desc));
		return this;
	}
	
	public TestEvent build() {
		return this.event;
	}
}
