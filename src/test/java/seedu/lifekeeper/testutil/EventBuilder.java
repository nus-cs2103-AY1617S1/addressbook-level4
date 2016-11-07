package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.Name;
import seedu.lifekeeper.model.activity.Reminder;
import seedu.lifekeeper.model.activity.event.EndTime;
import seedu.lifekeeper.model.activity.event.StartTime;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.testutil.TestEvent;

public class EventBuilder extends ActivityBuilder {
	
	private TestEvent event;
	
	public EventBuilder() throws IllegalValueException {
		this.event = new TestEvent();
	}

    public EventBuilder withName(String name) throws IllegalValueException {
        this.event.setName(new Name(name));
        return this;
    }
    
    public EventBuilder withReminder(String address) throws IllegalValueException {
        this.event.setReminder(new Reminder(address));
        return this;
    }

    public EventBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            event.addTags(new Tag(tag));
        }
        return this;
    }

    public EventBuilder withStartTime(String startTime) throws IllegalValueException {
    	this.event.setStartTime(new StartTime(startTime));
    	return this;
    }
    
    public EventBuilder withEndTime(String endTime) throws IllegalValueException {
    	this.event.setEndTime(new EndTime(endTime));
    	return this;
    }
    
    public TestEvent build() {
        return this.event;
    }
}
